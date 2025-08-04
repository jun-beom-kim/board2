package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, String> {
    Users findByUsername(String username);
}
