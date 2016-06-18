DROP TABLE IF EXISTS refuel;
CREATE TABLE refuel (
    id uuid,
    user_name varchar(100),
    vehicle_number varchar(100),
    fuel_type varchar(100),
    price double precision,
    amount double precision,
    amount_type int,
    station_id varchar(100),
    station_name varchar(100),
    state varchar(100),
    appoint_time timestamp,
    create_time timestamp,
    modify_time timestamp,
    CONSTRAINT refuel_pkey PRIMARY KEY (id),
    CONSTRAINT refuel_user_name_fkey FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);
