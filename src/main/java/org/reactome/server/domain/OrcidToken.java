package org.reactome.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Entity
@Table(name = "orcid_token")
public class OrcidToken {

    @Id
    @Column(name = "orcid", length = 20)
    private String orcid; //id

    @Column(name = "name")
    private String name;

    @Column(name = "access_token", nullable = false, length = 100)
    @JsonProperty("access_token")
    private String accessToken; //id

    @Column(name = "refresh_token", nullable = false, length = 100)
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Column(name = "token_type", length = 50)
    @JsonProperty("token_type")
    private String tokenType;

    @Column(name = "scope", length = 100)
    private String scope;

    @Column(name = "expires_in")
    @JsonProperty("expires_in")
    private Long expiresIn;

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
