use crate::error::Error;
use crate::model::list::list::ListList;
use crate::service::database::Pool;
use actix_web::{get, web, HttpResponse};
use uuid::Uuid;

#[get("/api/accounts/{account_id}/lists")]
async fn handler(
    pool: web::Data<Pool>,
    account_id: web::Path<Uuid>,
) -> Result<HttpResponse, Error> {
    let mut conn = pool.acquire().await?;
    let result = ListList::execute(&mut conn, account_id.into_inner()).await?;
    Ok(HttpResponse::Ok().json(result))
}
