use actix_web::http::StatusCode;
use actix_web::{HttpResponse, HttpResponseBuilder, ResponseError};

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

impl ResponseError for Error {
    fn status_code(&self) -> StatusCode {
        self.code
    }

    fn error_response(&self) -> HttpResponse {
        HttpResponseBuilder::new(self.code).json(&serde_json::json!({
            "message": self.message
        }))
    }
}

impl From<sqlx::Error> for Error {
    fn from(err: sqlx::Error) -> Self {
        match err {
            sqlx::Error::RowNotFound => Self {
                code: StatusCode::NOT_FOUND,
                message: "Entity not found.".into(),
            },
            sqlx::Error::Database(inner) => {
                // violates foreign key
                let invalid_constraint =
                    inner.code().map(|v| v.as_ref() == "23503").unwrap_or(false);
                if invalid_constraint {
                    Self {
                        code: StatusCode::NOT_FOUND,
                        message: "Entity not found".into(),
                    }
                } else {
                    Self {
                        code: StatusCode::INTERNAL_SERVER_ERROR,
                        message: "Internal server error.".into(),
                    }
                }
            }
            _ => Self {
                code: StatusCode::INTERNAL_SERVER_ERROR,
                message: "Internal server error.".into(),
            },
        }
    }
}
