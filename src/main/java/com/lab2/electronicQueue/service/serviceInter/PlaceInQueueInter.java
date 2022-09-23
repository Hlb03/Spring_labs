package com.lab2.electronicQueue.service.serviceInter;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.entity.PlaceInQueue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceInQueueInter {
    void addPlaceInQueue(PlaceInQueue placeInQueue);
    void deletePlace(String username, String queueName);
    Page<PlaceInQueueDTO> findAllByQueueName(String queueName, Pageable pageable, int pageNumber, String direction, String sort);
    Page<PlaceInQueueDTO> findAllByUsername(String username, Pageable pageable, int pageNumber, String direction, String sort);
}
