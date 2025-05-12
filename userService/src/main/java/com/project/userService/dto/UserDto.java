package com.project.userService.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String profilePhoto;
}
