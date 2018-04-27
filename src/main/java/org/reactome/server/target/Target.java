package org.reactome.server.target;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "report")
public class Target implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String term;

    private String ip;

    @Column(name = "releaseNumber") // release is keyword in MySQL
    private Integer releaseNumber;

    private String userAgent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private UserAgentType userAgentType;

    public Target(String term) {
        this.term = term;
    }

    public Target(String term, String ip, String userAgent, Integer releaseNumber, UserAgentType userAgentType) {
        this.term = term;
        this.ip = ip;
        this.userAgent = userAgent;
        this.releaseNumber = releaseNumber;
        this.userAgentType = userAgentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getReleaseNumber() {
        return releaseNumber;
    }

    public void setReleaseNumber(Integer releaseNumber) {
        this.releaseNumber = releaseNumber;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserAgentType getUserAgentType() {
        return userAgentType;
    }

    public void setUserAgentType(UserAgentType userAgentType) {
        this.userAgentType = userAgentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(term, target.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term);
    }
}
