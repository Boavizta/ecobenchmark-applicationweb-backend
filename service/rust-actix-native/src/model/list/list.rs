use crate::model::list::List;
use crate::model::task::Task;
use crate::service::database::{Client, Error};
use std::collections::HashMap;
use std::convert::TryFrom;
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

#[derive(serde::Deserialize)]
pub struct ListList {
    #[serde(default = "ListList::default_page")]
    page: usize,
    #[serde(default = "ListList::default_size")]
    size: usize,
}

impl Default for ListList {
    fn default() -> Self {
        Self {
            page: Self::default_page(),
            size: Self::default_size(),
        }
    }
}

#[derive(Debug)]
struct FlattenListWithTask {
    list: List,
    task: Task,
}

impl TryFrom<tokio_postgres::Row> for FlattenListWithTask {
    type Error = tokio_postgres::Error;

    fn try_from(row: tokio_postgres::Row) -> Result<Self, Self::Error> {
        let list_id: Uuid = row.try_get(0)?;
        Ok(Self {
            list: List {
                id: list_id.clone(),
                account_id: row.try_get(1)?,
                name: row.try_get(2)?,
                creation_date: row.try_get(3)?,
            },
            task: Task {
                id: row.try_get(4)?,
                list_id,
                name: row.try_get(5)?,
                description: row.try_get(6)?,
                creation_date: row.try_get(7)?,
            },
        })
    }
}

impl ListList {
    fn default_page() -> usize {
        0
    }

    fn default_size() -> usize {
        10
    }

    pub async fn execute(
        &self,
        client: &mut Client,
        account_id: Uuid,
    ) -> Result<Vec<ListWithTasks>, Error> {
        let stmt = client
            .prepare(
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
                LIMIT $2 OFFSET $3
            )
        "#,
            )
            .await?;
        let rows = client
            .query(
                &stmt,
                &[
                    &account_id,
                    &(self.size as i64),
                    &((self.page * self.size) as i64),
                ],
            )
            .await?;
        let rows: Result<Vec<FlattenListWithTask>, _> = rows
            .into_iter()
            .map(FlattenListWithTask::try_from)
            .collect();
        Ok(ListWithTasks::aggregate(rows?))
    }
}
