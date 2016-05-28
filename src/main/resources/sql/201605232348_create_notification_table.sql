CREATE TABLE notification (
    id uuid,
    vehicle_id uuid,
    user_name varchar(100),
    type varchar(100),
    description varchar(100),
    is_readed bool,
    read_time timestamp,
    create_time timestamp,
    CONSTRAINT notification_pkey PRIMARY KEY (id),
    CONSTRAINT notification_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT notification_user_name_fkey FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);
