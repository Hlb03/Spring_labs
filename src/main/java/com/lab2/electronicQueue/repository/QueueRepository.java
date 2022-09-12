package com.lab2.electronicQueue.repository;

import com.lab2.electronicQueue.entity.Queue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue,Long> {

    @Modifying
    @Query(value = "UPDATE queue SET is_active = :newQueueStatus WHERE queue_name = :queueName", nativeQuery = true)
    void closeOrOpenQueue(@Param("newQueueStatus") boolean newQueueStatus, @Param("queueName") String queueName);

    boolean existsByUser_UsernameAndQueueName(String username,String queueName);

    Page<Queue> findAllByUser_Username(String username, Pageable pageable);

    Queue findByQueueName(String queueName);

}
