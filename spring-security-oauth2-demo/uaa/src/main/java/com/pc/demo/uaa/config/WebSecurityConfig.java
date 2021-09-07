package com.pc.demo.uaa.config;

import com.pc.demo.uaa.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/r/r1").hasAnyAuthority("p1")
            .antMatchers("/login*").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        /*//从内存中加载用户信息
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager
            .createUser(User.withUsername("zhangsan")
                .password("$2a$10$zTKBPFaL6DgHOyqxdPnjuOTGV.7Pk6YO9VJP8z3Y0rVErfiHxWvae")
                .authorities("p1")
                .build());
        manager
            .createUser(User.withUsername("lisi")
                .password("$2a$10$zTKBPFaL6DgHOyqxdPnjuOTGV.7Pk6YO9VJP8z3Y0rVErfiHxWvae")
                .authorities("p2")
                .build());
        return manager;*/
        //从数据库中加载用户信息
        return new CustomUserDetailsService();
    }
}
