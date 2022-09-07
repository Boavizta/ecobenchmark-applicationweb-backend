use num_traits::cast::ToPrimitive;
use sqlx::postgres::PgRow;
use sqlx::types::Decimal;
use sqlx::Row;
use uuid::Uuid;

#[derive(serde::Serialize)]
#[serde(rename_all = "camelCase")]
pub struct AccountStat {
    account_id: Uuid,
    account_login: String,
    list_count: u64,
    task_avg: f64,
}

impl AccountStat {
    pub async fn list<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        executor: E,
    ) -> Result<Vec<AccountStat>, sqlx::Error> {
        sqlx::query_as(
            r#"
            select id, login, count(list_id) as nb_list, round(avg(nb_tasks), 2) as avg_tasks
            from (
                select account.id, account.login, list.id list_id, count(task.id) nb_tasks
                from account
                inner join list on (list.account_id=account.id)
                left join task on (task.list_id=list.id)
                group by account.id, account.login, list.id
            ) t
            group by id, login
            "#,
        )
        .fetch_all(executor)
        .await
    }
}

impl<'r> sqlx::FromRow<'r, PgRow> for AccountStat {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            account_id: row.get(0),
            account_login: row.get(1),
            list_count: row.get::<i64, _>(2) as u64,
            task_avg: row.get::<Decimal, _>(3).to_f64().unwrap_or(0.0),
        })
    }
}
