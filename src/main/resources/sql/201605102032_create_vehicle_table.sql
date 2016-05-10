CREATE TABLE vehicle (
    id uuid,
    user_name varchar(100),
    brand varchar(100),
    mark varchar(100),
    model varchar(100),
    number varchar(100),
    engine varchar(100),
    door_num int,
    seat_num int,
    create_time timestamp,
    modify_time timestamp,
    CONSTRAINT vehicle_pkey PRIMARY KEY (id),
    CONSTRAINT vehicle_user_name_fkey FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);

CREATE TABLE vehicle_status (
    id uuid,
    user_name varchar(100),
    mileage varchar(100),
    gasoline varchar(100),
    engine_state varchar(100),
    transmission_state varchar(100),
    headlight_state varchar(100),
    create_time timestamp,
    modify_time timestamp,
    CONSTRAINT vehicle_status_pkey PRIMARY KEY (id),
    CONSTRAINT vehicle_status_id_fkey FOREIGN KEY (id) REFERENCES vehicle(id),
    CONSTRAINT vehicle_status_user_name_fkey FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);
