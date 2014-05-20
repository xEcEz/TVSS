# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table query (
  id                        bigint not null,
  label                     varchar(255),
  constraint pk_query primary key (id))
;

create table user_show (
  id                        bigint not null,
  label                     varchar(255),
  constraint pk_user_show primary key (id))
;

create sequence query_seq;

create sequence user_show_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists query;

drop table if exists user_show;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists query_seq;

drop sequence if exists user_show_seq;

