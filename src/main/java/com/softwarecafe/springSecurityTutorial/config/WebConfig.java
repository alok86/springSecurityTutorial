package com.softwarecafe.springSecurityTutorial.config;

import com.softwarecafe.springSecurityTutorial.model.UserInfo;
import com.softwarecafe.springSecurityTutorial.service.UserInfoUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
public class WebConfig {

//    @Bean
//    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator()
//    {
//        Jackson2RepositoryPopulatorFactoryBean populator = new Jackson2RepositoryPopulatorFactoryBean();
//        populator.setResources(new Resource[]{new ClassPathResource("question_data.json")});
//        return populator;
//    }
    @Bean
    public UserInfo userInfo(){
        return new UserInfo(1L,"alok","alok","seth","alok@gmail.com", passwordEncoder().encode("alok"),"padeypur","9696019403","221002","ADMIN " );
    }

    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("alok")
//                .password(this.passwordEncoder().encode("alok"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("saumya")
//                .password(this.passwordEncoder().encode("saumya"))
//                .build();
//        return new InMemoryUserDetailsManager(admin , user);
        return new UserInfoUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.requestMatchers("/api/welcome","/userservice/new")
                        .permitAll())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/questions","/api/question/**")
                                .authenticated())
          //      .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf->csrf.disable());

        return http.build();
    }

    // this bean is required when you login using form login it will return the
    // error that no authentication provider for this username and password
    // therefor we need authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
