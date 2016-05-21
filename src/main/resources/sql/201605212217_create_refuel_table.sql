CREATE TABLE refuel (
    id uuid,
    user_name varchar(100),
    owner_name varchar(100),
    from_time timestamp,
    to_time timestamp,
    station_id varchar(100),
    station_name varchar(100),
    fuel_type varchar(100),
    litre double,
    cost double,
    state varchar(100),
    create_time timestamp,
    modify_time timestamp,
    CONSTRAINT refuel_pkey PRIMARY KEY (id),
    CONSTRAINT refuel_user_name_fkey FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);
