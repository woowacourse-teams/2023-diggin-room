create table comment
(
    created_at timestamp(6),
    id         bigint auto_increment,
    member_id  bigint,
    room_id    bigint,
    updated_at timestamp(6),
    comment    longtext,
    primary key (id)
)
