use crate::error::Error;
use crate::model::list::list::{ListList, ListWithTasks};
use crate::service::database::Pool;
use axum::extract::{Extension, Json, Path, Query};
use uuid::Uuid;

pub async fn handle(
    Path(account_id): Path<Uuid>,
    Extension(pool): Extension<Pool>,
    Query(payload): Query<ListList>,
) -> Result<Json<Vec<ListWithTasks>>, Error> {
    let mut conn = pool.acquire().await?;
    let result = payload.execute(&mut conn, account_id).await?;
    Ok(Json(result))
}
