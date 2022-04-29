use chrono::{DateTime, Utc};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

pub mod create;

#[derive(serde::Deserialize, serde::Serialize)]
pub struct Account {
    id: Uuid,
    login: String,
    creation_date: DateTime<Utc>,
}

impl<'r> sqlx::FromRow<'r, PgRow> for Account {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        let id: String = row.get(0);
        let id = Uuid::try_parse(&id).map_err(|err| sqlx::Error::ColumnDecode {
            index: "id".to_string(),
            source: Box::new(err),
        })?;
        Ok(Self {
            id,
            login: row.get(1),
            creation_date: row.get(2),
        })
    }
}
