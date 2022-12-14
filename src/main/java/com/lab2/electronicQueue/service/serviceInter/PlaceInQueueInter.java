package com.lab2.electronicQueue.service.serviceInter;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.entity.PlaceInQueue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceInQueueInter {
    void addPlaceInQueue(String queueName, String username);
    void deletePlace(String username, String queueName,String hostName);
    boolean isExistUserInQueue(String username, String queueName);
    void nextUser(String queueName,String hostName);
    Page<PlaceInQueueDTO> findAllByQueueName(String queueName, Pageable pageable, int pageNumber, String direction, String sort);
    List<PlaceInQueueDTO> findAllByQueueName(String queueName);
    Page<PlaceInQueueDTO> findAllByUsername(String username, Pageable pageable, int pageNumber, String direction, String sort);
}
