use crate::model::list::List;
use crate::model::task::Task;
use sqlx::postgres::PgRow;
use sqlx::Row;
use std::collections::HashMap;
use uuid::Uuid;

#[derive(serde::Serialize)]
pub struct ListWithTasks {
    #[serde(flatten)]
    inner: List,
    tasks: Vec<Task>,
}

impl From<List> for ListWithTasks {
    fn from(inner: List) -> Self {
        Self {
            inner,
            tasks: Vec::new(),
        }
    }
}

impl ListWithTasks {
    fn aggregate(origin: Vec<FlattenListWithTask>) -> Vec<ListWithTasks> {
        origin
            .into_iter()
            .fold(HashMap::new(), |mut res, flat| {
                let FlattenListWithTask { list, task } = flat;
                let list_id = list.id.clone();
                let entry = res.entry(list_id).or_insert(ListWithTasks::from(list));
                entry.tasks.push(task);
                res
            })
            .into_values()
            .collect()
    }
}

#[derive(Debug)]
struct FlattenListWithTask {
    list: List,
    task: Task,
}

impl<'r> sqlx::FromRow<'r, PgRow> for FlattenListWithTask {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        let list_id: Uuid = row.get(0);
        Ok(Self {
            list: List {
                id: list_id.clone(),
                account_id: row.get(1),
                name: row.get(2),
                creation_date: row.get(3),
            },
            task: Task {
                id: row.get(4),
                list_id,
                name: row.get(5),
                description: row.get(6),
                creation_date: row.get(7),
            },
        })
    }
}

pub struct ListList;

impl ListList {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        executor: E,
        account_id: Uuid,
    ) -> Result<Vec<ListWithTasks>, sqlx::Error> {
        let result: Vec<FlattenListWithTask> = sqlx::query_as(
            r#"
            SELECT
                l.id,
                l.account_id,
                l.name,
                l.creation_date,
                t.id AS task_id,
                t.name AS task_name,
                t.description,
                t.creation_date AS task_creation_date
            FROM list l
                LEFT JOIN task t ON l.id = t.list_id
            WHERE
                l.account_id = $1
                AND l.id IN (
                    SELECT id
                    FROM list
                    WHERE account_id = $1
                )
            "#,
        )
        .bind(account_id)
        .fetch_all(executor)
        .await?;
        Ok(ListWithTasks::aggregate(result))
    }
}
