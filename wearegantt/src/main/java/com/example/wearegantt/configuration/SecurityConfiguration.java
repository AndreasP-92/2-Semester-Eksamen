package com.example.wearegantt.configuration;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.User;
import com.example.wearegantt.services.ObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    ObjectManager objectManager = new ObjectManager();


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT user_mail, user_password, user_enabled "
                        + "FROM users "
                        + "WHERE user_mail = ?")
                .authoritiesByUsernameQuery("SELECT fk_userMail, auth_role "
                        + "FROM auth "
                        + "WHERE fk_userMail = ?");
    }

    @Override
//    hasAnyRole("ADMIN", "USER","TRIAL","SUPERUSER")
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/newsfeed/*").hasAnyRole("ADMIN", "USER","TRIAL","SUPERUSER")
                .antMatchers("/projects/*").hasAnyRole("ADMIN", "TRIAL", "NORMALUSER", "SUPERUSER")
                .antMatchers("/profile/*").hasAnyRole("ADMIN", "TRIAL", "NORMALUSER", "SUPERUSER")
                .antMatchers("/support/*").hasAnyRole("ADMIN", "TRIAL", "NORMALUSER", "SUPERUSER")
                .antMatchers("/").permitAll()
                .and().formLogin()
                .permitAll()
                .loginPage("/login")
                .usernameParameter("user_mail")
                .passwordParameter("user_password")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/profile")
                .failureUrl("/")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        String name = authentication.getName();
                        System.out.println("Logged in user: " + name);

                        String url;

                        User user = objectManager.userRepo.getOneUser(name);
                        Organization organization = objectManager.organizationRepo.getOneOrgWId(user.getFk_orgId());

                        if(user.getFk_orgId() == 0){
                            url = "/waiting";

                        }else{
                            url = "/newsfeed/"+organization.getOrg_name();

                        }


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

}
