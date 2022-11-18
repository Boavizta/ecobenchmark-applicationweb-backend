use chrono::{DateTime, Utc};
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

impl TryFrom<tokio_postgres::Row> for Task {
    type Error = tokio_postgres::Error;

    fn try_from(value: tokio_postgres::Row) -> Result<Self, Self::Error> {
        Ok(Self {
            id: value.try_get(0)?,
            list_id: value.try_get(1)?,
            name: value.try_get(2)?,
            description: value.try_get(3)?,
            creation_date: value.try_get(4)?,
        })
    }
}
