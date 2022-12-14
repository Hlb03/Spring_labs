package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.service.serviceImpl.PlaceInQueueService;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/host")
public class HostOfQueueController {
    private final QueueService queueService;
    private final PlaceInQueueService placeInQueueService;

    @Autowired
    public HostOfQueueController(QueueService queueService, PlaceInQueueService placeInQueueService) {
        this.queueService = queueService;
        this.placeInQueueService = placeInQueueService;
    }

    @GetMapping("/all/page/{pageNumber}")
    public String getAllHostQueue(Principal principal
            , Model model
            , @PageableDefault(size = 10) Pageable pageable
            , @PathVariable("pageNumber") int pageNumber
            , @RequestParam(required = false, defaultValue = "asc", value = "direction") String direction
            , @RequestParam(required = false, defaultValue = "id",value = "sort") String sort){
        Page<QueueDTO> queuePage = queueService.findAllQueueFromUser(principal.getName(), pageable,pageNumber,direction,sort);
        List<QueueDTO> queueDTOList = queuePage.getContent();
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageable",queuePage);
        model.addAttribute("queueDTOList",queueDTOList);

        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        return "allUserHostQueues";
    }

    @GetMapping("/{queueName}")
    public String getHostQueue(@PathVariable("queueName") String queueName, Principal principal
                                            , Model model){
        QueueDTO selectedQueue = queueService.findToHost(queueName,principal.getName());
        List<PlaceInQueueDTO> usersInQueue = placeInQueueService.findAllByQueueName(selectedQueue.getQueueName());
        if (queueService.existsByUser_UsernameAndQueueName(principal.getName(), selectedQueue.getQueueName()))
            model.addAttribute("iAmHost", "true");

        model.addAttribute("selectedQueue", selectedQueue);
        model.addAttribute("usersInQueue", usersInQueue);
        model.addAttribute("selectedQueue", selectedQueue);

        return "queueInfo";
    }

    @PostMapping("/delete/user/{username}/queue/{queueName}")
    public String deleteUserFromQueue(@PathVariable("username") String username, @PathVariable("queueName") String queueName, Principal principal){
        placeInQueueService.deletePlace(username, queueName, principal.getName());
        return "redirect:/host/" + queueName;
    }

    @PostMapping("/block/queue/{queueName}")
    public String blockQueue(@PathVariable("queueName") String queueName, Principal principal){
        queueService.closeOrOpenQueue(queueName,principal.getName());
        return "redirect:/host/all/page/1";
    }

    @PostMapping("/delete/queue/{id}")
    public String deleteQueue(@PathVariable("id") Long id, Principal principal){
        queueService.deleteQueueByID(id,principal.getName());
        return "redirect:/host/all/page/1";
    }

    @PostMapping("/next/queue/{queueName}")
    public String nextUser(@PathVariable("queueName") String queueName, Principal principal){
        placeInQueueService.nextUser(queueName, principal.getName());
        return "redirect:/host/" + queueName;
    }
}
