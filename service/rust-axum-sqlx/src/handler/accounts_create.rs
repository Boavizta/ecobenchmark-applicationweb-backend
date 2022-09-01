use crate::error::Error;
use crate::model::account::{create::AccountCreate, Account};
use crate::service::database::Pool;
use axum::extract::{Extension, Json};
use axum::http::StatusCode;

pub async fn handle(
    Extension(pool): Extension<Pool>,
    Json(payload): Json<AccountCreate>,
) -> Result<(StatusCode, Json<Account>), Error> {
    let mut conn = pool.acquire().await?;
    let created = payload.execute(&mut conn).await?;
    Ok((StatusCode::CREATED, Json(created)))
}
