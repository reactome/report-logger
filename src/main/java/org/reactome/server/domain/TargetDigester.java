package org.reactome.server.domain;

import java.io.Serializable;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
public class TargetDigester implements Serializable {

    private final String term;
    private final Long count;
    private final Long uniqueIPs;

    public TargetDigester(String term, Long count, Long uniqueIPs) {
        this.term = term;
        this.count = count;
        this.uniqueIPs = uniqueIPs;
    }

    public String getTerm() {
        return term;
    }

    public Long getCount() {
        return count;
    }

    public Long getUniqueIPs() {
        return uniqueIPs;
    }
}
