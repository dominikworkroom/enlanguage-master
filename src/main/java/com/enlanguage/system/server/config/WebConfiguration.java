package com.enlanguage.system.server.config;

import com.enlanguage.system.server.model.Role;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.enumeration.RoleType;
import com.enlanguage.system.server.repository.UserRepository;
import org.apache.catalina.servlets.WebdavServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


@Configuration
public class WebConfiguration {

  @Bean
  ServletRegistrationBean h2servletRegistration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebdavServlet());
    registrationBean.addUrlMappings("/console/*");
    return registrationBean;
  }

}
