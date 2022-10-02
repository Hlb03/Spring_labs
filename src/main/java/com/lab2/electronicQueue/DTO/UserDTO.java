package com.lab2.electronicQueue.DTO;

import com.lab2.electronicQueue.entity.UserRole;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    long userId;
    String userName;
    String userRole;
    String userEmail;
    boolean isActive;
}
