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
                      foreign key(user_admin_id) references user(id) on delete cascade
);

create table place_in_queue(
                               id int auto_increment primary key,
                               user_id int not null,
                               queue_id int not null,
                               order_in_queue int not null ,
                               foreign key(user_id) references user(id) on delete cascade,
                               foreign key(queue_id) references queue(id) on delete cascade
);

insert into user_role(user_role) value
    ('USER'),('ADMIN');

insert into user(user_name,user_password,user_role,is_active,user_email) value
/*pass: testuser*/
    ('TestUser','$2a$12$mjxwynrtRBYRJ1ietc.31OQUzA54Lw9G9Rqp3REO26iI5.fjO5Q3G','USER',true,'testUser@gmail.com'),

    /*pass: userPass1*/
    ('User1','$2a$12$QNoQCZL/Sn3kHGKN6Mh8NeJZgo2uWAyRIq6rfqtldlCDZxMYbtDk2','USER',true,'userOne@gmail.com'),

    /*pass: userPass2*/
    ('User2','$2a$12$EhCq4nu5WzdWTdXUI3VJ8eUg/ZiYd/rHHbk4ybKYJJeyM/loQ3k82','USER',true,'userTwo@gmail.com'),

    /*pass: userPass3*/
    ('User3','$2a$12$oPVmjsbCFlWJWnWCWPtXQuVj8Y67pgrdJLDSddYA5VNilRTei3Vsq','USER',true,'userThree@gmail.com'),

    /*pass: userPass4*/
    ('User4','$2a$12$qlLR42BA0sdTMF2uleB2YeIA/mK2X5VVquRjJW2/bkNI4QuTPFa8.','USER',true,'userFour@gmail.com'),

    /*pass: userPass5*/
    ('User5','$2a$12$px/.zWGdQOEGTl7.QIbVJusjguhlbrB.z99PntMnQYCTj5rt1XtL2','USER',true,'userFive@gmail.com'),

    /*pass: userPass6*/
    ('User6','$2a$12$TUHu9e6EUbBSlXYfraB/9OMxVTFz8FReMXLpHXBQLZzV2Ha.y0lNa','USER',true,'userSix@gmail.com'),

    /*pass: userPass7*/
    ('User7','$2a$12$OVOPVUouPYjhEZ3jXC3vAeahS1AE0Lke/zyljB8enS5bTH6yr86ma','USER',true,'userSeven@gmail.com'),

    /*pass: userPass8*/
    ('User8','$2a$12$/oKG4j9XlHUH8BDHkbeUjuTOZSa.9Kzqdc9jJkRlws7LcrBD7wQFm','USER',true,'userEight@gmail.com'),

    /*pass: userPass9*/
    ('User9','$2a$12$R6c5cPmqPKh2MguLb0uR4ONanS5wxHPA26uzDUHcw/pGs5YgHUNwa','USER',true,'userNine@gmail.com'),

    /*pass: userPass10*/
    ('User10','$2a$12$vF85Ncu88Qqw23nFgZCpTe37HH17bCmVvv4xAHxl45ojFRa6ccN66','USER',true,'userTen@gmail.com'),

    /*pass: userPass11*/
    ('User11','$2a$12$wcWwFIiksg4s/71bvtEYkO.KFa507gCoJpr3QW61wHDiLP/DgykYe','USER',true,'userEleven@gmail.com'),

    /*pass: userPass12*/
    ('User12','$2a$12$71BTKnnSBhHK0rUMasJXbeWLLo.sh.KCzoV7YUMDLx3e59/tkDR/e','USER',true,'userTwelv@gmail.com'),

    /*pass: testadmin*/
    ('TestAdmin','$2a$12$9tcavON6VVv6pkyKAQ2DFeVPBk8oqegu0RaEyTBUKy4FgPxu/0qsC','ADMIN',true,'testAdmin@gmail.com');

insert into queue(queue_name,number_of_seats,number_of_free_seats,is_active,user_admin_id) value
    ('Queue1',20, 17,true,1),
    ('Queue2', 12, 9, true, 2),
    ('ThirdQueue', 15, 12, true, 3),
    ('BestQueue', 10, 10, true, 5);

insert into place_in_queue(user_id, queue_id, order_in_queue) values
                                                                  (1, 1, 1), (5, 1, 2), (2, 1, 3),
                                                                  (2, 2, 1), (1, 2, 2), (6, 2, 3),
                                                                  (3, 3, 1), (4, 3, 2), (7, 3, 3);