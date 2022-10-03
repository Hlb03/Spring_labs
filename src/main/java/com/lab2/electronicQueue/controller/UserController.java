package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.DTO.PlaceInQueueDTO;
import com.lab2.electronicQueue.DTO.QueueDTO;
import com.lab2.electronicQueue.DTO.UserDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.service.serviceImpl.PlaceInQueueService;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import com.lab2.electronicQueue.service.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final QueueService queueService;
    private final PlaceInQueueService placeInQueueService;

    @Autowired
    public UserController(UserService userService, QueueService queueService, PlaceInQueueService placeInQueueService) {
        this.userService = userService;
        this.queueService = queueService;
        this.placeInQueueService = placeInQueueService;
    }

    @GetMapping
    public String getUserPage(Model model, Principal principal){
        UserDTO selectedUser = userService.userToUserDTO(userService.findUserByUsername(principal.getName()));
        model.addAttribute("selectedUser",selectedUser);
        if(selectedUser.getUserRole().equals("USER")){
            return "userPersonalOffice";
        }
        return "adminPersonalOffice";
    }

    @GetMapping("/add/queue")
    public String getAddQueuePage(Model model){
        model.addAttribute("queue", new Queue());
        return "addQueue";
    }

    @PostMapping("/add/queue")
    public String addQueue(@Valid @ModelAttribute Queue queue, Errors errors, Principal principal){
        if(errors.hasErrors()){
            return "addQueue";
        }
        queueService.addQueue(queue,principal.getName());
        return "redirect:/user";
    }

    @GetMapping("/all/page/{pageNumber}")
    public String getAllQueue(Principal principal
            , Model model
            , @PageableDefault(size = 10) Pageable pageable
            , @PathVariable("pageNumber") int pageNumber
            , @RequestParam(required = false, defaultValue = "asc", value = "direction") String direction
            , @RequestParam(required = false, defaultValue = "id",value = "sort") String sort){
        Page<PlaceInQueueDTO> placeInQueueDTOPage = placeInQueueService.findAllByUsername(principal.getName(),pageable,pageNumber,direction,sort);
        List<PlaceInQueueDTO> placeInQueueDTOList = placeInQueueDTOPage.getContent();

        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageable",placeInQueueDTOPage);
        model.addAttribute("queueDTOList",placeInQueueDTOList);

        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");

        return "allUserQueues";
    }

    @GetMapping("/host/all/page/{pageNumber}")
    public String getUsersQueue(Principal principal, Model model,
                                @PageableDefault(size = 10) Pageable pageable,
                                @PathVariable("pageNumber") int pageNumber,
                                @RequestParam(required = false, defaultValue = "asc", value = "direction") String direction,
                                @RequestParam(required = false, defaultValue = "id", value = "sort") String sort) {

        Page<QueueDTO> placeInQueueDTOPage = queueService.findAllQueueFromUser(principal.getName(),pageable,pageNumber,direction,sort);
        List<QueueDTO> placeInQueueDTOS = placeInQueueDTOPage.getContent();

        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageable",placeInQueueDTOPage);
        model.addAttribute("queueDTOList",placeInQueueDTOS);

        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        return "allUserHostQueues";
    }


    @PostMapping("/record")
    public String makeARecord(Principal principal, @RequestParam("queueName") String queueName){
        placeInQueueService.addPlaceInQueue(queueName,principal.getName());
        return "redirect:/queue/all/page/1";
    }
}
