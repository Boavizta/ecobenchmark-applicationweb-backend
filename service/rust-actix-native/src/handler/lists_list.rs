use crate::error::Error;
use crate::model::list::list::ListList;
use crate::service::database::Pool;
use actix_web::{get, web, HttpResponse};
use uuid::Uuid;

#[get("/api/accounts/{account_id}/lists")]
async fn handler(
    pool: web::Data<Pool>,
    account_id: web::Path<Uuid>,
    payload: web::Query<ListList>,
) -> Result<HttpResponse, Error> {
    let mut conn = pool.get().await?;
    let result = payload.execute(&mut conn, account_id.into_inner()).await?;
    Ok(HttpResponse::Ok().json(result))
}
