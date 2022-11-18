use std::convert::TryInto;

use crate::service::database::{Client, Error};

#[derive(serde::Deserialize)]
pub struct AccountCreate {
    login: String,
}

impl AccountCreate {
    pub async fn execute(&self, client: &mut Client) -> Result<super::Account, Error> {
        let stmt = client
            .prepare("INSERT INTO account(login) VALUES ($1) RETURNING id, login, creation_date")
            .await?;
        let row = client.query_one(&stmt, &[&self.login]).await?;
        Ok(row.try_into()?)
    }
}
