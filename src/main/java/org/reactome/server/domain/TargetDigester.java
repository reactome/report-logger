package org.reactome.server.domain;

import java.io.Serializable;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
public class TargetDigester implements Serializable {

    private String term;
    private String ip;
    private Long count;

    public TargetDigester() {
    }

    public TargetDigester(String term, Long count) {
        this.term = term;
        this.count = count;
    }

    public TargetDigester(String term, String ip, Long count) {
        this.term = term;
        this.ip = ip;
        this.count = count;
    }

    public String getTerm() {
        return term;
    }

    public String getIp() {
        return ip;
    }

    public Long getCount() {
        return count;
    }
}
