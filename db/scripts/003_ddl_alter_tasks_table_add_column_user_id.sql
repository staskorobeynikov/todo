ALTER TABLE tasks
    ADD user_id int references users(id);

UPDATE tasks SET user_id = (SELECT id FROM users LIMIT 1);