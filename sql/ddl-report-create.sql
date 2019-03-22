    create table search (
      id bigint not null auto_increment,
      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      ip_address varchar(16),
      release_number integer,
      term varchar(2048) not null,
      user_agent varchar(512),
      uatype_id bigint,
      primary key (id)
    ) engine=InnoDB;

    create table target (
      id bigint not null auto_increment,
      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      ip_address varchar(16),
      release_number integer,
      term varchar(2048) not null,
      user_agent varchar(512),
      uatype_id bigint,
      res_id bigint,
      primary key (id)
    ) engine=InnoDB;

    create table target_resource (
      id bigint not null auto_increment,
      alternative_names varchar(255),
      name varchar(255) not null,
      primary key (id)
    ) engine=InnoDB;

    create table user_agent_type (
      id bigint not null auto_increment,
      name varchar(255) not null,
      primary key (id)
    ) engine=InnoDB;

    create table analysis_report (
      id bigint not null auto_increment,
      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      ip_address varchar(16),
      pages_number integer not null,
      report_time bigint not null,
      user_agent varchar(512),
      waiting_time bigint not null,
      uatype_id bigint,
      primary key (id)
    ) engine=InnoDB;

    create table event_pdf (
      id bigint not null auto_increment,
      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      ip_address varchar(16),
      pages_number integer not null,
      report_time bigint not null,
      user_agent varchar(512),
      waiting_time bigint not null,
      uatype_id bigint,
      primary key (id)
    ) engine=InnoDB;

    create table orcid_claim (
      orcid varchar(20) not null,
      st_id varchar(20) not null,
      putcode bigint not null,
      primary key (orcid, st_id)
    ) engine=InnoDB;

    alter table target_resource
    add constraint UK_TR_NAME
    unique (name);

    alter table user_agent_type
    add constraint UK_UAT_NAME
    unique (name);

    alter table search
    add constraint FK_SR_UAT
    foreign key (uatype_id)
    references user_agent_type (id);

    alter table target
    add constraint FK_TR_UAT
    foreign key (uatype_id)
    references user_agent_type (id);

    alter table target
    add constraint FK_REP_TR
    foreign key (res_id)
    references target_resource (id);

    alter table analysis_report
    add constraint FK_AR_UAT
    foreign key (uatype_id)
    references user_agent_type (id)