use uuid::Uuid;
use chrono::Utc;

#[derive(serde::Deserialize)]
pub struct AccountCreate {
    login: String,
}

impl AccountCreate {
    pub async fn execute<'e, E: sqlx::Executor<'e, Database = sqlx::MySql>>(
        &self,
        executor: E,
    ) -> Result<super::Account, sqlx::Error> {
        let id = Uuid::new_v4();
        let creation_date = Utc::now();
        sqlx::query("INSERT INTO account(id,login,creation_date) VALUES (?,?,?)")
            .bind(&id)
            .bind(self.login.as_str())
            .bind(&creation_date)
            .execute(executor)
            .await?;
        Ok(super::Account {
            id,
            login: self.login.clone(),
            creation_date,
        })
    }
}
