package com.enlanguage.system;


import com.enlanguage.system.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class EnlanguageApplication {

  @Autowired
  UserRepository userRepository;


  public static void main(String[] args) {
    SpringApplication.run(EnlanguageApplication.class, args);

  }


}
