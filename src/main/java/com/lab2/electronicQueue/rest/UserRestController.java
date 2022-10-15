package com.lab2.electronicQueue.rest;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.repository.QueueRepository;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/user",produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class UserRestController {
    private final QueueRepository queueRepository;
    private final QueueService queueService;

    @Autowired
    public UserRestController(QueueRepository queueRepository, QueueService queueService) {
        this.queueRepository = queueRepository;
        this.queueService = queueService;
    }

    @PostMapping(path = "/add/queue", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public QueueDTO addQueue(@RequestBody Queue queue, Principal principal){
        return queueService.addQueue(queue,principal.getName());
    }

    @GetMapping(path = "/all")
    public Iterable<QueueDTO> getAllQueue(@AuthenticationPrincipal UserDetails user){
        PageRequest pageRanges = PageRequest.of(0,10, Sort.by("id").descending());
        List<Queue> queueList = queueRepository.findAllByUser_Username(user.getUsername(), pageRanges).getContent();
        return queueList.stream().map(queueService::queueToQueueDTO).collect(Collectors.toList());
    }
}
