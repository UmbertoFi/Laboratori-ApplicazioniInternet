package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

 @Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /* @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler; */
    /* @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } */

    /* @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    } */
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                 .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                // non abbiamo bisogno di una sessione
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .cors().and()
//                .authorizeRequests()
//                .antMatchers(
//                        //HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"
//                ).permitAll()
//                .antMatchers("/public/**").permitAll()
//                .anyRequest().authenticated();
//        // Filtro Custom JWT
//        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//
//        httpSecurity.headers().cacheControl();
//
//    }

    // @Autowired
    // private UserDetailsService userDetailsService;

     @Override
     protected void configure(HttpSecurity http) throws Exception {
         //@formatter:off
         http
                 //.httpBasic().disable()
                 .csrf().disable()
                 //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 //.and()
                 .authorizeRequests().antMatchers("/demo/login","/demo/register","/demo/confirm/**", "/demo/recover/**").permitAll()
                 .anyRequest().authenticated()
                 .and()
                 .apply(new JwtConfigurer(jwtTokenProvider));



        /* .and()
            .formLogin()
         .and()
            .logout();*/
     }
/*
     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.authenticationProvider(authenticationProvider());
     }

     @Bean
     public DaoAuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider
                 = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userDetailsService);
         authProvider.setPasswordEncoder(encoder());
         return authProvider;
     }

     @Bean
     public PasswordEncoder encoder() {
         return new BCryptPasswordEncoder(11);
     }*/

     @Autowired
     JwtTokenProvider jwtTokenProvider;

     @Bean
     @Override
     public AuthenticationManager authenticationManagerBean() throws Exception {
         return super.authenticationManagerBean();
     }

     @Override
     public void configure(WebSecurity web) throws Exception {
         super.configure(web);
     }
 }
