pub use deadpool_postgres::Pool;
use deadpool_postgres::{Config, ManagerConfig, PoolConfig, RecyclingMethod, Runtime};
use tokio_postgres::NoTls;
pub use tokio_postgres::{Client, Error};
use url::Url;

fn database_url() -> Url {
    let url =
        std::env::var("DATABASE_URL").unwrap_or_else(|_| "postgres://localhost/postgres".into());
    Url::parse(&url).expect("invalid database url")
}

fn pool_max() -> usize {
    std::env::var("DATABASE_POOL_MAX")
        .ok()
        .and_then(|value| value.parse::<usize>().ok())
        .unwrap_or(5)
}

pub async fn create_pool() -> Pool {
    let url = database_url();
    let mut cfg = Config::new();
    cfg.dbname = Some(url.path().trim_matches('/').to_string());
    cfg.manager = Some(ManagerConfig {
        recycling_method: RecyclingMethod::Fast,
    });
    cfg.pool = Some(PoolConfig {
        max_size: pool_max(),
        ..Default::default()
    });
    cfg.host = url.host().map(|v| v.to_string());
    cfg.port = url.port();
    cfg.user = match url.username() {
        "" => None,
        other => Some(other.to_string()),
    };
    cfg.password = url.password().map(ToString::to_string);
    cfg.create_pool(Some(Runtime::Tokio1), NoTls).unwrap()
}
