use actix_web::{head, HttpResponse};

#[head("/healthcheck")]
async fn handler() -> HttpResponse {
    HttpResponse::NoContent().finish()
}
