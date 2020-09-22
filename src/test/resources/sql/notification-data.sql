insert into notification_data (id, text, start_date, end_date, author_id) values
(
1,
'test notification',
current_timestamp,
current_timestamp,
(select id from users where username = 'testuser')
);