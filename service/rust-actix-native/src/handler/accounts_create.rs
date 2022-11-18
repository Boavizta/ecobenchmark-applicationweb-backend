use crate::error::Error;
use crate::model::account::create::AccountCreate;
use crate::service::database::Pool;
use actix_web::{post, web, HttpResponse};

#[post("/api/accounts")]
async fn handler(
    pool: web::Data<Pool>,
    payload: web::Json<AccountCreate>,
) -> Result<HttpResponse, Error> {
    let mut conn = pool.get().await?;
    let created = payload.execute(&mut conn).await?;
    Ok(HttpResponse::Created().json(created))
}
