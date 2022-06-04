use crate::model::list::List;
use crate::model::task::Task;
use uuid::Uuid;

#[derive(serde::Serialize)]
pub struct ListWithTasks {
    #[serde(flatten)]
    pub inner: List,
    pub tasks: Vec<Task>,
}

impl From<List> for ListWithTasks {
    fn from(inner: List) -> Self {
        Self {
            inner,
            tasks: Vec::new(),
        }
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
    ) -> Result<Vec<List>, sqlx::Error> {
        sqlx::query_as(
            r#"
            SELECT id, account_id, name, creation_date
            FROM list
            WHERE account_id = $1
            LIMIT $2 OFFSET $3
            "#,
        )
        .bind(account_id)
        .bind(self.size as i64)
        .bind((self.page * self.size) as i64)
        .fetch_all(executor)
        .await
    }
}
