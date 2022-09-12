ARG RUST_VERSION=1.61.0
FROM rust:${RUST_VERSION}-bullseye AS builder

ENV USER=root

WORKDIR /code
RUN cargo init
COPY Cargo.lock Cargo.toml /code/
RUN mkdir -p /code/.cargo
RUN cargo vendor >> /code/.cargo/config.toml

COPY src /code/src
RUN cargo build --release --offline

FROM gcr.io/distroless/cc-debian11

ENV ADDRESS=0.0.0.0
ENV PORT=8080

COPY --from=builder /code/target/release/rust-actix-sqlx /usr/bin/rust-actix-sqlx

ENTRYPOINT ["/usr/bin/rust-actix-sqlx"]
