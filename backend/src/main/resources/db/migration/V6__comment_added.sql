create table comment
(
    created_at timestamp(6),
    id         bigint auto_increment,
    member_id  bigint,
    room_id    bigint,
    updated_at timestamp(6),
    comment    varchar(500),
    primary key (id),
    constraint FKmrrrpi513ssu63i2783jyiv9m foreign key (member_id) references member (id)
)
