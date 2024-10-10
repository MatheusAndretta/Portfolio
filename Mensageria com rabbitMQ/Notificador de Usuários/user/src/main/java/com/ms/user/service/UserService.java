package com.ms.user.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.user.model.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repository.UserRepository;

/**
 * Serviço responsável pela lógica de negócios relacionada a usuários.
 * Inclui operações para salvar e deletar usuários, além de publicar mensagens
 * relacionadas
 * ao registro e remoção de usuários.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProducer userProducer;

    /**
     * Salva um novo usuário no repositório e publica uma mensagem de email de
     * boas-vindas.
     *
     * @param userModel O objeto UserModel a ser salvo.
     * @return O objeto UserModel salvo, incluindo os dados gerados (como ID).
     */
    @Transactional
    public UserModel save(UserModel userModel) {
        userModel = userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);
        return userModel;
    }

    /**
     * Deleta um usuário do repositório e publica uma mensagem de email informando a
     * desativação.
     *
     * @param userModel O objeto UserModel a ser deletado.
     * @return O objeto UserModel que foi deletado.
     * @throws UserPrincipalNotFoundException Se o usuário não for encontrado.
     */
    public UserModel delete(UserModel userModel) throws UserPrincipalNotFoundException {

        Optional<UserModel> optionalUser = userRepository.findById(userModel.getUserID());

        if (optionalUser.isPresent()) {
            UserModel deleterUser = optionalUser.get();

            userProducer.publishMessageEmailDelete(deleterUser);

            userRepository.delete(deleterUser);

            return deleterUser;
        } else {
            throw new UserPrincipalNotFoundException("Usuário não encontrado");
        }

    }

}
