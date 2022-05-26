# Use case code in Golang using pgx for db connection

## How to run

Environment variable `DATABASE_URL` needs to be set.

`go run main.go`

Then it listen on port 8080

http://localhost:8080

## Development Environment setup

See the readme in the root folder.

## GRPC regeneration

### Mac OS Installation

`brew install protobuf`

`brew install grpc`

`go install google.golang.org/protobuf/cmd/protoc-gen-go@latest`

`go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest`

`export PATH="$PATH:$(go env GOPATH)/bin"`

### Generation

`protoc --go_out=. --go_opt=paths=source_relative \\n    --go-grpc_out=. --go-grpc_opt=paths=source_relative \\n    endpoints/endpoints.proto`

## MAC OS GRPC CLient

`brew install --cask bloomrpc`