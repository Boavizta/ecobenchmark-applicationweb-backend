use crate::error::Error;
use crate::model::list::create::ListCreate;
use crate::service::database::Pool;
use actix_web::{post, web, HttpResponse};
use uuid::Uuid;

#[post("/api/accounts/{account_id}/lists")]
async fn handler(
    pool: web::Data<Pool>,
    account_id: web::Path<Uuid>,
    payload: web::Json<ListCreate>,
) -> Result<HttpResponse, Error> {
    let mut conn = pool.acquire().await?;
    let created = payload.execute(&mut conn, account_id.into_inner()).await?;
    Ok(HttpResponse::Created().json(created))
}
