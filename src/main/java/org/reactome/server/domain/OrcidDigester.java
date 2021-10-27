package org.reactome.server.domain;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
public class OrcidDigester {
    private String name;
    private String orcid;
    private long total;

    public OrcidDigester() {
    }

    public OrcidDigester(String name, String orcid, long total) {
        this.name = name;
        this.orcid = orcid;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
