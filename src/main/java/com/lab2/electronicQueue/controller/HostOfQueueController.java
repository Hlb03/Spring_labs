package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/host")
public class HostOfQueueController {
    private final QueueService queueService;

    @Autowired
    public HostOfQueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/all/page/{pageNumber}")
    public String getAllHostQueue(@AuthenticationPrincipal User user
            , Model model
            , @PageableDefault(size = 10) Pageable pageable
            , @PathVariable("pageNumber") int pageNumber
            , @RequestParam(required = false, defaultValue = "asc", value = "direction") String direction
            , @RequestParam(required = false, defaultValue = "id",value = "sort") String sort){
        Page<QueueDTO> queuePage = queueService.findAllQueueFromUser(user.getUsername(),pageable,pageNumber,direction,sort);
        List<QueueDTO> queueDTOList = queuePage.getContent();
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageable",queuePage);
        model.addAttribute("queueDTOList",queueDTOList);

        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        return "allHostQueues";
    }

    @GetMapping("/{queueName}")
    public String getHostQueue(@PathVariable("queueName") String queueName, @AuthenticationPrincipal User user
    , Model model){
        QueueDTO selectedQueue = queueService.findToHost(queueName, user.getUsername());
        model.addAttribute("selectedQueue", selectedQueue);
        return "managerQueue";
    }

    @PostMapping("/block/queue/{queueName}")
    public String blockQueue(@PathVariable("queueName") String queueName, @AuthenticationPrincipal User user){
        queueService.closeOrOpenQueue(queueName,user.getUsername());
        return "redirect:/host/all/page/1";
    }

    @PostMapping("/delete/queue/{id}")
    public String deleteQueue(@PathVariable("id") Long id, @AuthenticationPrincipal User user){
        queueService.deleteQueueByID(id,user.getUsername());
        return "redirect:/host/all/page/1";
    }


}
