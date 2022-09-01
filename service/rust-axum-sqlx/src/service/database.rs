use sqlx::postgres::PgPoolOptions;

pub type Pool = sqlx::PgPool;

fn database_url() -> String {
    std::env::var("DATABASE_URL").unwrap_or_else(|_| "postgres://localhost/postgres".into())
}

fn pool_max() -> u32 {
    std::env::var("DATABASE_POOL_MAX")
        .ok()
        .and_then(|value| value.parse::<u32>().ok())
        .unwrap_or(5)
}

pub async fn create_pool() -> sqlx::PgPool {
    let url = database_url();
    PgPoolOptions::new()
        .max_connections(pool_max())
        .connect(url.as_str())
        .await
        .expect("unable to create postgres pool")
}
