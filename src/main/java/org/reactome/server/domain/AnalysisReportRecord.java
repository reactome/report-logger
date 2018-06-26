package org.reactome.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "analysis_report")
@AssociationOverride(name = "userAgentType",
        joinColumns = @JoinColumn(name="uatype_id"),
        foreignKey = @ForeignKey(name = "FK_AR_UAT"))
public class AnalysisReportRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ip_address", length = 16)
    private String ip;

    @Column(name = "waiting_time", nullable = false)
    private Long waitingTime;

    @Column(name = "report_time", nullable = false)
    private Long reportTime;

    @Column(name = "pages_number", nullable = false)
    private Integer pages;

    @Column(name = "user_agent", length = 512)
    private String userAgent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uatype_id")
    private UserAgentType userAgentType;

    AnalysisReportRecord() {}

    public AnalysisReportRecord(String ip, Long waitingTime, Long reportTime, Integer pages, String userAgent, UserAgentType userAgentType) {
        this.ip = ip;
        this.waitingTime = waitingTime;
        this.reportTime = reportTime;
        this.pages = pages;
        this.userAgent = userAgent;
        this.userAgentType = userAgentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
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
        AnalysisReportRecord that = (AnalysisReportRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
