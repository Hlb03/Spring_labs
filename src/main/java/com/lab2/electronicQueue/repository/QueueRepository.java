package com.lab2.electronicQueue.repository;

import com.lab2.electronicQueue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueRepository extends JpaRepository<Queue,Long> {

    @Modifying
    @Query(value = "UPDATE queue SET is_active = :newQueueStatus WHERE queue_name = :userName", nativeQuery = true)
    void closeOrOpenQueue(@Param("newQueueStatus") boolean newQueueStatus, @Param("queueName") String queueName);

    boolean existsByUser_UsernameAndQueueName(String username,String queueName);
}
