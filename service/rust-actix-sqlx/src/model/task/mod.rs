use chrono::{DateTime, Utc};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;

#[derive(serde::Deserialize, serde::Serialize)]
pub struct Task {
    id: Uuid,
    list_id: Uuid,
    name: String,
    description: String,
    creation_date: DateTime<Utc>,
}

impl Task {
    pub fn from_row_offset(row: &PgRow, offset: usize) -> Result<Self, sqlx::Error> {
        Ok(Self {
            id: row.get(offset),
            list_id: row.get(offset + 1),
            name: row.get(offset + 2),
            description: row.get(offset + 3),
            creation_date: row.get(offset + 4),
        })
    }
}

impl<'r> sqlx::FromRow<'r, PgRow> for Task {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Self::from_row_offset(row, 0)
    }
}
