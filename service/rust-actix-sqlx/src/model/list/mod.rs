use chrono::{DateTime, Utc};
use sqlx::mysql::MySqlRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;
pub mod list;

#[derive(Debug, serde::Deserialize, serde::Serialize)]
pub struct List {
    pub id: Uuid,
    pub account_id: Uuid,
    pub name: String,
    pub creation_date: DateTime<Utc>,
}

impl<'r> sqlx::FromRow<'r, MySqlRow> for List {
    fn from_row(row: &'r MySqlRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            id: row.get(0),
            account_id: row.get(1),
            name: row.get(2),
            creation_date: row.get(3),
        })
    }
}
