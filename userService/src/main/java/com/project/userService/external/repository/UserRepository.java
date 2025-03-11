package com.project.userService.external.repository;

import com.project.userService.model.User;
import com.project.userService.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
