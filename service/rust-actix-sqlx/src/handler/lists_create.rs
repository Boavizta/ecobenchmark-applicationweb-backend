use crate::error::Error;
use crate::model::list::create::ListCreate;
use crate::service::database::Pool;
use actix_web::{post, web, HttpResponse};

#[post("/api/lists")]
async fn handler(
    pool: web::Data<Pool>,
    payload: web::Json<ListCreate>,
) -> Result<HttpResponse, Error> {
    let mut conn = pool.acquire().await?;
    let created = payload.execute(&mut conn).await?;
    Ok(HttpResponse::Ok().json(created))
}
