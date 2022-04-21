## How to run
`go run main.go`

Port 8080

Environment variable `POSTGRESQL_CONNECTION_URI` needs to be set.



## Env configuration :

`docker run --name some-postgres  -p 5432:5432  -e POSTGRES_PASSWORD=mysecretpassword -d postgres`

`POSTGRESQL_CONNECTION_URI=postgresql://postgres:mysecretpassword@127.0.0.1:5432/postgres?search_path=ecobenchmark`
 


    ➜  ~ curl -i -X POST http://localhost:8080/api/account -H "Content-Type: application/json"  -d '{"login":"toto"}'

    HTTP/1.1 201 Created
    Content-Type: application/json; charset=UTF-8
    Date: Fri, 15 Apr 2022 08:42:50 GMT
    Content-Length: 115

    {"id":"fcbf7d89-b611-405d-89cb-9688a965b78d","name":"toto","creation_date":"2022-04-15 08:42:49.769601 +0000 UTC"}

----

    curl -i -X POST http://localhost:8080/api/list -H "Content-Type: application/json"  -d '{"name":"list 1", "account_id":"f7d36f5e-ecba-4255-91ae-d817bcd0f1bc"}'                                                                       2 ↵ ──(Mon,Apr18)─┘
    HTTP/1.1 201 Created
    Content-Type: application/json; charset=UTF-8
    Date: Mon, 18 Apr 2022 17:34:50 GMT
    Content-Length: 117

    {"id":"cc052192-f329-44ea-b3dc-4d0f8115aff6","name":"list 1","creation_date":"2022-04-18 17:34:50.264097 +0000 UTC"}

----

    curl -i -X POST http://localhost:8080/api/list/88b1108b-c6ea-458b-8bb2-08225f70300a/task -H "Content-Type: application/json"  -d '{"name":"task 1", "description":"des"}'

    HTTP/1.1 201 Created
    Content-Type: application/json; charset=UTF-8
    Date: Tue, 19 Apr 2022 06:40:09 GMT
    Content-Length: 194

    {"id":"a49f3a3f-c91d-4dc5-a058-d582dbda9495","name":"task 1","description":"description","creation_date":"2022-04-19 06:40:09.688522 +0000 UTC","list_id":"88b1108b-c6ea-458b-8bb2-08225f70300a"}
----
    curl -X GET http://localhost:8080/api/account/f7d36f5e-ecba-4255-91ae-d817bcd0f1bc/list/\?page\=0  | jq
----
    curl -X GET http://localhost:8080/api/account/f7d36f5e-ecba-4255-91ae-d817bcd0f1bc/list/\?page\=1  | jq
----
    curl -X GET http://localhost:8080/api/stats  | jq                                                      
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
    Dload  Upload   Total   Spent    Left  Speed
    100   111  100   111    0     0    653      0 --:--:-- --:--:-- --:--:--   707
    [
        {
            "account_id": "f7d36f5e-ecba-4255-91ae-d817bcd0f1bc",
            "account_login": "toto",
            "list_count": 12,
            "task_avg": 1.83
        }
    ]
----