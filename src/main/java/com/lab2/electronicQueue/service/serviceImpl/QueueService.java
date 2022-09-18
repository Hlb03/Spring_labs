package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.repository.QueueRepository;
import com.lab2.electronicQueue.service.serviceInter.QueueInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueueService implements QueueInter {

    @Autowired
    private QueueRepository queueRepository;

    @Override
    @Transactional
    public void addQueue(Queue queue) {
        queueRepository.save(queue);
    }

    @Override
    @Transactional
    public void update(Queue queue) {
        queueRepository.save(queue);
    }

    @Override
    @Transactional
    public void closeOrOpenQueue(boolean newQueueStatus, String queueName) {

        queueRepository.closeOrOpenQueue(newQueueStatus,queueName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUser_UsernameAndQueueName(String username, String queueName) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QueueDTO> findAllQueueFromUser(String username, Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return queueRepository.findAllByUser_Username(username,changePageable)
                .map(this::QueueToQueueDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Queue findByQueueName(String queueName) {
        return queueRepository.findByQueueName(queueName);
    }

    public QueueDTO QueueToQueueDTO(Queue queue){
        QueueDTO dto = new QueueDTO();
        dto.setId(queue.getId());
        dto.setQueueName(queue.getQueueName());
        dto.setUsername(queue.getUser().getUsername());
        dto.set_active(queue.isActive());
        return dto;
    }

}
