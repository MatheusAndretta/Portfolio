Descrição do Projeto
Nome do Projeto: Notificador de Usuários

Visão Geral
Este projeto consiste em dois microserviços: um para gerenciar usuários e outro para enviar e-mails. O microserviço de usuários permite operações de criação, deleção e validação de usuários, enquanto o microserviço de e-mails se encarrega do envio de notificações para os usuários.

Funcionalidades

Microserviço de Usuários:
Criação e deleção de usuários.
Publicação de mensagens de e-mail após ações de cadastro e deleção.

Microserviço de E-mails:
Recepção de mensagens de e-mail através do RabbitMQ.
Envio de e-mails de notificação aos usuários.

Tecnologias Utilizadas
Java 17
Spring Boot
Spring Data JPA
RabbitMQ
PostgreSQL
Lombok