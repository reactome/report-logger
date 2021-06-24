package org.reactome.server.util;

import java.util.Map;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
public class Mail {
    private final String from;
    private final String to;
    private final String subject;
    private final String template;
    private String content;

    private Map<String, Object> model;

    public Mail(String from, String to, String subject, String template) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.template = template;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return template;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
