use crate::error::Error;
use crate::model::task::{create::TaskCreate, Task};
use crate::service::database::Pool;
use axum::extract::{Extension, Json, Path};
use axum::http::StatusCode;
use uuid::Uuid;

pub async fn handle(
    Extension(pool): Extension<Pool>,
    Path(list_id): Path<Uuid>,
    Json(payload): Json<TaskCreate>,
) -> Result<(StatusCode, Json<Task>), Error> {
    let mut conn = pool.acquire().await?;
    let created = payload.execute(&mut conn, list_id).await?;
    Ok((StatusCode::CREATED, Json(created)))
}
