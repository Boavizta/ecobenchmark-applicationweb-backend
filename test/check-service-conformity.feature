Feature:

Background:
  * def now = function(){ return java.lang.System.currentTimeMillis() }
  * def account_name = 'Conformity-' + now()
  * url 'http://localhost:8080/'

Scenario:
  Given path 'healthcheck'
  When method head

Scenario: Simple Conformity Scenario - API Behavior and Cache agressive check
  # Create an account
  Given path 'api/accounts'
  And header Content-Type = 'application/json'
  And request { login: #(account_name) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, login: #(account_name), creation_date: #notnull }
  Then def account_id = response.id
  # Create list linked to the account
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And def list_name = 'List-' + now()
  And request { name: #(list_name) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(list_name), creation_date: #notnull , account_id: #(account_id)}
  Then def list_id = response.id
  # Create 1 task on the list
  Given path 'api/lists',list_id,'tasks'
  And header Content-Type = 'application/json'
  And def task_name = 'Task-' + now()
  And def task_description = 'Description-' + now()
  And request { name: #(task_name), description: #(task_description) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(task_name), description: #(task_description) , creation_date: #notnull , list_id: #(list_id)}
  Then def task_id = response.id
  # Check current list content
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And param page = 0
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then match response[0] == { id: #(list_id), name: #(list_name), creation_date: #notnull , account_id: #(account_id), tasks: #notnull }
  Then match response[0].tasks[0] == { id: #(task_id), name: #(task_name), description: #(task_description), creation_date: #notnull }
  # Check stats
  Given path 'api/stats'
  And header Content-Type = 'application/json'
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then def sub_result = karate.filter(response, x => { return x.account_id == account_id } ) 
  Then match sub_result[0] == { account_id: #(account_id), account_login: #(account_name), list_count:1, task_avg:1 }
  # Create a 2nd task on the list
  Given path 'api/lists',list_id,'tasks'
  And header Content-Type = 'application/json'
  And def task_name_2 = 'Task-' + now()
  And def task_description_2 = 'Description-' + now()
  And request { name: #(task_name_2), description: #(task_description_2) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(task_name_2), description: #(task_description_2) , creation_date: #notnull , list_id: #(list_id)}
  Then def task_id_2 = response.id
  # Check current list content - should be 2 tasks
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
  # Create a 2nd list on the account
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And def list_name_2 = 'List-' + now()
  And request { name: #(list_name_2) }
  When method post
  Then match [200, 201, 204] contains responseStatus
  Then match response == { id: #uuid, name: #(list_name_2), creation_date: #notnull , account_id: #(account_id)}
  Then def list_id_2 = response.id
  # Check current lists content - should be 2 tasks
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And param page = 0
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then def lr1 = karate.filter(response, x => { return x.id == list_id } )
  Then match lr1[0] == { id: #(list_id), name: #(list_name), creation_date: #notnull , account_id: #(account_id), tasks: #notnull }
  Then def r = karate.filter(lr1[0].tasks, x => { return x.id == task_id } )
  Then match r[0] == { id: #(task_id), name: #(task_name), description: #(task_description), creation_date: #notnull }
  Then def r = karate.filter(lr1[0].tasks, x => { return x.id == task_id_2 } )
  Then match r[0] == { id: #(task_id_2), name: #(task_name_2), description: #(task_description_2), creation_date: #notnull }
  Then def lr2 = karate.filter(response, x => { return x.id == list_id_2 } )
  Then match lr2[0] == { id: #(list_id_2), name: #(list_name_2), creation_date: #notnull , account_id: #(account_id) }
   # Create 1 task on the 2nd list
   Given path 'api/lists',list_id_2,'tasks'
   And header Content-Type = 'application/json'
   And request { name: #(task_name), description: #(task_description) }
   When method post
   Then match [200, 201, 204] contains responseStatus
   Then match response == { id: #uuid, name: #(task_name), description: #(task_description) , creation_date: #notnull , list_id: #(list_id_2)}
   Then def task_id_1_2 = response.id
  # Check current lists content - should be 2 tasks
  Given path 'api/accounts',account_id,'lists'
  And header Content-Type = 'application/json'
  And param page = 0
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then def lr1 = karate.filter(response, x => { return x.id == list_id } )
  Then match lr1[0] == { id: #(list_id), name: #(list_name), creation_date: #notnull , account_id: #(account_id), tasks: #notnull }
  Then def r = karate.filter(lr1[0].tasks, x => { return x.id == task_id } )
  Then match r[0] == { id: #(task_id), name: #(task_name), description: #(task_description), creation_date: #notnull }
  Then def r = karate.filter(lr1[0].tasks, x => { return x.id == task_id_2 } )
  Then match r[0] == { id: #(task_id_2), name: #(task_name_2), description: #(task_description_2), creation_date: #notnull }
  Then def lr2 = karate.filter(response, x => { return x.id == list_id_2 } )
  Then match lr2[0] == { id: #(list_id_2), name: #(list_name_2), creation_date: #notnull , account_id: #(account_id) , tasks: #notnull }
  Then def r = karate.filter(lr2[0].tasks, x => { return x.id == task_id_1_2 } )
  Then match r[0] == { id: #(task_id_1_2), name: #(task_name), description: #(task_description), creation_date: #notnull }
  # Check stats
  Given path 'api/stats'
  And header Content-Type = 'application/json'
  When method get
  Then match [200, 201, 204] contains responseStatus
  Then def sub_result = karate.filter(response, x => { return x.account_id == account_id } ) 
  Then match sub_result[0] == { account_id: #(account_id), account_login: #(account_name), list_count:2, task_avg:1.5 }