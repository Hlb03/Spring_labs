package com.lab2.electronicQueue.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDTOForAdmin {
    long id;
    String queueName;
    String hostName;
    int numberOfSeats;
    int numberOfFreeSeats;
    boolean is_active;
    List<UserDTO> userDTOList;

}