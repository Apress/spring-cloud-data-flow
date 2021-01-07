create table IF NOT EXISTS movies(
    id int not null auto_increment,
    title varchar(250),
    actor varchar(100),
    year smallint,
primary key(id));
