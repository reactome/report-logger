package org.reactome.server.domain;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "search")
@AssociationOverride(name = "userAgentType",
        joinColumns = @JoinColumn(name = "uatype_id"),
        foreignKey = @ForeignKey(name = "FK_SR_UAT"))
public class SearchRecord extends AbstractRecord {
    public SearchRecord(String term, String ip, String userAgent, Integer releaseNumber, UserAgentType userAgentType) {
        super(term, ip, userAgent, releaseNumber, userAgentType);
    }

    public SearchRecord() {

    }
}
