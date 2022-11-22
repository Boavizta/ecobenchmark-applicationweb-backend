use crate::error::Error;
use crate::model::list::{create::ListCreate, List};
use crate::service::database::Pool;
use axum::extract::{Extension, Path, Json};
use axum::http::StatusCode;
use uuid::Uuid;

pub async fn handle(
    Path(account_id): Path<Uuid>,
    Extension(pool): Extension<Pool>,
    Json(payload): Json<ListCreate>,
) -> Result<(StatusCode, Json<List>), Error> {
    let mut conn = pool.acquire().await?;
    let created = payload.execute(&mut conn, account_id).await?;
    Ok((StatusCode::CREATED, Json(created)))
}
