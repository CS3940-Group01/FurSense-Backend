package com.project.userService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String profilePhoto;

}



