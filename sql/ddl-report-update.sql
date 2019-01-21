
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

    alter table event_pdf 
       add constraint FK_EP_UAT 
       foreign key (uatype_id) 
       references user_agent_type (id);
