create table feedback
(
    id         bigint auto_increment primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    writer_id  bigint       null,
    content    varchar(255) null,
    constraint fk_feedback_writer
        foreign key (writer_id) references member (id)
);
