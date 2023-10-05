Feature:

Background:
  * def now = function(){ return java.lang.System.currentTimeMillis() }
  * def account_name = 'Conformity-' + now()
  * url 'http://localhost:8080/'

Scenario:
  * path 'healthcheck'
  * method head

Scenario: Simple Conformity Scenario
  Given path 'api/accounts'
  And header Content-Type = 'application/json'
  And request { login: #(account_name) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, login: #(account_name), creation_date: #notnull }
  Then def account_id = response.id
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And def list_name = 'List-' + now()
  And request { name: #(list_name) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(list_name), creation_date: #notnull , account_id: #(account_id)}
  Then def list_id = response.id
  Given path 'api/lists',list_id,'tasks'
  And header Content-Type = 'application/json'
  And def task_name = 'Task-' + now()
  And def task_description = 'Description-' + now()
  And request { name: #(task_name), description: #(task_description) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(task_name), description: #(task_description) , creation_date: #notnull , list_id: #(list_id)}
  Then def task_id = response.id
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And param page = 0
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then match response[0] == { id: #(list_id), name: #(list_name), creation_date: #notnull , account_id: #(account_id), tasks: #notnull }
  Then match response[0].tasks[0] == { id: #(task_id), name: #(task_name), description: #(task_description), creation_date: #notnull }
  Given path 'api/stats'
  And header Content-Type = 'application/json'
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then def sub_result = karate.filter(response, x => { return x.account_id == account_id } ) 
  Then match sub_result[0] == { account_id: #(account_id), account_login: #(account_name), list_count:1, task_avg:1 }
  Given path 'api/lists',list_id,'tasks'
  And header Content-Type = 'application/json'
  And def task_name_2 = 'Task-' + now()
  And def task_description_2 = 'Description-' + now()
  And request { name: #(task_name_2), description: #(task_description_2) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(task_name_2), description: #(task_description_2) , creation_date: #notnull , list_id: #(list_id)}
  Then def task_id_2 = response.id
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And param page = 0
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then match response[0] == { id: #(list_id), name: #(list_name), creation_date: #notnull , account_id: #(account_id), tasks: #notnull }
  Then def r = karate.filter(response[0].tasks, x => { return x.id == task_id } )
  Then match r[0] == { id: #(task_id), name: #(task_name), description: #(task_description), creation_date: #notnull }
  Then def r = karate.filter(response[0].tasks, x => { return x.id == task_id_2 } )
  Then match r[0] == { id: #(task_id_2), name: #(task_name_2), description: #(task_description_2), creation_date: #notnull }