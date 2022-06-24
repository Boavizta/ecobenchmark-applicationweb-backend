use std::collections::{HashMap, HashSet};
use sqlx::postgres::PgRow;
use sqlx::Row;
use uuid::Uuid;

#[derive(serde::Serialize)]
pub struct AccountStat {
    account_id: Uuid,
    account_login: String,
    list_count: usize,
    task_avg: f64,
}

#[derive(serde::Serialize)]
pub struct AccountRow {
    pub account_id: Uuid,
    pub account_login: String,
    pub list_id: Uuid,
    pub task_id: Option<Uuid>,
}

impl AccountStat {
    pub async fn list<'e, E: sqlx::Executor<'e, Database = sqlx::Postgres>>(
        executor: E,
    ) -> Result<Vec<AccountStat>, sqlx::Error> {
        let rows: Vec<AccountRow> = sqlx::query_as(
            r#"
            select account.id, account.login, list.id list_id, task.id task_id 
            from account
            inner join list on (list.account_id=account.id)
            left join task on (task.list_id=list.id)
            "#,
        )
        .fetch_all(executor)
        .await?;
        
        let mut mapping: HashMap<Uuid, (String, HashMap<Uuid, HashSet<Uuid>>)> = HashMap::new();
        
        for row in rows.into_iter() {
            let account_entry = mapping.entry(row.account_id).or_insert((row.account_login, HashMap::new()));
            let list_entry = account_entry.1.entry(row.list_id).or_insert(HashSet::new());
            if let Some(task_id) = row.task_id {
                list_entry.insert(task_id);
            }
        }

        Ok(mapping.into_iter().map(|(account_id, (account_login, lists))| {
            let task_count: usize = lists.values().map(|tasks| tasks.len()).sum();
            let list_count = lists.len();
            let task_avg = (task_count as f64) / (list_count as f64);
            AccountStat {
                account_id,
                account_login,
                list_count,
                task_avg,
            }
        }).collect())
    }
}

impl<'r> sqlx::FromRow<'r, PgRow> for AccountRow {
    fn from_row(row: &'r PgRow) -> Result<Self, sqlx::Error> {
        Ok(Self {
            account_id: row.get(0),
            account_login: row.get(1),
            list_id: row.get(2),
            task_id: row.get(3),
        })
    }
}
