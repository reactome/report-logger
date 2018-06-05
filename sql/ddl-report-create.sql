
    create table REPORT (
       ID bigint not null auto_increment,
        CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        IP_ADDRESS varchar(16),
        RELEASE_NUMBER integer,
        NAME varchar(2048) not null,
        USER_AGENT varchar(512),
        UATYPE_ID bigint,
        RES_ID bigint,
        primary key (ID)
    ) engine=InnoDB;

    create table SEARCH (
       ID bigint not null auto_increment,
        CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        IP_ADDRESS varchar(16),
        RELEASE_NUMBER integer,
        NAME varchar(2048) not null,
        USER_AGENT varchar(512),
        UATYPE_ID bigint,
        primary key (ID)
    ) engine=InnoDB;

    create table TARGET_RESOURCE (
       ID bigint not null auto_increment,
        ALTERNATIVE_NAMES varchar(255),
        NAME varchar(255) not null,
        primary key (ID)
    ) engine=InnoDB;

    create table USER_AGENT_TYPE (
       ID bigint not null auto_increment,
        NAME varchar(255) not null,
        primary key (ID)
    ) engine=InnoDB;

    alter table TARGET_RESOURCE 
       add constraint UK_TR_NAME unique (NAME);

    alter table USER_AGENT_TYPE 
       add constraint UK_UAT_NAME unique (NAME);

    alter table REPORT 
       add constraint FK_TR_UAT 
       foreign key (UATYPE_ID) 
       references USER_AGENT_TYPE (ID);

    alter table REPORT 
       add constraint FK_REP_TR 
       foreign key (RES_ID) 
       references TARGET_RESOURCE (ID);

    alter table SEARCH 
       add constraint FK_SR_UAT 
       foreign key (UATYPE_ID) 
       references USER_AGENT_TYPE (ID);
