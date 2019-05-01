create table orcid_claim (
     orcid varchar(20) not null,
     st_id varchar(20) not null,
     putcode bigint not null,
     created datetime,
     last_modified datetime,
     primary key (orcid, st_id)
) engine=InnoDB;


create table orcid_token (
     orcid varchar(20) not null,
     access_token varchar(100) not null,
     expires_in bigint,
     name varchar(255) not null,
     refresh_token varchar(100) not null,
     scope varchar(100),
     token_type varchar(50),
     primary key (orcid)
) engine=InnoDB;