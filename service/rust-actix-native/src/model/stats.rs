use crate::service::database::{Client, Error};
use std::convert::TryFrom;
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
    pub async fn list(client: &mut Client) -> Result<Vec<AccountStat>, Error> {
        let stmt = client
            .prepare(
                r#"
            select id, login, count(list_id) as nb_list, round(avg(nb_tasks), 2)::float as avg_tasks
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
            .await?;
        let rows = client.query(&stmt, &[]).await?;
        rows.into_iter().map(AccountStat::try_from).collect()
    }
}

impl TryFrom<tokio_postgres::Row> for AccountStat {
    type Error = tokio_postgres::Error;

    fn try_from(value: tokio_postgres::Row) -> Result<Self, Self::Error> {
        let list_count: i64 = value.try_get(2)?;
        Ok(Self {
            account_id: value.try_get(0)?,
            account_login: value.try_get(1)?,
            list_count: list_count as u64,
            task_avg: value.try_get(3)?,
        })
    }
}
