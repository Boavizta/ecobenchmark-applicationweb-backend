use actix_web::{web, App, HttpServer};

mod error;
mod handler;
mod model;
mod service;

fn binding() -> (String, u16) {
    let host = std::env::var("ADDRESS").unwrap_or_else(|_| "localhost".into());
    let port = std::env::var("PORT")
        .ok()
        .and_then(|port| port.parse::<u16>().ok())
        .unwrap_or(3000);
    (host, port)
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let pool = web::Data::new(service::database::create_pool().await);

    HttpServer::new(move || {
        App::new()
            .app_data(pool.clone())
            .service(crate::handler::health::handler)
            .service(crate::handler::accounts_create::handler)
            .service(crate::handler::lists_create::handler)
            .service(crate::handler::lists_list::handler)
            .service(crate::handler::tasks_create::handler)
            .service(crate::handler::stats::handler)
    })
    .bind(binding())?
    .run()
    .await
}
