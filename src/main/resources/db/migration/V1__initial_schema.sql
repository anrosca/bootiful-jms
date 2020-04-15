create sequence report_sequence start with 100;

create table report
(
    id                 bigint default nextval('report_sequence') primary key,
    file_name          varchar(255) not null,
    creation_timestamp timestamp    not null,
    content            bytea         not null
);

create sequence city_sequence start with 100;

create table city
(
    id        bigint default nextval('city_sequence') primary key,
    city_name varchar(100) not null
);

create sequence report_config_sequence start with 100;

create table report_config
(
    id           bigint default nextval('report_config_sequence') primary key,
    city_id      bigint      not null references city (id),
    trigger_type varchar(10) not null
);
