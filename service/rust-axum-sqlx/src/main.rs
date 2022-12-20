use std::net::{SocketAddr, IpAddr};
use axum::Server;

mod handler;
mod model;
mod error;
mod service;

fn binding() -> SocketAddr {
    let host = std::env::var("ADDRESS").unwrap_or_else(|_| "127.0.0.1".into());
    let host = host.parse::<IpAddr>().unwrap();
    let port = std::env::var("PORT")
        .ok()
        .and_then(|port| port.parse::<u16>().ok())
        .unwrap_or(3000);
        SocketAddr::new(host, port)
}

#[tokio::main]
async fn main() {
    let addr = binding();
    let db = service::database::create_pool().await;

    Server::bind(&addr)
        .serve(handler::router(db).into_make_service())
        .await
        .unwrap();
}
