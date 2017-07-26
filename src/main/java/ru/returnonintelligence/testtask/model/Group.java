package ru.returnonintelligence.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * @author NIKIT on 25.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="groups")
public class Group {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @ManyToMany//
    private Set<User> users;
}
