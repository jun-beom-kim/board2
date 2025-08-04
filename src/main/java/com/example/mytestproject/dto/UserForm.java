package com.example.mytestproject.dto;

import com.example.mytestproject.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
@Getter
public class UserForm {
    private Long id;
    private String username;
    private String password;
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Users toEntity() {
        return new Users(id,username, password, email);
    }

}
