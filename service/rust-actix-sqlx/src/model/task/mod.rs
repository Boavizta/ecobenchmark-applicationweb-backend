use chrono::{DateTime, Utc};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;

#[derive(Debug, serde::Deserialize, serde::Serialize)]
#[serde(rename_all = "camelCase")]
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
