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

    @Modifying
    @Query(value = "UPDATE queue SET number_of_free_seats = number_of_free_seats-1 where id =: id", nativeQuery = true)
    void subFreeSeats(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE queue SET number_of_free_seats = number_of_free_seats+1 where id =: id", nativeQuery = true)
    void addFreeSeats(@Param("id") Long id);

    boolean existsByUser_UsernameAndQueueName(String username,String queueName);
    Page<Queue> findAll(Pageable pageable);
    Page<Queue> findAllByActive(Pageable pageable, boolean isActive);
    Page<Queue> findAllByUser_Username(String username, Pageable pageable);
    Queue findByQueueName(String queueName);
    Queue findQueueById(Long id);

}
