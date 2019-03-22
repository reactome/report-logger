    create table orcid_claim (
       orcid varchar(20) not null,
       st_id varchar(20) not null,
       putcode bigint not null,
       primary key (orcid, st_id)
    ) engine=InnoDB;