package com.lab2.electronicQueue.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name")
    String username;

    @Column(name = "user_password")
    String user_password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "user_email")
    String user_email;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Queue> queueSet;

    @OneToMany(mappedBy = "user")
    private Set<PlaceInQueue> placeInQueues;

    /*@ManyToMany(mappedBy = "users")
    private Set<Queue> queues;*/



}
