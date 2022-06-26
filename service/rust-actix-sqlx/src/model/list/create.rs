use uuid::Uuid;
use chrono::Utc;

#[derive(serde::Deserialize)]
pub struct ListCreate {
    name: String,
}

impl ListCreate {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::MySql>>(
        &self,
        executor: E,
        account_id: Uuid,
    ) -> Result<super::List, sqlx::Error> {
        let id = Uuid::new_v4();
        let creation_date = Utc::now();
        sqlx::query("INSERT INTO list(id, account_id, name, creation_date) VALUES (?,?,?,?)")
            .bind(&id)
            .bind(&account_id)
            .bind(self.name.as_str())
            .bind(&creation_date)
            .execute(executor)
            .await?;
        Ok(super::List {
            id,
            account_id,
            name: self.name.clone(),
            creation_date,
        })
    }
}
