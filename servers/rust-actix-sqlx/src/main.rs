use actix_web::{get, App, HttpServer, Responder};

fn binding() -> (String, u16) {
    let host = std::env::var("ADDRESS").unwrap_or_else(|_| "localhost".into());
    let port = std::env::var("PORT")
        .ok()
        .and_then(|port| port.parse::<u16>().ok())
        .unwrap_or(3000);
    (host, port)
}

#[get("/_healthcheck")]
async fn healthcheck() -> impl Responder {
    "up"
}

#[actix_web::main] // or #[tokio::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| App::new().service(healthcheck))
        .bind(binding())?
        .run()
        .await
}
