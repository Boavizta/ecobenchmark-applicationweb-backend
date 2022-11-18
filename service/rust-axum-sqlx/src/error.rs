use axum::http::StatusCode;
use axum::response::{IntoResponse, Response};

#[derive(Debug)]
pub struct Error {
    code: StatusCode,
    message: String,
}

impl std::fmt::Display for Error {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(f, "Error {}: {:?}", self.code.as_u16(), self.message)
    }
}

impl From<sqlx::Error> for Error {
    fn from(err: sqlx::Error) -> Self {
        match err {
            sqlx::Error::RowNotFound => Self {
                code: StatusCode::NOT_FOUND,
                message: "Entity not found.".into(),
            },
            _ => Self {
                code: StatusCode::INTERNAL_SERVER_ERROR,
                message: "Internal server error.".into(),
            },
        }
    }
}

impl IntoResponse for Error {
    fn into_response(self) -> Response {
        (self.code, self.message).into_response()
    }
}
