package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.DTO.UserDTO;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.repository.UserRepository;
import com.lab2.electronicQueue.service.serviceInter.UserInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserInter {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setUserVerification(boolean newVerificationStatus, String userName) {
        User user = findUserByUsername(userName);
        userRepository.setUserVerification(!user.isActive(),userName);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }
    public UserDTO UserToUserDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setUser_id(user.getId());
        dto.setUser_name(user.getUsername());
        dto.setUser_role(user.getUserRole());
        dto.setUser_email(user.getUser_email());
        dto.set_active(user.isActive());
        return dto;
}
}