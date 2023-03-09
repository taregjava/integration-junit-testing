package com.halfacode.service;

import com.halfacode.dto.UserForm;
import com.halfacode.reppository.LoginRepository;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
//@Service
public class LoginService {

    private LoginRepository loginRepository;
    private List<String> userLogged= new ArrayList<>();

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean login(UserForm userForm) throws LoginException {

        checkForm(userForm);
        String username =userForm.getUsername();
        if (userLogged.contains(userForm)){
            throw new LoginException(username+"already logged");
        }
        boolean login = loginRepository.login(userForm);
        if (login){
            userLogged.add(username);
        }
        return login;
    }

    public int getUserLoggedCount(){
        return userLogged.size();
    }
    private void checkForm(UserForm userForm) {
        assert userForm !=null;
        assert userForm.getUsername() !=null;
        assert userForm.getPassword() !=null;
    }
}
