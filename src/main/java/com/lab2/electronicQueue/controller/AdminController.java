package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.DTO.UserDTO;
import com.lab2.electronicQueue.service.serviceImpl.PlaceInQueueService;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import com.lab2.electronicQueue.service.serviceImpl.UserService;
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
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final QueueService queueService;
    private final PlaceInQueueService placeInQueueService;

    @Autowired
    public AdminController(UserService userService, QueueService queueService, PlaceInQueueService placeInQueueService) {
        this.userService = userService;
        this.queueService = queueService;
        this.placeInQueueService = placeInQueueService;
    }

    @GetMapping("/all/users/page/{pageNumber}")
    public String getAllUsersPage(Model model
            , @PageableDefault(size = 10) Pageable pageable
            , @PathVariable("pageNumber") int pageNumber
            , @RequestParam(required = false, defaultValue = "asc", value = "direction") String direction
            , @RequestParam(required = false, defaultValue = "id",value = "sort") String sort){
        Page<UserDTO> userDTOPage = userService.findAllUsers(pageable, pageNumber, direction, sort);
        List<UserDTO> userDTOList = userDTOPage.getContent();
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageable",userDTOPage);
        model.addAttribute("userDTOList",userDTOList);

        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        return "allUsersForAdmin";
    }

    @PostMapping("/set/user/status/{username}")
    public String changeUserAccountStatus(@PathVariable("username") String username){
        userService.setUserVerification(username);
        return "redirect:/admin/all/users/page/1";
    }

    @PostMapping("/delete/user/{username}")
    public String deleteUserByUsername(@PathVariable("username") String username){
        userService.deleteUserByUsername(username);
        return "redirect:/admin/all/users/page/1";
    }

    @PostMapping("/set/queue/status/{queueName}")
    public String changeQueueStatus(@PathVariable("queueName") String queueName, Principal principal){
        queueService.closeOrOpenQueue(queueName, principal.getName());
        return "redirect:/admin/all/users/page/1";
    }

    @PostMapping("/delete/queue/{id}")
    public String deleteQueueById(@PathVariable("id") Long id, Principal principal){
        queueService.deleteQueueByID(id,principal.getName());
        return "redirect:/admin/all/users/page/1";
    }

}
