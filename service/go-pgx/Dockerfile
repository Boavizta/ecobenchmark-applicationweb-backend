FROM golang:1.17.5-bullseye

WORKDIR /workdir
ENV GOOS=linux\
    GOARCH=amd64\
    CGO_ENABLED=0
COPY go.mod go.mod
COPY go.sum go.sum
RUN go mod download
COPY . .
RUN go build -o out/app main.go

FROM scratch

WORKDIR /workdir
COPY --from=0 /workdir/out/app app

CMD ["/workdir/app"]
