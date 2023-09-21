update member set nickname = concat('user-', left(uuid(), 8)) where nickname is null;
