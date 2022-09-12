package com.lab2.electronicQueue.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "queue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Queue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "queue_name")
    private String queueName;

    @Column(name = "is_active")
    boolean isActive;

    @JoinColumn(name = "user_admin_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "queue")
    private Set<PlaceInQueue> placeInQueues;

    /*@ManyToMany
    @JoinTable(name = "place_in_queue",
    joinColumns = {@JoinColumn(name = "queue_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;*/
}
