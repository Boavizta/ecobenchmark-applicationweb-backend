CREATE TABLE result
(
    service varchar(64),
    use_case varchar(128),
    run_id	integer primary key,
    valid boolean,
    vus integer,
    iterations  integer,
    http_reqs integer,
    checks_passes integer,
    checks_fails integer,
    excludes boolean default false,
    application_ram_avg decimal,
    application_ram_median numeric,
    application_cpu_avg decimal,
    application_cpu_median numeric,
    application_energy numeric,
    database_ram_avg decimal,
    database_ram_median numeric,
    database_cpu_avg decimal,
    database_cpu_median numeric,
    database_energy numeric
);





