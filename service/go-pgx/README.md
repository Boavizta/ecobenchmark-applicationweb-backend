# Use case code in Golang using pgx for db connection

## How to run

Environment variable `POSTGRESQL_CONNECTION_URI` needs to be set.

`go run main.go`

Then it listen on port 8080

http://localhost:8080

## Development Environment setup

`docker run --name some-postgres  -p 5432:5432  -e POSTGRES_PASSWORD=mysecretpassword -d postgres`

`POSTGRESQL_CONNECTION_URI=postgresql://postgres:mysecretpassword@127.0.0.1:5432/postgres?search_path=ecobenchmark`

And init the db with sql in ../../migrations folder.
 
## API usage example

    curl -I http://localhost:8080/healthcheck   

    HTTP/1.1 200 OK
    Content-Type: text/plain; charset=UTF-8
    Date: Fri, 29 Apr 2022 05:51:42 GMT

----

    curl -i -X POST http://localhost:8080/api/accounts -H "Content-Type: application/json"  -d '{"login":"toto"}'

    HTTP/1.1 201 Created
    Content-Type: application/json; charset=UTF-8
    Date: Fri, 15 Apr 2022 08:42:50 GMT
    Content-Length: 115

    {"id":"fcbf7d89-b611-405d-89cb-9688a965b78d","name":"toto","creation_date":"2022-04-15 08:42:49.769601 +0000 UTC"}

----

    curl -i -X POST http://localhost:8080/api/lists -H "Content-Type: application/json"  -d '{"name":"list 1", "account_id":"f7d36f5e-ecba-4255-91ae-d817bcd0f1bc"}'

    HTTP/1.1 201 Created
    Content-Type: application/json; charset=UTF-8
    Date: Mon, 18 Apr 2022 17:34:50 GMT
    Content-Length: 117

    {"id":"cc052192-f329-44ea-b3dc-4d0f8115aff6","name":"list 1","creation_date":"2022-04-18 17:34:50.264097 +0000 UTC"}

----

    curl -i -X POST http://localhost:8080/api/lists/88b1108b-c6ea-458b-8bb2-08225f70300a/tasks -H "Content-Type: application/json"  -d '{"name":"task 1", "description":"des"}'

    HTTP/1.1 201 Created
    Content-Type: application/json; charset=UTF-8
    Date: Tue, 19 Apr 2022 06:40:09 GMT
    Content-Length: 194

    {"id":"a49f3a3f-c91d-4dc5-a058-d582dbda9495","name":"task 1","description":"description","creation_date":"2022-04-19 06:40:09.688522 +0000 UTC","list_id":"88b1108b-c6ea-458b-8bb2-08225f70300a"}
----
    curl -X GET http://localhost:8080/api/accounts/f7d36f5e-ecba-4255-91ae-d817bcd0f1bc/lists/\?page\=0  | jq
----
    curl -X GET http://localhost:8080/api/accounts/f7d36f5e-ecba-4255-91ae-d817bcd0f1bc/lists/\?page\=1  | jq
----
    curl -X GET http://localhost:8080/api/stats  | jq

    [
        {
            "account_id": "f7d36f5e-ecba-4255-91ae-d817bcd0f1bc",
            "account_login": "toto",
            "list_count": 12,
            "task_avg": 1.83
        }
    ]
----