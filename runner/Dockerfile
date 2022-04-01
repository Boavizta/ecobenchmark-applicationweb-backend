FROM rust:1.58.1-bullseye AS builder

RUN cargo init --bin code

WORKDIR /code

COPY Cargo.toml Cargo.lock /code/

RUN mkdir -p /code/.cargo
RUN cargo vendor > /code/.cargo/config.toml

COPY src /code/src

RUN cargo build --release --offline

FROM scratch

COPY --from=builder /code/target/release/eco-bench-runner /eco-bench-runner
