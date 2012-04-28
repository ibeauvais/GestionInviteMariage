
# --- !Ups

create table adulte (
  invite_id                 bigint not null,
  nom                       varchar(255),
  prenom                    varchar(255))
;

create table enfant (
  id                        bigint not null,
  invite_id                 bigint not null,
  nom                       varchar(255),
  age                       varchar(255),
  constraint pk_enfant primary key (id))
;

create table invite (
  id                        bigint not null,
  nom                       varchar(255),
  presence                  integer,
  type                      integer,
  nb_adulte                 integer,
  nb_enfant                 integer,
  present_dimanche          integer,
  valide                    boolean,
  constraint ck_invite_presence check (presence in (0,1,2)),
  constraint ck_invite_type check (type in (0,1,2)),
  constraint ck_invite_present_dimanche check (present_dimanche in (0,1,2)),
  constraint pk_invite primary key (id))
;

create table tarif (
  id                        bigint not null,
  tarif_repas               decimal(38),
  tarif_repas_enfant        decimal(38),
  tarif_vin_honneur         decimal(38),
  tarif_vin_honneur_enfant  decimal(38),
  tarif_dimanche            decimal(38),
  tarif_dimanche_enfant     decimal(38),
  animations                decimal(38),
  vaisselles                decimal(38),
  serveurs                  decimal(38),
  constraint pk_tarif primary key (id))
;

create table to_do (
  id                        bigint not null,
  invite_id                 bigint,
  description               varchar(255),
  constraint pk_to_do primary key (id))
;

create sequence enfant_seq;

create sequence invite_seq;

create sequence tarif_seq;

create sequence to_do_seq;

alter table adulte add constraint fk_adulte_invite_1 foreign key (invite_id) references invite (id) on delete restrict on update restrict;
create index ix_adulte_invite_1 on adulte (invite_id);
alter table enfant add constraint fk_enfant_invite_2 foreign key (invite_id) references invite (id) on delete restrict on update restrict;
create index ix_enfant_invite_2 on enfant (invite_id);
alter table to_do add constraint fk_to_do_invite_3 foreign key (invite_id) references invite (id) on delete restrict on update restrict;
create index ix_to_do_invite_3 on to_do (invite_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists adulte;

drop table if exists enfant;

drop table if exists invite;

drop table if exists tarif;

drop table if exists to_do;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists enfant_seq;

drop sequence if exists invite_seq;

drop sequence if exists tarif_seq;

drop sequence if exists to_do_seq;

