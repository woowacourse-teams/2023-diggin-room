alter table room add column description varchar(500);
alter table room add column artist varchar(255);
alter table room add column sub_genres varchar(255);
alter table room add column super_genre varchar(255);
alter table room add column title varchar(255);

alter table room add column identifier varchar(255);

drop temporary table new_room;
CREATE TEMPORARY TABLE new_room(
   id              bigint auto_increment
       primary key,
   scrap_count     bigint default 0 not null,
   created_at timestamp(6) null,
   updated_at timestamp(6) null,
   description     varchar(500)     null,
   artist          varchar(255)     null,
   sub_genres      varchar(255)     null,
   super_genre     varchar(255)     null,
   title           varchar(255)     null,
   identifier      varchar(255)     null
);
insert into new_room select room.id as id, room.scrap_count, room.created_at, room.updated_at, t.description, t.artist, t.sub_genres, t.super_genre, t.title as title, m.identifier
from room,media_source m,track t
where room.media_source_id = m.id and room.track_id = t.id;

SET FOREIGN_KEY_CHECKS = 0;

alter table room drop constraint FKh4o8jqamvtubinly1vjviijnd;
alter table room drop constraint FKt3qfwfkqav3hfmomfccjs4b6a;
alter table room drop media_source_id;
alter table room drop track_id;

truncate table room;

insert into room select * from new_room;

drop table track;
drop table media_source;

drop temporary table new_room;
SET FOREIGN_KEY_CHECKS = 1;
