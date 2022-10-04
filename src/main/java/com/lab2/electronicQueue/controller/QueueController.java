package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.PlaceInQueue;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.service.serviceImpl.PlaceInQueueService;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/queue")
public class QueueController {
    private final QueueService queueService;
    private final PlaceInQueueService placeInQueueService;

    @Autowired
    public QueueController(QueueService queueService, PlaceInQueueService placeInQueueService) {
        this.queueService = queueService;
        this.placeInQueueService = placeInQueueService;
    }

    @GetMapping("/all/page/{pageNumber}")
    public String getAllQueue(Model model
            , @PageableDefault(size = 10) Pageable pageable
            , @PathVariable("pageNumber") int pageNumber
            , @RequestParam(required = false, defaultValue = "asc", value = "direction") String direction
            , @RequestParam(required = false, defaultValue = "id", value = "sort") String sort) {
        Page<QueueDTO> queuePage = queueService.findAllActiveQueue(true,pageable, pageNumber, direction, sort);
        List<QueueDTO> queueDTOList = queuePage.getContent();
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageable", queuePage);
        model.addAttribute("queueDTOList", queueDTOList);

        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        return "allQueues";
    }

    @GetMapping("/{id}")
    public String getQueueById(@PathVariable("id") Long id, Model model) {

        QueueDTO selectedQueue = queueService.queueToQueueDTO(queueService.findById(id));
        List<PlaceInQueueDTO> usersInQueue = placeInQueueService.findAllByQueueName(selectedQueue.getQueueName());

        model.addAttribute("selectedQueue", selectedQueue);
        model.addAttribute("usersInQueue", usersInQueue);

        return "queueInfo";
    }

     /*
        Ambiguous. Spring can't definitely match which one to use.
        Reason - both {id} and {queueName} come as String param
     */
//    @GetMapping("/{queueName}")
//    public String getQueueByQueueName(@PathVariable("queueName") String queueName, Model model){
//        QueueDTO selectedQueue = queueService.queueToQueueDTO(queueService.findByQueueName(queueName));
//        model.addAttribute("selectedQueue",selectedQueue);
//        return "queuePage";
////        return "queueInfo";
//    }
}
