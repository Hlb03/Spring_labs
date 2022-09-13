package com.lab2.electronicQueue.DTO;

import com.lab2.electronicQueue.entity.UserRole;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    long user_id;
    String user_name;
    UserRole user_role;
    String user_email;
    boolean is_active;
}
