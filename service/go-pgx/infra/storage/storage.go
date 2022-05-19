package storage

import (
	"context"
	"database/sql"
	"github.com/gofrs/uuid"
	"github.com/jackc/pgx/v4/pgxpool"
	"github.com/pkg/errors"
	"go_pgx/usecases/add_account"
	"go_pgx/usecases/add_list"
	"go_pgx/usecases/add_task_to_list"
	"go_pgx/usecases/get_lists"
	"go_pgx/usecases/get_stats"
	"math"
	"time"
)

type Storage struct {
	ConnectionURI string
	pool          *pgxpool.Pool
}

func New(connectionURI string) (*Storage, error) {
	pool, err := pgxpool.Connect(context.Background(), connectionURI)
	if err != nil {
		return nil, errors.Wrap(err, "failed to create the connection pool")
	}

	pgStorage := &Storage{
		ConnectionURI: connectionURI,
		pool:          pool,
	}

	return pgStorage, nil
}

func (s *Storage) AddAccount(account *add_account.AddAccountResponse) error {
	_, err := s.pool.Exec(
		context.Background(),
		"INSERT INTO account(id, login, creation_date) values ($1, $2, $3);",
		account.Id,
		account.Login,
		account.CreationDate,
	)
	if err != nil {
		return errors.Wrapf(err, "failed to insert the account %s", account.Login)
	}
	return nil
}

func (s *Storage) AddList(list *add_list.AddListResponse) error {
	_, err := s.pool.Exec(
		context.Background(),
		"INSERT INTO list(id, account_id, name,  creation_date) values ($1, $2, $3, $4);",
		list.Id,
		list.AccountId,
		list.Name,
		list.CreationDate,
	)
	if err != nil {
		return errors.Wrapf(err, "failed to insert the list %s", list.Name)
	}
	return nil
}

func (s *Storage) AddTask(task *add_task_to_list.AddTaskResponse) error {
	_, err := s.pool.Exec(
		context.Background(),
		"INSERT INTO task(id, list_id, name, description, creation_date) values ($1, $2, $3, $4, $5);",
		task.Id,
		task.ListId,
		task.Name,
		task.Description,
		task.CreationDate,
	)
	if err != nil {
		return errors.Wrapf(err, "failed to insert the task %s", task.Name)
	}
	return nil
}

func (s *Storage) GetListsByAccountId(request get_lists.GetListsRequest) ([]get_lists.GetListsResponse, error) {
	var listWithTask = []get_lists.GetListsResponse{}
	var listsRef = map[string]*get_lists.GetListsResponse{}
	var pageSize int64 = 10

	rowsTask, err := s.pool.Query(context.Background(),
		`SELECT
                l.id,
                l.name,
                l.creation_date,
                l.account_id,
                t.id AS task_id,
                t.name AS task_name,
                t.description,
                t.creation_date AS task_creation_date
            FROM list l
                LEFT JOIN task t ON l.id = t.list_id
            WHERE
                l.account_id = $1
                AND l.id IN (SELECT id FROM list WHERE account_id = $1 LIMIT $2 OFFSET $3)
		`,
		request.AccountId,
		pageSize,
		request.Page*pageSize,
	)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to select the tasks")
	}
	defer rowsTask.Close()
	for rowsTask.Next() {
		var rawListId string
		var listName string
		var listCreationDate time.Time
		var rawAccountId string
		var rawTaskId sql.NullString
		var taskName sql.NullString
		var taskDescription sql.NullString
		var taskCreationDate sql.NullTime
		err = rowsTask.Scan(&rawListId, &listName, &listCreationDate, &rawAccountId, &rawTaskId, &taskName, &taskDescription, &taskCreationDate)
		if err != nil {
			return nil, errors.Wrap(err, "failed to scan the SQL result")
		}

		listId, err := uuid.FromString(rawListId)
		if err != nil {
			return nil, errors.Wrapf(err, "the task has wrong id: %s", rawListId)
		}

		accountId, err := uuid.FromString(rawAccountId)
		if err != nil {
			return nil, errors.Wrapf(err, "the task has wrong id: %s", rawAccountId)
		}

		if rawTaskId.Valid {
			// List with tasks
			taskId, err := uuid.FromString(rawTaskId.String)
			if err != nil {
				return nil, errors.Wrapf(err, "the task has wrong id: %s", rawTaskId)
			}

			task := get_lists.TasksResponse{
				Id:           taskId,
				Name:         taskName.String,
				Description:  taskDescription.String,
				CreationDate: taskCreationDate.Time,
			}

			list := listsRef[rawListId]
			if list != nil {
				list.Tasks = append(listsRef[rawListId].Tasks, task)
			} else {
				list := get_lists.GetListsResponse{
					Id:           listId,
					Name:         listName,
					CreationDate: taskCreationDate.Time,
					AccountId:    accountId,
					Tasks:        []get_lists.TasksResponse{},
				}
				list.Tasks = append(list.Tasks, task)
				listWithTask = append(listWithTask, list)
				listsRef[rawListId] = &listWithTask[len(listWithTask)-1]
			}
		} else {
			// List without tasks
			list := get_lists.GetListsResponse{
				Id:           listId,
				Name:         listName,
				CreationDate: taskCreationDate.Time,
				AccountId:    accountId,
				Tasks:        []get_lists.TasksResponse{},
			}
			listWithTask = append(listWithTask, list)
			listsRef[rawListId] = &listWithTask[len(listWithTask)-1]
		}
	}
	return listWithTask, nil
}

func (s *Storage) GetStatsByAccounts() ([]get_stats.GetStatsByAccountsResponse, error) {
	var stats = []get_stats.GetStatsByAccountsResponse{}

	type rawData struct {
		AccountId    uuid.UUID
		AccountLogin string
		RawListId    string
		RawTaskId    sql.NullString
	}

	var rawDataArray = []rawData{}

	rowsList, err := s.pool.Query(context.Background(),
		`select account.id, account.login, list.id list_id, task.id task_id from account inner join list on (list.account_id=account.id) left join task on (task.list_id=list.id)`,
	)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to select the stats")
	}
	defer rowsList.Close()
	for rowsList.Next() {
		var rawAccountId string
		var accountLogin sql.NullString
		var listId string
		var taskId sql.NullString
		err = rowsList.Scan(&rawAccountId, &accountLogin, &listId, &taskId)
		if err != nil {
			return nil, errors.Wrap(err, "failed to scan the SQL result")
		}
		accountId, err := uuid.FromString(rawAccountId)
		if err != nil {
			return nil, errors.Wrapf(err, "the account has wrong id: %s", rawAccountId)
		}
		raw := rawData{
			AccountId:    accountId,
			AccountLogin: accountLogin.String,
			RawListId:    listId,
			RawTaskId:    taskId,
		}
		rawDataArray = append(rawDataArray, raw)
	}

	type preparedData struct {
		AccountId    uuid.UUID
		AccountLogin string
		CountListId  int
		TaskIdMap    map[string]int
	}
	var preparedDataMap = map[string]*preparedData{}

	for _, e := range rawDataArray {
		if preparedDataMap[e.AccountId.String()] == nil {
			taskIdMap := map[string]int{}
			taskIdMap[e.RawListId] = 0
			if e.RawTaskId.Valid {
				taskIdMap[e.RawListId] = 1
			}
			prepData := preparedData{
				AccountId:    e.AccountId,
				AccountLogin: e.AccountLogin,
				CountListId:  1,
				TaskIdMap:    taskIdMap,
			}
			preparedDataMap[e.AccountId.String()] = &prepData
		} else {
			if _, ok := preparedDataMap[e.AccountId.String()].TaskIdMap[e.RawListId]; ok {
				if e.RawTaskId.Valid {
					preparedDataMap[e.AccountId.String()].TaskIdMap[e.RawListId] += 1
				}
			} else {
				preparedDataMap[e.AccountId.String()].CountListId += 1
				preparedDataMap[e.AccountId.String()].TaskIdMap[e.RawListId] = 0
				if e.RawTaskId.Valid {
					preparedDataMap[e.AccountId.String()].TaskIdMap[e.RawListId] = 1
				}
			}
		}
	}

	for _, e := range preparedDataMap {
		inc := 0
		for _, t := range e.TaskIdMap {
			inc += t
		}
		avgTask := float64(inc) / float64(e.CountListId)
		stat := get_stats.GetStatsByAccountsResponse{
			AccountId:    e.AccountId,
			AccountLogin: e.AccountLogin,
			ListCount:    int64(e.CountListId),
			TaskAvg:      math.Round(avgTask*100) / 100,
		}
		stats = append(stats, stat)
	}

	return stats, nil
}
