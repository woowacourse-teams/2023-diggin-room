create table if not exists media_source
(
    id         bigint auto_increment primary key,
    identifier varchar(255) null
);

create table if not exists member
(
    id       bigint auto_increment primary key,
    password varchar(255) null,
    username varchar(255) null
);

create table if not exists member_genre
(
    id        bigint auto_increment primary key,
    weight    int      not null,
    member_id bigint   null,
    genre     enum ('AMBIENT', 'BLUES', 'CLASSICAL_MUSIC', 'COMEDY', 'COUNTRY', 'DANCE', 'ELECTRONIC', 'EXPERIMENTAL',
        'FIELD_RECORDINGS', 'FOLK', 'HIP_HOP', 'INDUSTRIAL_MUSIC', 'JAZZ', 'METAL', 'MUSICAL_THEATRE_AND_ENTERTAINMENT',
        'NEW_AGE', 'POP', 'PSYCHEDELIA', 'PUNK', 'REGIONAL_MUSIC', 'RNB', 'ROCK', 'SINGER_SONGWRITER', 'SKA', 'SOUNDS_AND_EFFECTS',
        'SPOKEN_WORD') null,
    constraint FKqkf5e9wjn44yufdx1a1v6wky5 foreign key (member_id) references member (id)
);

create table if not exists track
(
    id          bigint auto_increment primary key,
    artist      varchar(255) null,
    sub_genres  varchar(255) null,
    super_genre enum ('AMBIENT', 'BLUES', 'CLASSICAL_MUSIC', 'COMEDY', 'COUNTRY', 'DANCE', 'ELECTRONIC', 'EXPERIMENTAL',
        'FIELD_RECORDINGS', 'FOLK', 'HIP_HOP', 'INDUSTRIAL_MUSIC', 'JAZZ', 'METAL', 'MUSICAL_THEATRE_AND_ENTERTAINMENT',
        'NEW_AGE', 'POP', 'PSYCHEDELIA', 'PUNK', 'REGIONAL_MUSIC', 'RNB', 'ROCK', 'SINGER_SONGWRITER', 'SKA', 'SOUNDS_AND_EFFECTS',
        'SPOKEN_WORD')       null,
    title       varchar(255) null,
    constraint UKbaphiakqrv9kmnl6n0858bul4 unique (artist, title)
);

create table if not exists room
(
    id              bigint auto_increment primary key,
    media_source_id bigint not null,
    track_id        bigint null,
    constraint FKh4o8jqamvtubinly1vjviijnd foreign key (media_source_id) references media_source (id),
    constraint FKt3qfwfkqav3hfmomfccjs4b6a foreign key (track_id) references track (id)
);

create table if not exists member_dislike_rooms
(
    dislike_rooms_id bigint not null,
    member_id        bigint not null,
    constraint FKkllu9bnmlcie1wcjetm2gl99c foreign key (dislike_rooms_id) references room (id),
    constraint FKqvegid4dqmmdvjijwa9k2jvl1 foreign key (member_id) references member (id)
);

create table if not exists member_scrap_rooms
(
    member_id      bigint not null,
    scrap_rooms_id bigint not null,
    constraint FKhilpxusps0p5ltd3d1rl77mi3 foreign key (member_id) references member (id),
    constraint FKnbpmx67gju2c0jxut1o1txiuu foreign key (scrap_rooms_id) references room (id)
);

