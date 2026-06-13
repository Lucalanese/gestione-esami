package it.unimol.gestione_esami.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_cache")
public class UserCache {
    @Id
    private Long userId;

    private String fullName;
    private String roleId;

    public UserCache() {
    }

    public UserCache(Long userId, String fullName, String roleId) {
        this.userId = userId;
        this.fullName = fullName;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
