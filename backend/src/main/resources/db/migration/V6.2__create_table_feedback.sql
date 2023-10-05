create table feedback
(
    created_at datetime(6)  null,
    id         bigint auto_increment primary key,
    updated_at datetime(6)  null,
    content    varchar(255) null
)
