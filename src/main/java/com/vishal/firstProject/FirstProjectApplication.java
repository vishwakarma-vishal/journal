package com.vishal.firstProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// to enable transaction
@EnableTransactionManagement
public class FirstProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstProjectApplication.class, args);

	}

	// for transaction
	@Bean
	public PlatformTransactionManager addTransaction(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}
