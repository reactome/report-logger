
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
        created datetime,
        last_modified datetime,
        putcode bigint not null,
        primary key (orcid, st_id)
    ) engine=InnoDB;

    create table orcid_token (
       orcid varchar(20) not null,
        access_token varchar(100) not null,
        expires_in bigint,
        name varchar(255),
        refresh_token varchar(100) not null,
        scope varchar(100),
        token_type varchar(50),
        primary key (orcid)
    ) engine=InnoDB;

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

    alter table target_resource
       add constraint UK_TR_NAME unique (name);

    alter table user_agent_type
       add constraint UK_UAT_NAME unique (name);

    alter table analysis_report
       add constraint FK_AR_UAT
       foreign key (uatype_id)
       references user_agent_type (id);

    alter table event_pdf
       add constraint FK_EP_UAT
       foreign key (uatype_id)
       references user_agent_type (id);

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
