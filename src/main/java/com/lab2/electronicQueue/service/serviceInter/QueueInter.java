package com.lab2.electronicQueue.service.serviceInter;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.Queue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueInter {
    void addQueue (Queue queue);
    void update (Queue queue, String username);
    void deleteQueueByID(Long id, String username);
    void  closeOrOpenQueue(String queueName, String username);
    boolean existsByUser_UsernameAndQueueName(String username,String queueName);
    Page<QueueDTO> findAllQueueFromUser(String username, Pageable pageable, int pageNumber, String direction, String sort);
    Page<QueueDTO> findAllQueue(Pageable pageable, int pageNumber, String direction, String sort);
    Page<QueueDTO> findAllActiveQueue(boolean isActive, Pageable pageable, int pageNumber, String direction, String sort);
    Queue findByQueueName(String queueName);
    QueueDTO findToHost(String queueName, String username);
    Queue findById(Long id);
    void addFreeSeat(Long id);
    void subFreeSeat(Long id);
}
