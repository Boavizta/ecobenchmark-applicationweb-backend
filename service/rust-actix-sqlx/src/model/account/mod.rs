use chrono::{DateTime, Utc};
use sqlx::mysql::MySqlRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;

#[derive(serde::Deserialize, serde::Serialize)]
pub struct Account {
    id: Uuid,
    login: String,
    creation_date: DateTime<Utc>,
}

impl<'r> sqlx::FromRow<'r, MySqlRow> for Account {
    fn from_row(row: &'r MySqlRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            id: row.get(0),
            login: row.get(1),
            creation_date: row.get(2),
        })
    }
}
