package ru.returnonintelligence.testtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER")
public class User implements UserDetails {
//    @PrePersist
//    protected void onCreate() {
//        this.lastUpdatedTimestamp = this.createdTimestamp = new Timestamp(System.currentTimeMillis());
//    }
//    @PreUpdate
//    protected void onUpdate() {
//        this.lastUpdatedTimestamp = new Timestamp(System.currentTimeMillis());
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 6, nullable = false)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "is_active")
    private Boolean isActive;
    public void setPassword(String password) {
        System.out.println("password"+new BCryptPasswordEncoder().encode(password));
        this.password = new BCryptPasswordEncoder().encode(password);
    }
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_timestamp")
    private Date lastUpdatedTimestamp;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_timestamp")
    private Date createdTimestamp;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> groups;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> grantedAuths = new ArrayList<>();
//        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return this.authorities;
    }
    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @JsonIgnore
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;
    @JsonIgnore
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;
    @JsonIgnore
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;
    @JsonIgnore
    private boolean enabled;
}
