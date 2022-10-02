package com.lab2.electronicQueue.repository;

import com.lab2.electronicQueue.entity.PlaceInQueue;
import com.lab2.electronicQueue.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceInQueueRepository extends JpaRepository<PlaceInQueue,Long> {

    void deletePlaceInQueueByUser_UsernameAndQueue_QueueName(String username, String queueName);
    Page<PlaceInQueue> findAllByQueue_QueueName(String queueName, Pageable pageable);
    List<PlaceInQueue> findAllByQueue_QueueName(String queueName);
    Page<PlaceInQueue> findAllByUser_Username(String username, Pageable pageable);
    boolean existsByUser_UsernameAndQueue_QueueName(String username, String queueName);

}
