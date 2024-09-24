package br.com.matheus.vendas.online.usercase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.matheus.vendas.online.domain.users.User;
import br.com.matheus.vendas.online.repository.UserRepository;

@Component
public class UserValidation {

    
    private UserRepository repositoryUser;

    
    @Autowired
    public UserValidation(UserRepository repositoryUser) {
        this.repositoryUser = repositoryUser;
    }



    public boolean isValidado(String email){
        Optional<User> user = repositoryUser.findByEmail(email);
        return user.isPresent();
    }



  
}
