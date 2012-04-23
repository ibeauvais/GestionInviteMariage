# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table adulte (
  nom                       varchar(255),
  prenom                    varchar(255))
;

create table enfant (
  id                        bigint not null,
  nom                       varchar(255),
  age                       integer,
  constraint pk_enfant primary key (id))
;

create table invite (
  id                        bigint not null,
  nom                       varchar(255),
  presence                  integer,
  constraint ck_invite_presence check (presence in (0,1,2)),
  constraint pk_invite primary key (id))
;

create sequence enfant_seq;

create sequence invite_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists adulte;

drop table if exists enfant;

drop table if exists invite;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists enfant_seq;

drop sequence if exists invite_seq;

