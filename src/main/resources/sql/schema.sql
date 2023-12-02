drop database if exists kade3;
create database if not exists kade3;

use kade3;

create table user(
                     username varchar(35)primary key ,
                     email varchar(35)not null,
                     password varchar(35)not null
);



create table login_page(
                           l_id varchar(35)primary key ,
                           date date not null ,
                           login_date date not null ,
                           exite_date date not null,
                           username varchar(35)not null ,
                           constraint foreign key (username) references user (username)
                               on UPDATE cascade on DELETE cascade
);

create table employee (
                          emp_id varchar(35)primary key ,
                          first_name varchar(35)not null ,
                          last_name varchar(35)not null ,
                          nic varchar(35)not null ,
                          city varchar(35)not null ,
                          Contact_no varchar(35)not null
);

create table customer(
                         id varchar(35) primary key ,
                         first_name varchar(35)not null ,
                         last_name varchar(35)not null ,
                         telephone_number varchar(35)not null,
                         address varchar(35)not null ,
                         city varchar(35)not null

);

create table Orders(
                       O_id varchar(35) primary key ,
                       c_id varchar(35) not null,
                       date date not null ,
                       constraint foreign key (c_id) references customer (id)
                           on delete cascade on update cascade
);

create table  prepared_stock(
                                p_id varchar(35)primary key ,
                                description varchar(35)not null ,
                                unit_price int not null ,
                                weight int not null ,
                                qty int not null
);


create table order_details(
                              o_id varchar(35)not null ,
                              p_id varchar(35)not null ,
                              unit_price int not null ,
                              weight int not null ,
                              qty int not null,
                              constraint foreign key (o_id) references orders (o_id)
                                  on UPDATE cascade on DELETE cascade,
                              constraint foreign key (p_id) references prepared_stock (p_id)
                                  on UPDATE cascade on DELETE cascade
);

create table raw_stock(
                          rs_id varchar(35)primary key ,
                          description varchar(35)not null ,
                          unit_price int not null ,
                          weight int not null ,
                          qty int not null
);

create table stock_details(
                              p_id varchar(35)not null ,
                              rs_id varchar(35)not null ,
                              description varchar(35)not null ,
                              unit_price int not null ,
                              weight int not null ,
                              qty int not null ,
                              constraint foreign key (p_id) references order_details (p_id)
                                  on UPDATE cascade on DELETE cascade,
                              constraint foreign key (rs_id) references raw_stock (rs_id)
                                  on UPDATE cascade on DELETE cascade
);



create table salary (
                        s_id varchar(35)primary key ,
                        emp_id varchar(35)not null ,
                        date date not null ,
                        salary int not null ,
                        constraint foreign key (emp_id) references employee (emp_id)
                            on UPDATE cascade on DELETE cascade

);
drop table salary;


create table supplier(
                         su_id varchar(35)primary key ,
                         name varchar(35)not null ,
                         loction varchar(35)not null ,
                         email varchar(35)not null ,
                         telephone_number varchar(35)not null
);
create table supplier_order(
                               s_id varchar(35)primary key ,
                               su_id varchar(35)not null ,
                               description varchar(35)not null ,
                               unit_price int not null ,
                               weight int not null ,
                               qty int not null ,
                               constraint foreign key (su_id) references supplier (su_id)
                                   on UPDATE cascade on DELETE cascade

);

create table supplier_order_details(
                                       rs_id varchar(35)not null ,
                                       s_id varchar(35)not null ,
                                       unit_price int not null ,
                                       weight int not null ,
                                       qty int not null ,
                                       constraint foreign key (rs_id) references raw_stock (rs_id)
                                           on UPDATE cascade on DELETE cascade,
                                       constraint foreign key (s_id) references supplier_order (s_id)
                                           on UPDATE cascade on DELETE cascade

);








