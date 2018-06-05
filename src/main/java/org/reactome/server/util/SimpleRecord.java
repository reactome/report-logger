package org.reactome.server.util;

import java.io.Serializable;

/**
 * Simple object to marshall the report input.
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

public class SimpleRecord implements Serializable {
    private String term;
    private String resource;

    public SimpleRecord() {
    }

    public SimpleRecord(String term, String resource) {
        this.term = term;
        this.resource = resource;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
