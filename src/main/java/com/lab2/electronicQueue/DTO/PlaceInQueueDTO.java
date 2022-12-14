package com.lab2.electronicQueue.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceInQueueDTO {
    long id;
    String username;
    QueueDTO queueDTO;
    int orderInQueue;
}
