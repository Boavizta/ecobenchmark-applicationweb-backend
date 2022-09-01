use crate::service::database::Pool;
use axum::extract::Extension;
use axum::routing::{get, head, post};
use axum::Router;

mod accounts_create;
mod healthcheck;
mod lists_create;
mod lists_list;
mod stats;
mod tasks_create;

pub fn router(db: Pool) -> Router {
    Router::new()
        .route("/healthcheck", head(healthcheck::handle))
        .route("/api/accounts", post(accounts_create::handle))
        .route(
            "/api/accounts/:account_id/lists",
            post(lists_create::handle),
        )
        .route("/api/accounts/:account_id/lists", get(lists_list::handle))
        .route("/api/lists/:list_id/tasks", post(tasks_create::handle))
        .route("/api/stats", get(stats::handle))
        .layer(Extension(db))
}
