package br.com.matheus.vendas.online.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.matheus.vendas.online.domain.users.User;

public interface UserRepository extends MongoRepository<User,String>{

    Optional<User> findByEmail(String email);



}
