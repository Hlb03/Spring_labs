package com.lab2.electronicQueue.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = [[#{error.message.blank}]])
    @Size(min = 4, max = 32, message = [[#{error.message.4.32}]])
    @Column(name = "user_name")
    String username;

    @NotBlank(message = [[#{error.message.blank}]])
    @Size(min = 8, max = 64, message = [[#{error.message.8.64}]])
    @Column(name = "user_password")
    String userPassword;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Column(name = "is_active")
    boolean isActive;

    @NotBlank(message = [[#{error.message.blank}]])
    @Email(message = [[#{error.message.email}]])
    @Column(name = "user_email")
    String userEmail;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Queue> queueSet;

    @OneToMany(mappedBy = "user")
    private Set<PlaceInQueue> placeInQueues;




}
