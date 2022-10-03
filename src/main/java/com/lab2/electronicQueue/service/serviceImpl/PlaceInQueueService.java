package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.entity.PlaceInQueue;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.repository.PlaceInQueueRepository;
import com.lab2.electronicQueue.service.serviceInter.PlaceInQueueInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceInQueueService implements PlaceInQueueInter {
    private  PlaceInQueueRepository placeInQueueRepository;
    private  QueueService queueService;
    private UserService userService;

    @Autowired
    public void setQueueService(QueueService queueService) {
        this.queueService = queueService;
    }

    @Autowired
    public void setPlaceInQueueRepository(PlaceInQueueRepository placeInQueueRepository) {
        this.placeInQueueRepository = placeInQueueRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void addPlaceInQueue(String queueName, String username) {
        if(isExistUserInQueue(username,queueName)){
            throw new IllegalArgumentException("This user in queue");
        }
        Queue selectedQueue = queueService.findByQueueName(queueName);
        queueService.subFreeSeat(selectedQueue.getId());
        User selectedUser = userService.findUserByUsername(username);
        PlaceInQueue placeInQueue = new PlaceInQueue();
        placeInQueue.setUser(selectedUser);
        placeInQueue.setQueue(selectedQueue);
        placeInQueue.setOrderInQueue(findMaxOrderInQueue(queueName));

        placeInQueueRepository.save(placeInQueue);
    }

    @Override
    @Transactional
    public void deletePlace(String username, String queueName) {
        placeInQueueRepository.deletePlaceInQueueByUser_UsernameAndQueue_QueueName(username, queueName);
    }

    @Override
    public boolean isExistUserInQueue(String username, String queueName) {
        return placeInQueueRepository.existsByUser_UsernameAndQueue_QueueName(username, queueName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceInQueueDTO> findAllByQueueName(String queueName, Pageable pageable
            , int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return placeInQueueRepository.findAllByQueue_QueueName(queueName, changePageable)
                .map(this::placeInQueueToPlaceInQueueDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaceInQueueDTO> findAllByQueueName(String queueName) {
        return placeInQueueRepository.findAllByQueue_QueueName(queueName)
                .stream()
                .map(this::placeInQueueToPlaceInQueueDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceInQueueDTO> findAllByUsername(String username, Pageable pageable
            , int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return placeInQueueRepository.findAllByUser_Username(username,changePageable)
                .map(this::placeInQueueToPlaceInQueueDTO);
    }

    public PlaceInQueueDTO placeInQueueToPlaceInQueueDTO(PlaceInQueue place_in_queue){
        PlaceInQueueDTO dto = new PlaceInQueueDTO();
        dto.setId(place_in_queue.getId());
        dto.setUsername(place_in_queue.getUser().getUsername());
        dto.setQueueDTO(queueService.queueToQueueDTO(queueService.findByQueueName(place_in_queue.getQueue().getQueueName())));
        dto.setOrderInQueue(place_in_queue.getOrderInQueue());
        return dto;
    }

    private int findMaxOrderInQueue(String queueName) {
        List<PlaceInQueue> allQueueByName = placeInQueueRepository.findAllByQueue_QueueName(queueName);

        int maxInQueue = 1;

        if (allQueueByName == null)
            return maxInQueue;

        for (PlaceInQueue q : allQueueByName){
            if (q.getOrderInQueue() > maxInQueue)
                maxInQueue = q.getOrderInQueue();
        }

        return maxInQueue + 1;
    }
}
