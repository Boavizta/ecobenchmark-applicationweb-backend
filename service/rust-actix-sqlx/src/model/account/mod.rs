use chrono::{DateTime, Utc};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;

#[derive(serde::Deserialize, serde::Serialize)]
#[serde(rename_all = "camelCase")]
pub struct Account {
    id: Uuid,
    login: String,
    creation_date: DateTime<Utc>,
}

impl<'r> sqlx::FromRow<'r, PgRow> for Account {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            id: row.get(0),
            login: row.get(1),
            creation_date: row.get(2),
        })
    }
}
