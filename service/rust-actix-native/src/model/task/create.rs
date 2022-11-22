use std::convert::TryInto;

use crate::service::database::{Client, Error};
use uuid::Uuid;

#[derive(serde::Deserialize)]
pub struct TaskCreate {
    name: String,
    description: String,
}

impl TaskCreate {
    pub async fn execute(&self, client: &mut Client, list_id: Uuid) -> Result<super::Task, Error> {
        let stmt = client.prepare("INSERT INTO task(list_id, name, description) VALUES ($1,$2,$3) RETURNING id, list_id, name, description, creation_date").await?;
        let row = client
            .query_one(&stmt, &[&list_id, &self.name, &self.description])
            .await?;
        Ok(row.try_into()?)
    }
}
