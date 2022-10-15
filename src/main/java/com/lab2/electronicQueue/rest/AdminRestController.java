package com.lab2.electronicQueue.rest;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.DTO.UserDTO;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.repository.UserRepository;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import com.lab2.electronicQueue.service.serviceImpl.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/admin",produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class AdminRestController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final QueueService queueService;

    public AdminRestController(UserRepository userRepository, UserService userService, QueueService queueService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.queueService = queueService;
    }

    @GetMapping(path = "/all/users")
    public Iterable<UserDTO> getAllUser(){
        PageRequest pageRanges = PageRequest.of(0,10, Sort.by("id").descending());
        List<User> userList = userRepository.findAll(pageRanges).getContent();
        return userList.stream().map(userService::userToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable("username") String username){
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional
                .map(value -> new ResponseEntity<>(userService.userToUserDTO(value), HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/set/user/status/{username}")
    public void changeUserAccountStatus(@PathVariable("username") String username){
        userService.setUserVerification(username);
    }

    @DeleteMapping("/delete/user/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByUsername(@PathVariable("username") String username){
        userService.deleteUserByUsername(username);
    }

    @PatchMapping(path = "/set/queue/status/{queueName}", consumes="application/json")
    public QueueDTO changeQueueStatus(@PathVariable("queueName") String queueName){
        return queueService.closeOrOpenQueue(queueName);
    }

    @DeleteMapping("/delete/queue/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQueueByID(@PathVariable("id") Long id){
        queueService.deleteQueue(id);
    }

}
