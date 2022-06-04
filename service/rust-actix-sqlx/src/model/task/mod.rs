use chrono::{DateTime, Utc};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;

#[derive(Debug, serde::Deserialize, serde::Serialize)]
pub struct Task {
    pub id: Uuid,
    pub list_id: Uuid,
    pub name: String,
    pub description: String,
    pub creation_date: DateTime<Utc>,
}

impl<'r> sqlx::FromRow<'r, PgRow> for Task {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            id: row.get(0),
            list_id: row.get(1),
            name: row.get(2),
            description: row.get(3),
            creation_date: row.get(4),
        })
    }
}

impl Task {
    pub async fn find_for_list<'e, 'u, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        executor: E,
        list_id: &'u Uuid,
    ) -> Result<Vec<Task>, sqlx::Error> {
        sqlx::query_as(
            r#"
            SELECT id, list_id, name, description, creation_date
            FROM task
            WHERE list_id = $1
            "#,
        )
        .bind(list_id)
        .fetch_all(executor)
        .await
    }
}
