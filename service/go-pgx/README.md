## How to run
`go run main.go`

Port 8080

Environment variable `POSTGRESQL_CONNECTION_URI` needs to be set.

----
➜  ~ curl -i -X POST http://localhost:8080/api/account -H "Content-Type: application/json"  -d '{"login":"toto"}'
HTTP/1.1 201 Created
Content-Type: application/json; charset=UTF-8
Date: Fri, 15 Apr 2022 08:42:50 GMT
Content-Length: 115

{"id":"fcbf7d89-b611-405d-89cb-9688a965b78d","name":"toto","creation_date":"2022-04-15 08:42:49.769601 +0000 UTC"}
----