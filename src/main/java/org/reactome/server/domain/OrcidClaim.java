package org.reactome.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Entity
@Table(name = "orcid_claim")
@IdClass(OrcidClaimPk.class)
public class OrcidClaim {

    @Id
    @Column(name = "st_id", length = 20)
    private String stId;

    @Id
    @Column(name = "orcid", length = 20)
    private String orcid;

    @Column(name = "putcode", nullable = false)
    private Long putCode;

    public OrcidClaim() {
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public Long getPutCode() {
        return putCode;
    }

    public void setPutCode(Long putCode) {
        this.putCode = putCode;
    }
}

class OrcidClaimPk implements Serializable {

    private String stId;
    private String orcid;

    public OrcidClaimPk() {
    }

    public OrcidClaimPk(String stId, String orcid) {
        this.stId = stId;
        this.orcid = orcid;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrcidClaimPk that = (OrcidClaimPk) o;
        return Objects.equals(stId, that.stId) &&
                Objects.equals(orcid, that.orcid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stId, orcid);
    }
}

