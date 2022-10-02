package com.lab2.electronicQueue.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "This field can't be blank")
    @Size(min = 2, max = 32, message = "length must be from 2 to 32 characters")
    @Column(name = "queue_name")
    private String queueName;

    @NotNull(message = "This field can't be blank")
    @Min(value = 0, message = "Count of compartment seats can't negative")
    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "number_of_free_seats")
    private int numberOfFreeSeats;

    @Column(name = "is_active")
    private boolean active;

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
