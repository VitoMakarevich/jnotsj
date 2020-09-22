insert into users (username, email, password) values ('testuser', 'testuser@jnotsj.com', 'simplepassword');
insert into user_roles(user_id, role_id) VALUES
(
    (select id from users where username = 'testuser'),
    (select id from role where role_name = 'USER')
)