# CONTRIBUTION GUIDE

## Service Contribution

Target folders :
- [/service](./service)

### Foreword

#### Definition

A service is a combination of a language, a web framework, dao/orm lib (optional) and a webserver (optional).

#### Name convention
`[ecosystem-]language-framework[-dao][-webserver]`

**Example** :
- go-pgx (go as the language, pgx as the dao lib)
- jvm-kotlin-spring (jvm as the ecosystem, kotlin as the language, spring as the framework)
- php-symfony-apache2 (php as the language, symfony as the framework, apache2 as the webserver).

#### Implementation Spirit

Remember that is not a  benchmark to make language competition. But an eco benchmark to identify which design choice got impact on web application footprint on our planet. Design should represent practices from the communities.

#### Degraded use case

In order to compare other design choice than framework and language, there is a list of predefined use case documented. [See use case documentation](./readme.md) Each use case got a design difference (and only one).

### Design rules

Each service must respect :
- an API contract, 
- a predefined set of SQL requests by API to respect.
- for some service, algorithm to respect.

Reference implementation is the [golang implementation](./service/go-pgx).

#### API Contract

API Contract must respect the following [API Definition documentation](./service/specs.md).

#### SQL requests

Each API endpoint must respect the following SQL request.

---
**NOTE**

Due to ORM behavior you may have small difference in the real generated SQL. Please just check if there is no major differences and document the minor differences.
___

##### POST /api/accounts

`INSERT INTO account(id, login, creation_date) values ($1, $2, $3);`

##### POST /api/accounts/:account/lists

`INSERT INTO list(id, account_id, name,  creation_date) values ($1, $2, $3, $4);`

##### POST /api/lists/:list/tasks

`INSERT INTO task(id, list_id, name, description, creation_date) values ($1, $2, $3, $4, $5);`

##### GET /api/accounts/:account/lists?page=n

`SELECT l.id, l.name, l.creation_date, l.account_id, t.id AS task_id, t.name AS task_name, t.description, t.creation_date AS task_creation_date
FROM list l LEFT JOIN task t ON l.id = t.list_id
WHERE l.account_id = $1 AND l.id IN (SELECT id FROM list WHERE account_id = $1 LIMIT $2 OFFSET $3)`

##### GET /api/stats

`select id,  login, count(list_id) as nb_list, round(avg(nb_tasks),2) as avg_tasks from (select account.id, account.login, list.id list_id, count(task.id) nb_tasks from account inner join list on (list.account_id=account.id) left join task on (task.list_id=list.id) group by account.id, account.login, list.id) t group by id, login`

#### Algorithm

##### GET /api/stats

Please follow the following example :

```go
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
```

## Result analysis contribution

Target folders :
- [/results](./results)

_Not available yet_

## Runner contribution

Target folders :
- [/builder](./builder)
- [/migrations](./migrations)
- [/runner](./runner)
- [/script](./script)

_Not available yet_
