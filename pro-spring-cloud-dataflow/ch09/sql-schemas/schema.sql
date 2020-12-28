create database reviews;
use reviews
create table movies(
 id varchar(10) primary key,
 title varchar(200),
 actor varchar(200),
 year int,
 genre varchar(25),
 stars int,
 rating decimal(2,1),
 ratingcount int);
