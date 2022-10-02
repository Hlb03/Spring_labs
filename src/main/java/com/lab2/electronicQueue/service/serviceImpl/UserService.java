package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.DTO.UserDTO;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.repository.UserRepository;
import com.lab2.electronicQueue.service.serviceInter.UserInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserByID(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void setUserVerification(String userName) {
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

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDTO> findAllUsers(Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return userRepository.findAll(changePageable)
                .map(this::userToUserDTO);
    }

    public UserDTO userToUserDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getId());
        dto.setUserName(user.getUsername());
        dto.setUserRole(user.getUserRole().name());
        dto.setUserEmail(user.getUserEmail());
        dto.setActive(user.isActive());
        return dto;
    }
}