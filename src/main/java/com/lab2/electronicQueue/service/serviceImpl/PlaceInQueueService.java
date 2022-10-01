package com.lab2.electronicQueue.service.serviceImpl;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.PlaceInQueue;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.repository.PlaceInQueueRepository;
import com.lab2.electronicQueue.service.serviceInter.PlaceInQueueInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceInQueueService implements PlaceInQueueInter {
    private PlaceInQueueRepository placeInQueueRepository;

    @Autowired
    public void setPlaceInQueueRepository(PlaceInQueueRepository placeInQueueRepository) {
        this.placeInQueueRepository = placeInQueueRepository;
    }

    @Override
    @Transactional
    public void addPlaceInQueue(PlaceInQueue placeInQueue) {
        placeInQueueRepository.save(placeInQueue);
    }

    @Override
    @Transactional
    public void deletePlace(String username, String queueName) {
        placeInQueueRepository.deletePlaceInQueueByUser_UsernameAndQueue_QueueName(username, queueName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceInQueueDTO> findAllByQueueName(String queueName, Pageable pageable
            , int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return placeInQueueRepository.findAllByQueue_QueueName(queueName, changePageable)
                .map(this::placeInQueueToPlaceInQueueDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaceInQueueDTO> findAllByQueueName(String queueName) {
        return placeInQueueRepository.findAllByQueue_QueueName(queueName)
                .stream()
                .map(this::placeInQueueToPlaceInQueueDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceInQueueDTO> findAllByUsername(String username, Pageable pageable
            , int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return placeInQueueRepository.findAllByUser_Username(username,changePageable)
                .map(this::placeInQueueToPlaceInQueueDTO);
    }

    public PlaceInQueueDTO placeInQueueToPlaceInQueueDTO(PlaceInQueue place_in_queue){
        PlaceInQueueDTO dto = new PlaceInQueueDTO();
        dto.setId(place_in_queue.getId());
        dto.setUsername(place_in_queue.getUser().getUsername());
        dto.setQueue(place_in_queue.getQueue());
        return dto;
    }
}
