package com.lab2.electronicQueue.repository;

import com.lab2.electronicQueue.entity.PlaceInQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceInQueueRepository extends JpaRepository<PlaceInQueue,Long> {

    void deletePlaceInQueueByUser_UsernameAndQueue_QueueName(String username, String queueName);



}
