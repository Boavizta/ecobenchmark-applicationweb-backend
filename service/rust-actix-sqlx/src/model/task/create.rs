use uuid::Uuid;

#[derive(serde::Deserialize)]
pub struct TaskCreate {
    name: String,
    description: String,
}

impl TaskCreate {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::MySql>>(
        self,
        executor: E,
        list_id: Uuid,
    ) -> Result<super::Task, sqlx::Error> {
        let id = Uuid::new_v4();
        let creation_date = chrono::Utc::now();
        sqlx::query("INSERT INTO task(id, list_id, name, description, creation_date) VALUES (?,?,?,?,?)")
            .bind(&id)
            .bind(&list_id)
            .bind(self.name.as_str())
            .bind(self.description.as_str())
            .bind(&creation_date)
            .execute(executor)
            .await?;
        Ok(super::Task {
            id,
            list_id,
            name: self.name,
            description: self.description,
            creation_date,
        })
    }
}
