create table tb_car
(
    id           uuid                  not null,
    model        varchar(255)          not null,
    make_id      int8                  not null,
    available    boolean default false not null,
    created_at   timestamp,
    created_by   varchar(100),
    updated_at   timestamp,
    deleted_tmsp timestamp,
    primary key (id)
);
create sequence tb_make_id_seq start 10000 increment 1;
create table tb_make
(
    id           int8         not null DEFAULT nextval('tb_make_id_seq'),
    name         varchar(255) not null unique,
    created_at   timestamp default current_timestamp,
    created_by   varchar(100),
    updated_at   timestamp default current_timestamp,
    deleted_tmsp timestamp,
    primary key (id)
);
alter table tb_car
    add constraint FK45thtdx367n3ig2nfm2pka98e foreign key (make_id) references tb_make;
