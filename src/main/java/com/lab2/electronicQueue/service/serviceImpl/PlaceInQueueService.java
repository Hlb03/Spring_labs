package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.entity.PlaceInQueue;
import com.lab2.electronicQueue.repository.PlaceInQueueRepository;
import com.lab2.electronicQueue.service.serviceInter.PlaceInQueueInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PlaceInQueueService implements PlaceInQueueInter {
    private PlaceInQueueRepository placeInQueueRepository;

    @Autowired
    public void setPlaceInQueueRepository(PlaceInQueueRepository placeInQueueRepository) {
        this.placeInQueueRepository = placeInQueueRepository;
    }

    @Override
    public void addPlaceInQueue(PlaceInQueue placeInQueue) {
        placeInQueueRepository.save(placeInQueue);
    }

    @Override
    public void deletePlace(String username, String queueName) {
        placeInQueueRepository.deletePlaceInQueueByUser_UsernameAndQueue_QueueName(username, queueName);
    }

    @Override
    public Page<PlaceInQueue> findAllByQueueName(String queueName, Pageable pageable
            , int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return placeInQueueRepository.findAllByQueue_QueueName(queueName, changePageable);
    }

    @Override
    public Page<PlaceInQueue> findAllByUsername(String username, Pageable pageable
            , int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return placeInQueueRepository.findAllByUser_Username(username,changePageable);
    }
}
