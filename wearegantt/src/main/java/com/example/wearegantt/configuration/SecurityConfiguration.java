package com.example.wearegantt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT user_mail, user_password, user_enabled "
                        + "FROM users "
                        + "WHERE user_mail = ?")
                .authoritiesByUsernameQuery("SELECT fk_userMail, auth_role "
                        + "FROM auth "
                        + "WHERE fk_userMail = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/newsfeed").permitAll()
                .antMatchers("/projects").permitAll()
                .antMatchers("/profile/{profile_mail}").hasAnyRole("ADMIN", "USER","TRIAL","SUPERUSER")
                .antMatchers("/").permitAll()
                .and().formLogin()
                .permitAll()
                .loginPage("/login")
                .usernameParameter("user_mail")
                .passwordParameter("user_password")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/profile")
                .failureUrl("/")
//                    .successForwardUrl("/login_success_handler")
//                    .failureForwardUrl("/login_failure_handler")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        String name = authentication.getName();
                        System.out.println("Logged in user: " + name);

                        String url = "/projects";

                        httpServletResponse.sendRedirect(url);

                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        System.out.println("Login Failure!!!....");

                        httpServletResponse.sendRedirect("/");
                    }
                })
                .and().exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
