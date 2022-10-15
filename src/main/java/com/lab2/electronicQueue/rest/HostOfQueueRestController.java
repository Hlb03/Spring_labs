package com.lab2.electronicQueue.rest;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.repository.QueueRepository;
import com.lab2.electronicQueue.service.serviceImpl.PlaceInQueueService;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/host",produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class HostOfQueueRestController {
    private final QueueRepository queueRepository;
    private final PlaceInQueueService placeInQueueService;
    private final QueueService queueService;

    @Autowired
    public HostOfQueueRestController(QueueRepository queueRepository, PlaceInQueueService placeInQueueService, QueueService queueService) {
        this.queueRepository = queueRepository;
        this.placeInQueueService = placeInQueueService;
        this.queueService = queueService;
    }

    @GetMapping("/all")
    public Iterable<QueueDTO> getAllHostQueue(Principal principal, @RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("direction") String direction, @RequestParam("sort") String sort){
        return queueService.findAllHostQueue(principal.getName(),page,size,direction,sort);
    }

    @GetMapping("/{queueName}")
    public ResponseEntity<QueueDTO> getHostQueue(Principal principal, @PathVariable("queueName") String queueName){
        Optional<Queue> queue = queueRepository.findByQueueNameAndUser_Username(queueName,principal.getName());
        return queue
                .map(value -> new ResponseEntity<>(queueService.queueToQueueDTO(value),HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(null,HttpStatus.NOT_FOUND));

    }

    @DeleteMapping(path = "/delete/user/{username}/queue/{queueName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserFromQueue(@PathVariable("username") String username
            , @PathVariable("queueName") String queueName, Principal principal){
        placeInQueueService.deletePlace(username,queueName,principal.getName());
    }

    @PatchMapping(path = "/block/queue/{queueName}")
    public void blockQueue(@PathVariable("queueName") String queueName, Principal principal){
        queueService.closeOrOpenQueue(queueName, principal.getName());
    }

    @DeleteMapping(path = "/delete/queue/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQueue(@PathVariable("id") Long id, Principal principal){
        queueService.deleteQueueByID(id,principal.getName());
    }

    @DeleteMapping("/next/queue/{queueName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void nextUser(@PathVariable("queueName") String queueName, Principal principal){
        placeInQueueService.nextUser(queueName, principal.getName());
    }

    @PutMapping(path = "/update/{id}", consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    private QueueDTO updateData(@RequestBody QueueDTO queueDTO, @PathVariable("id") Long id) {
        return queueService.updateQueueData(queueDTO, id);
    }
}
