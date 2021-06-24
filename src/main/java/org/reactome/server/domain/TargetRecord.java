package org.reactome.server.domain;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "target")
@AssociationOverride(name = "userAgentType",
        joinColumns = @JoinColumn(name = "uatype_id"),
        foreignKey = @ForeignKey(name = "FK_TR_UAT"))
public class TargetRecord extends AbstractRecord {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "res_id", foreignKey = @ForeignKey(name = "FK_REP_TR"))
    private TargetResource targetResource;

    public TargetRecord() {
    }

    public TargetRecord(String term) {
        this.term = term;
    }

    public TargetRecord(String term, String ip, String userAgent, Integer releaseNumber, UserAgentType userAgentType, TargetResource targetResource) {
        super(term, ip, userAgent, releaseNumber, userAgentType);
        setTargetResource(targetResource);
    }

    public TargetResource getTargetResource() {
        return targetResource;
    }

    public void setTargetResource(TargetResource targetResource) {
        this.targetResource = targetResource;
    }

}
