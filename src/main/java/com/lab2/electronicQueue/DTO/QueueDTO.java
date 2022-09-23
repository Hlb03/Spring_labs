package com.lab2.electronicQueue.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDTO {
    long id;
    String queueName;
    String username;
    int numberOfSeats;
    int numberOfFreeSeats;
    boolean is_active;
}
