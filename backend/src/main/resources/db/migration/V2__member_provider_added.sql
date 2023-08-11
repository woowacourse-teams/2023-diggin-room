alter table member add column provider enum ('GOOGLE', 'SELF') not null default 'SELF';
