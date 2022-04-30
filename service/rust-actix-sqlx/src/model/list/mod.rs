use chrono::{DateTime, Utc};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;
pub mod list;

#[derive(serde::Deserialize, serde::Serialize)]
pub struct List {
    id: Uuid,
    account_id: Uuid,
    name: String,
    creation_date: DateTime<Utc>,
}

impl<'r> sqlx::FromRow<'r, PgRow> for List {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            id: row.get(0),
            account_id: row.get(1),
            name: row.get(2),
            creation_date: row.get(3),
        })
    }
}
