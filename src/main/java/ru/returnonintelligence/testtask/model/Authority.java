package ru.returnonintelligence.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by fan.jin on 2016-11-03.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToMany()
//    private List<User> users;
    @Column(name="name")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
//public enum Authority implements GrantedAuthority {
//    ADMIN, USER;
//
//    @Override
//    public String getAuthority() {
//        return name();
//    }
//}