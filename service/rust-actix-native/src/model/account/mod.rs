use chrono::{DateTime, Utc};
use std::convert::TryFrom;
use uuid::Uuid;

pub mod create;

#[derive(serde::Deserialize, serde::Serialize)]
#[serde(rename_all = "camelCase")]
pub struct Account {
    id: Uuid,
    login: String,
    creation_date: DateTime<Utc>,
}

impl TryFrom<tokio_postgres::Row> for Account {
    type Error = tokio_postgres::Error;

    fn try_from(value: tokio_postgres::Row) -> Result<Self, Self::Error> {
        Ok(Self {
            id: value.try_get(0)?,
            login: value.try_get(1)?,
            creation_date: value.try_get(2)?,
        })
    }
}
