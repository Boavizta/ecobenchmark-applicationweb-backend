#[derive(serde::Deserialize)]
#[serde(deny_unknown_fields)]
pub struct AccountCreate {
    login: String,
}

impl AccountCreate {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        &self,
        executor: E,
    ) -> Result<super::Account, sqlx::Error> {
        sqlx::query_as("INSERT INTO account(login) VALUES ($1) RETURNING id, login, creation_date")
            .bind(self.login.as_str())
            .fetch_one(executor)
            .await
    }
}
