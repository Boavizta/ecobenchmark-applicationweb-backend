use crate::error::Error;
use crate::model::task::create::TaskCreate;
use crate::service::database::Pool;
use actix_web::{post, web, HttpResponse};
use uuid::Uuid;

#[post("/api/lists/{list_id}/tasks")]
async fn handler(
    pool: web::Data<Pool>,
    list_id: web::Path<Uuid>,
    payload: web::Json<TaskCreate>,
) -> Result<HttpResponse, Error> {
    let mut conn = pool.acquire().await?;
    let created = payload.execute(&mut conn, list_id.into_inner()).await?;
    Ok(HttpResponse::Created().json(created))
}
