package com.lab2.electronicQueue.rest;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.repository.QueueRepository;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/queue",produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class QueueRestController {
    private final QueueRepository queueRepository;
    private final QueueService queueService;

    @Autowired
    public QueueRestController(QueueRepository queueRepository, QueueService queueService) {
        this.queueRepository = queueRepository;
        this.queueService = queueService;
    }

    @GetMapping("/all")
    public Iterable<QueueDTO> getAllQueue(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("direction") String direction, @RequestParam("sort") String sort){
        PageRequest pageRanges = PageRequest.of(page,size
                , direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        List<Queue> queueList = queueRepository.findAll(pageRanges).getContent();
        return queueList.stream().map(queueService::queueToQueueDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueueDTO> getQueueById(@PathVariable("id") Long id){
        Optional<Queue> queue = queueRepository.findById(id);

        return queue
                .map(value -> new ResponseEntity<>(queueService.queueToQueueDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
