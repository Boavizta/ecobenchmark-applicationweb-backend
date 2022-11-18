use crate::error::Error;
use crate::model::stats::AccountStat;
use crate::service::database::Pool;
use actix_web::{get, web, HttpResponse};

#[get("/api/stats")]
async fn handler(pool: web::Data<Pool>) -> Result<HttpResponse, Error> {
    let mut conn = pool.get().await?;
    let result = AccountStat::list(&mut conn).await?;
    Ok(HttpResponse::Ok().json(result))
}
