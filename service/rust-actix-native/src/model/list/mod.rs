use chrono::{DateTime, Utc};
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

impl TryFrom<tokio_postgres::Row> for List {
    type Error = tokio_postgres::Error;

    fn try_from(value: tokio_postgres::Row) -> Result<Self, Self::Error> {
        Ok(Self {
            id: value.try_get(0)?,
            account_id: value.try_get(1)?,
            name: value.try_get(2)?,
            creation_date: value.try_get(3)?,
        })
    }
}
