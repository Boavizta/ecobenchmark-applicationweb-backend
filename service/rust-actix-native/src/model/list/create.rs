use crate::service::database::{Client, Error};
use uuid::Uuid;

#[derive(serde::Deserialize)]
pub struct ListCreate {
    name: String,
}

impl ListCreate {
    pub async fn execute(
        &self,
        client: &mut Client,
        account_id: Uuid,
    ) -> Result<super::List, Error> {
        let stmt = client.prepare("INSERT INTO list(account_id, name) VALUES ($1,$2) RETURNING id, account_id, name, creation_date").await?;
        let row = client.query_one(&stmt, &[&account_id, &self.name]).await?;
        Ok(row.try_into()?)
    }
}
