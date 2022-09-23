drop database if exists Electronic_Queue_DB;

create database if not exists Electronic_Queue_DB;

USE Electronic_Queue_DB;

create table user_role(
    user_role varchar(64) primary key not null unique
);

create table user(
                     id int auto_increment primary key,
                     user_name varchar(256) not null unique,
                     user_password varchar(1024) not null,
                     user_role varchar(64) not null,
                     is_active boolean not null,
                     user_email varchar(128) not null unique,
                     foreign key(user_role) references user_role(user_role)
);

create table queue(
                      id int auto_increment primary key,
                      queue_name varchar(256) not null,
                      number_of_seats int not null,
                      number_of_free_seats int not null,
                      is_active boolean not null,
                      user_admin_id int not null,
                      foreign key(user_admin_id) references user(id)
);

create table place_in_queue(
                               id int auto_increment primary key,
                               user_id int not null,
                               queue_id int not null,
                               foreign key(user_id) references user(id),
                               foreign key(queue_id) references queue(id)
);