package com.hayder.socialprojekt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String email;
    private String password;
    private String userRole;
    private Date userTimestamp;
    private String userProfileImage;

    public ApplicationUser(String name, String email, String password) {
        this.userRole = UserRoles.USER.name();
        this.userTimestamp = new Date();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ApplicationUser() {
        this.userTimestamp = new Date();
        this.userRole = UserRoles.USER.name();
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getUserTimestamp() {
        return userTimestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationUser)) return false;
        ApplicationUser user = (ApplicationUser) o;
        return getUserId() == user.getUserId() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getUserTimestamp(), user.getUserTimestamp()) &&
                Objects.equals(userProfileImage, user.userProfileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getEmail(), getPassword(), getUserTimestamp(), userProfileImage);
    }
}
