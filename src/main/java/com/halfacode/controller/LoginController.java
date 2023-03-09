package com.halfacode.controller;

import com.halfacode.dto.UserForm;
import com.halfacode.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class LoginController {


   // @Autowired
    private LoginService loginService;

   // @PostMapping("/test")
    public String login(@RequestBody UserForm userForm){
     System.out.println("Login Controller" +userForm);
     try {
         if (userForm == null){
             return "ERROR";
         }
         else if (loginService.login(userForm)) {
             return "ok";
         }else {
             return "ok";
         }
     }catch (Exception e){
         return "ERROR";
     }
    }


}
