package com.enlanguage.system.server.config;

import com.enlanguage.system.server.model.Role;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.enumeration.RoleType;
import com.enlanguage.system.server.repository.UserRepository;
import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.vaadin.spring.annotation.EnableVaadinExtensions;
import org.vaadin.spring.http.HttpService;
import org.vaadin.spring.security.annotation.EnableVaadinSharedSecurity;
import org.vaadin.spring.security.shared.VaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.shared.VaadinUrlAuthenticationSuccessHandler;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;

import java.util.*;


@Configuration
@EnableWebSecurity
@EnableVaadin
@EnableVaadinSharedSecurity
@EnableJpaAuditing
@EnableVaadinExtensions
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserRepository userRepository;


  @Autowired
  UserDetailsService userDetailsService;


  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
    auth.jdbcAuthentication();
    initCreatingUser();
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();

    http.authorizeRequests().antMatchers("/login/**").anonymous()
        .antMatchers("/vaadinServlet/UIDL/**")
        .permitAll().antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll().anyRequest()
        .authenticated();

    http.httpBasic().disable();
    http.formLogin().disable();

    http.exceptionHandling()
        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

    http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy());

    http.authorizeRequests().antMatchers("/").permitAll().and()
        .authorizeRequests().antMatchers("/console/**").permitAll();

    http.csrf().disable();
    http.headers().frameOptions().disable();


  }


  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/VAADIN/**");
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }


  @Bean
  public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new SessionFixationProtectionStrategy();
  }

  @Bean
  public VaadinAuthenticationSuccessHandler vaadinAuthenticationSuccessHandler(
      HttpService httpService,
      VaadinRedirectStrategy vaadinRedirectStrategy) {
    return new VaadinUrlAuthenticationSuccessHandler(httpService, vaadinRedirectStrategy, "/");
  }


  public void initCreatingUser() {

    User user = new User();
    user.setId(1L);
    user.setActive(true);
    user.setCreateAt(new Date());
    user.setPassword("admin123");
    user.setUsername("admin");
    user.setRoles(Arrays.asList(initUserRole(user), initAdminRole(user)));

    User user2 = new User();
    user2.setId(2L);
    user2.setActive(true);
    user2.setCreateAt(new Date());
    user2.setPassword("user123");
    user2.setUsername("user");
    user2.setRoles(Collections.singletonList(initUserRole(user2)));

    userRepository.save(user);
    userRepository.save(user2);
  }

  private Role initAdminRole(User user) {

    Role adminRole = new Role();
    adminRole.setType(RoleType.ROLE_ADMIN);
    adminRole.setId(2L);
    adminRole.setCreatedDate(new Date());
    adminRole.setUser(user);

    return adminRole;
  }

  private Role initUserRole(User user) {
    Role userRole = new Role();
    userRole.setType(RoleType.ROLE_USER);
    userRole.setId(1L);
    userRole.setCreatedDate(new Date());
    userRole.setUser(user);

    return userRole;
  }

}
