package com.matheus.pro.configdoc;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.matheus.pro.repository")
public class MongoConfig {

}