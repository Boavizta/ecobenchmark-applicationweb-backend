use uuid::Uuid;

#[derive(serde::Deserialize)]
#[serde(deny_unknown_fields)]
pub struct ListCreate {
    name: String,
}

impl ListCreate {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        &self,
        executor: E,
        account_id: Uuid,
    ) -> Result<super::List, sqlx::Error> {
        sqlx::query_as("INSERT INTO list(account_id, name) VALUES ($1,$2) RETURNING id, account_id, name, creation_date")
            .bind(account_id)
            .bind(self.name.as_str())
            .fetch_one(executor)
            .await
    }
}
