package com.lab2.electronicQueue.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDTO {
    long id;
    String queueName;
    String hostName;
    int numberOfSeats;
    int numberOfFreeSeats;
    boolean is_active;
    List<String> userNameList;

}
