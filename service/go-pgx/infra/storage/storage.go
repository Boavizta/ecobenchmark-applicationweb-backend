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
	var listIds []string

	rowsList, err := s.pool.Query(context.Background(),
		`select id,name,creation_date 
      		from list
			where list.account_id=$1 limit $2 offset $3
		`,
		request.AccountId,
		pageSize,
		request.Page*pageSize,
	)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to select the tasks list")
	}
	defer rowsList.Close()
	for rowsList.Next() {
		var rawId string
		var name string
		var creationDate time.Time
		err = rowsList.Scan(&rawId, &name, &creationDate)
		if err != nil {
			return nil, errors.Wrap(err, "failed to scan the SQL result")
		}
		id, err := uuid.FromString(rawId)
		if err != nil {
			return nil, errors.Wrapf(err, "the list has wrong id: %s", rawId)
		}
		list := get_lists.GetListsResponse{
			Id:           id,
			Name:         name,
			CreationDate: creationDate,
			Tasks:        []get_lists.TasksResponse{},
		}
		listsRef[rawId] = &list
		listIds = append(listIds, rawId)
	}

	rowsTask, err := s.pool.Query(context.Background(),
		`select list.id,task.id,task.name,task.description,task.creation_date 
      		from list inner join task on (task.list_id=list.id)
			where list.account_id=$1 and list.id = any($2)
		`,
		request.AccountId,
		listIds,
	)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to select the tasks")
	}
	defer rowsTask.Close()
	for rowsTask.Next() {
		var rawListId string
		var rawId string
		var name sql.NullString
		var description sql.NullString
		var creationDate time.Time
		err = rowsTask.Scan(&rawListId, &rawId, &name, &description, &creationDate)
		if err != nil {
			return nil, errors.Wrap(err, "failed to scan the SQL result")
		}

		id, err := uuid.FromString(rawId)
		if err != nil {
			return nil, errors.Wrapf(err, "the task has wrong id: %s", rawId)
		}

		task := get_lists.TasksResponse{
			Id:           id,
			Name:         name.String,
			Description:  description.String,
			CreationDate: creationDate,
		}

		list := listsRef[rawListId]
		list.Tasks = append(listsRef[rawListId].Tasks, task)
	}
	for _, value := range listsRef {
		listWithTask = append(listWithTask, *value)
	}

	return listWithTask, nil
}

func (s *Storage) GetStatsByAccounts() ([]get_stats.GetStatsByAccountsResponse, error) {
	var stats = []get_stats.GetStatsByAccountsResponse{}

	rowsList, err := s.pool.Query(context.Background(),
		`select id,  login, count(list_id) as nb_list, round(avg(nb_tasks),2) as avg_tasks from (select account.id, account.login, list.id list_id, count(task.id) nb_tasks from account inner join list on (list.account_id=account.id) left join task on (task.list_id=list.id) group by account.id, account.login, list.id) t group by id, login`,
	)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to select the stats")
	}
	defer rowsList.Close()
	for rowsList.Next() {
		var rawAccountId string
		var accountLogin sql.NullString
		var listCount int64
		var taskAvg float64
		err = rowsList.Scan(&rawAccountId, &accountLogin, &listCount, &taskAvg)
		if err != nil {
			return nil, errors.Wrap(err, "failed to scan the SQL result")
		}
		accountId, err := uuid.FromString(rawAccountId)
		if err != nil {
			return nil, errors.Wrapf(err, "the account has wrong id: %s", rawAccountId)
		}
		stat := get_stats.GetStatsByAccountsResponse{
			AccountId:    accountId,
			AccountLogin: accountLogin.String,
			ListCount:    listCount,
			TaskAvg:      taskAvg,
		}
		stats = append(stats, stat)
	}
	return stats, nil
}
