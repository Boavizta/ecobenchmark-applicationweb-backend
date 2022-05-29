use crate::model::list::List;
use crate::model::task::Task;
use sqlx::postgres::PgRow;
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

struct FlattenListWithTask {
    list: List,
    task: Task,
}

impl<'r> sqlx::FromRow<'r, PgRow> for FlattenListWithTask {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            list: List::from_row(row)?,
            task: Task::from_row_offset(row, 4)?,
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

    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        &self,
        executor: E,
        account_id: Uuid,
    ) -> Result<Vec<ListWithTasks>, sqlx::Error> {
        let result: Vec<FlattenListWithTask> = sqlx::query_as("SELECT id, account_id, name, creation_date FROM list WHERE account_id = $1 OFFSET $2 LIMIT $3")
            .bind(account_id)
            .bind((self.page * self.size) as i64)
            .bind(self.size as i64)
            .fetch_all(executor)
            .await?;
        Ok(ListWithTasks::aggregate(result))
    }
}
