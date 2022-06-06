# Request result documentation

## Import

`COPY result(service,use_case,run_id,valid,vus,iterations,http_reqs,checks_passes,checks_fails,application_ram_avg,application_ram_median,application_cpu_avg,application_cpu_median,application_energy,database_ram_avg,database_ram_median,database_cpu_avg,database_cpu_median,database_energy)
FROM '/Users/youen/eco-bench-results.csv'
DELIMITER ','
CSV HEADER;`


## Requests

### No filter
`select service,use_case,vus,avg(energy),stddev_pop(energy)/avg(energy) incertitude,avg(iterations),(((avg(energy)/avg(iterations))/1000000)/3600) wh_by_it
from ( select run_id, service, use_case, vus, iterations, checks_fails, (application_energy + database_energy) energy from result where excludes=false) r
group by service,use_case,vus order by vus, service, use_case ASC`

###  Filter by failure > 0

`select service,use_case,vus,avg(energy) microjoules,stddev_pop(energy)/avg(energy) incertitude,avg(iterations) iterations,(((avg(energy)/avg(iterations))/1000000)/3600) wh_by_iteration
from ( select run_id, service, use_case, vus, iterations, checks_fails, (application_energy + database_energy) energy from result where excludes=false) r
WHERE checks_fails = 0
group by service,use_case,vus order by vus, service, use_case ASC`