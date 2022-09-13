package com.lab2.electronicQueue.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    long user_id;
    String user_name;
    String user_role;
    boolean is_active;
}
