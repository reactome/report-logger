package org.reactome.server.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "term", nullable = false, length = 2048)
    protected String term;

    @Column(name = "ip_address", length = 16)
    protected String ip;

    @Column(name = "release_number") // release is keyword in MySQL
    protected Integer releaseNumber;

    @Column(name = "user_agent", length = 512)
    protected String userAgent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @CreationTimestamp
    protected Date created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uatype_id")
    protected UserAgentType userAgentType;

    AbstractRecord() {
    }

    public AbstractRecord(String term, String ip, String userAgent, Integer releaseNumber, UserAgentType userAgentType) {
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
        AbstractRecord target = (AbstractRecord) o;
        return Objects.equals(term, target.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term);
    }
}
