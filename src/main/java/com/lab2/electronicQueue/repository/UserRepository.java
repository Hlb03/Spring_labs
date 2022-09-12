package com.lab2.electronicQueue.repository;

import com.lab2.electronicQueue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
