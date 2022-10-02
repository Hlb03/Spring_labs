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

    @NotBlank(message = "This field can't be blank")
    @Size(min = 4, max = 32, message = "length must be from 4 to 32 characters")
    @Column(name = "user_name")
    String username;

    @NotBlank(message = "This field can't be blank")
    @Size(min = 8, max = 64, message = "length must be from 8 to 64 characters")
    @Column(name = "user_password")
    String userPassword;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Column(name = "is_active")
    boolean isActive;

    @NotBlank(message = "This field can't be blank")
    @Email(message = "Email address is invalid")
    @Column(name = "user_email")
    String userEmail;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Queue> queueSet;

    @OneToMany(mappedBy = "user")
    private Set<PlaceInQueue> placeInQueues;




}
