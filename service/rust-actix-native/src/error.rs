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

impl From<deadpool_postgres::PoolError> for Error {
    fn from(err: deadpool_postgres::PoolError) -> Self {
        eprintln!("unable to get pool connection: {:?}", err);
        Self {
            code: StatusCode::INTERNAL_SERVER_ERROR,
            message: "Internal server error.".into(),
        }
    }
}

impl From<tokio_postgres::Error> for Error {
    fn from(err: tokio_postgres::Error) -> Self {
        eprintln!("unable to execute query: {:?}", err);
        Self {
            code: StatusCode::INTERNAL_SERVER_ERROR,
            message: "Internal server error.".into(),
        }
    }
}
