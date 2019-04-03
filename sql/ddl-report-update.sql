create table orcid_claim (
     orcid varchar(20) not null,
     st_id varchar(20) not null,
     putcode bigint not null,
     created datetime,
     last_modified datetime,
     primary key (orcid, st_id)
) engine=InnoDB;