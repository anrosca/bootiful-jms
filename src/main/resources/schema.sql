create table report_config (
    id            int primary key,
    report_code   varchar(6)  not null,
    trigger_type  varchar(10) not null,
    report_format varchar(3)  not null
);
