package com.halfacode.reppository;

import com.halfacode.dto.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LoginRepository {


    Map<String,String> users;

    public LoginRepository(){
        users =new HashMap<>();
        users.put("user1","p1");
        users.put("user2","p3");
        users.put("user3","p4");
    }

    public boolean login(UserForm userForm){
        String username= userForm.getUsername();
        String password = userForm.getPassword();

        return users.keySet().contains(username) && users.get(username).equals(password);
    }
}
