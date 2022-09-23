package com.lab2.electronicQueue.service.serviceInter;

import com.lab2.electronicQueue.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

public interface UserInter {
    void addUser(User user);
    void updateUser(User user);
    void deleteUserByID(Long id);
    void deleteUserByUsername(String username);
    void setUserVerification(boolean newVerificationStatus, String userName);
    User findUserByUsername(String username);
    boolean existsUserByUsername(String username);
}
