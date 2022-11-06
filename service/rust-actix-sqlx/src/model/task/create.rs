use uuid::Uuid;

#[derive(serde::Deserialize)]
#[serde(deny_unknown_fields)]
pub struct TaskCreate {
    name: String,
    description: String,
}

impl TaskCreate {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        &self,
        executor: E,
        list_id: Uuid,
    ) -> Result<super::Task, sqlx::Error> {
        sqlx::query_as("INSERT INTO task(list_id, name, description) VALUES ($1,$2,$3) RETURNING id, list_id, name, description, creation_date")
            .bind(&list_id)
            .bind(self.name.as_str())
            .bind(self.description.as_str())
            .fetch_one(executor)
            .await
    }
}
