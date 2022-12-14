package com.lab2.electronicQueue.repository;


import com.lab2.electronicQueue.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);
    boolean existsUserByUsername(String username);
    void deleteByUsername(String username);
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "UPDATE user SET is_active = :newVerificationStatus WHERE user_name = :userName", nativeQuery = true)
    void setUserVerification(@Param("newVerificationStatus") boolean newVerificationStatus, @Param("userName") String userName);

    Page<User> findAll(Pageable pageable);

}
