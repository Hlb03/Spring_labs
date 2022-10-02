package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.repository.QueueRepository;
import com.lab2.electronicQueue.service.serviceInter.QueueInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueService implements QueueInter {

    @Autowired
    private QueueRepository queueRepository;
    /*@Autowired
    private PlaceInQueueService placeInQueueService;*/
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void addQueue(Queue queue, String username) {
        queue.setUser(userService.findUserByUsername(username));
        queue.setNumberOfFreeSeats(queue.getNumberOfSeats());
        queue.setActive(true);
        queueRepository.save(queue);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') ||" +
            "#username == authentication.principal.username")
    public void update(Queue queue, String username) {
        if(!findByQueueName(queue.getQueueName() ).getUser().getUsername().equals(username)
                || !userService.findUserByUsername(username).getUserRole().name().equals("ADMIN")){
            throw new IllegalArgumentException("Bad user");
        }
        queueRepository.save(queue);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') ||" +
            "#username == authentication.principal.username")
    public void deleteQueueByID(Long id,String username) {
        if(!findById(id).getUser().getUsername().equals(username)
                || !userService.findUserByUsername(username).getUserRole().name().equals("ADMIN")){
            throw new IllegalArgumentException("Bad user");
        }
        queueRepository.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') ||" +
            "#username == authentication.principal.username")
    public void closeOrOpenQueue(String queueName,String username) {
        if(!findByQueueName(queueName).getUser().getUsername().equals(username)
                || !userService.findUserByUsername(username).getUserRole().name().equals("ADMIN")){
            throw new IllegalArgumentException("Bad user");
        }
        boolean isActive = findByQueueName(queueName).isActive();
        queueRepository.closeOrOpenQueue(!isActive, queueName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUser_UsernameAndQueueName(String username, String queueName) {
        return queueRepository.existsByUser_UsernameAndQueueName(username, queueName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QueueDTO> findAllQueueFromUser(String username, Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return queueRepository.findAllByUser_Username(username,changePageable)
                .map(this::queueToQueueDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QueueDTO> findAllQueue(Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return queueRepository.findAll(changePageable)
                .map(this::queueToQueueDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QueueDTO> findAllActiveQueue(boolean isActive, Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return queueRepository.findAllByActive(changePageable, isActive)
                .map(this::queueToQueueDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Queue findByQueueName(String queueName) {
        return queueRepository.findByQueueName(queueName);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') ||" +
            "#username == authentication.principal.username")
    public QueueDTO findToHost(String queueName, String username) {
        if(existsByUser_UsernameAndQueueName(username,queueName)
                || userService.findUserByUsername(username).getUserRole().name().equals("ADMIN")){
            return queueToQueueDTO(findByQueueName(queueName));
        }
        return new QueueDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public Queue findById(Long id) {
        return queueRepository.findQueueById(id);
    }

    @Override
    @Transactional
    public void addFreeSeat(Long id) {
        Queue selectedQueue = findById(id);
        int numberOfFreeSeats = selectedQueue.getNumberOfFreeSeats();
        int numberOfSeats = selectedQueue.getNumberOfSeats();
        if(numberOfSeats>=numberOfFreeSeats+1){
            queueRepository.addFreeSeats(id);
        }
    }

    @Override
    @Transactional
    public void subFreeSeat(Long id) {
        Queue selectedQueue = findById(id);
        int numberOfFreeSeats = selectedQueue.getNumberOfFreeSeats();
        if(0<=numberOfFreeSeats-1){
            queueRepository.subFreeSeats(id);
        }
    }

    @Transactional
    public QueueDTO queueToQueueDTO(Queue queue){
        QueueDTO dto = new QueueDTO();
        dto.setId(queue.getId());
        dto.setQueueName(queue.getQueueName());
        dto.setNumberOfSeats(queue.getNumberOfSeats());
        dto.setNumberOfFreeSeats(queue.getNumberOfFreeSeats());
        dto.set_active(queue.isActive());
        dto.setHostName(queue.getUser().getUsername());
        /*List<String> userNameList = placeInQueueService.findAllByQueueName(queue.getQueueName())
                .stream()
                .map(PlaceInQueueDTO::getUsername)
                .collect(Collectors.toList());
        dto.setUserNameList(userNameList);*/
        return dto;
    }


}
