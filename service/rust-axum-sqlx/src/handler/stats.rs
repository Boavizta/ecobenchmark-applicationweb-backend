use crate::error::Error;
use crate::model::stats::AccountStat;
use crate::service::database::Pool;
use axum::extract::{Extension, Json};

pub async fn handle(Extension(pool): Extension<Pool>) -> Result<Json<Vec<AccountStat>>, Error> {
    let mut conn = pool.acquire().await?;
    let result = AccountStat::list(&mut conn).await?;
    Ok(Json(result))
}
