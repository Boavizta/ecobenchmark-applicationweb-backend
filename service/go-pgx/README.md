## How to run
`go run main.go`

Port 8080

Environment variable `POSTGRESQL_CONNECTION_URI` needs to be set.



## Env configuration :

`docker run --name some-postgres  -p 5432:5432  -e POSTGRES_PASSWORD=mysecretpassword -d postgres`

`POSTGRESQL_CONNECTION_URI=postgresql://postgres:mysecretpassword@127.0.0.1:5432/postgres?search_path=ecobenchmark`
 



----
➜  ~ curl -i -X POST http://localhost:8080/api/account -H "Content-Type: application/json"  -d '{"login":"toto"}'
HTTP/1.1 201 Created
Content-Type: application/json; charset=UTF-8
Date: Fri, 15 Apr 2022 08:42:50 GMT
Content-Length: 115

{"id":"fcbf7d89-b611-405d-89cb-9688a965b78d","name":"toto","creation_date":"2022-04-15 08:42:49.769601 +0000 UTC"}
----

----
curl -i -X POST http://localhost:8080/api/list -H "Content-Type: application/json"  -d '{"name":"list 1"}'                                                                       2 ↵ ──(Mon,Apr18)─┘
HTTP/1.1 201 Created
Content-Type: application/json; charset=UTF-8
Date: Mon, 18 Apr 2022 17:34:50 GMT
Content-Length: 117

{"id":"cc052192-f329-44ea-b3dc-4d0f8115aff6","name":"list 1","creation_date":"2022-04-18 17:34:50.264097 +0000 UTC"}
----