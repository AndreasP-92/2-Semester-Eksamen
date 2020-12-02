package com.example.wearegantt.repository;


import com.example.wearegantt.model.Authorities;
import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // GET ALL USERS

    public List<User> list(){
        String sql = "SELECT * FROM wag_user";

        List<User> listUser = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));

    return listUser;
    }


    //SAVE User
    public void save(User User) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);

        insertActor.withTableName("wag_user").usingColumns("user_mail","user_password","user_enabled");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(User);

        insertActor.execute(param);
    }

    // SAVE ROLE
    public void saveAuth (Authorities auth){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);

        insertActor.withTableName("wag_auth").usingColumns(
                "auth_role"
                ,   "fk_userMail"
        );
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(auth);

        insertActor.execute(param);
    }

    //    GET USER WITH mail
    public User getUser(String user_mail){
        String sql = "SELECT * FROM wag_user WHERE user_mail = ?";
        Object[] args = {user_mail};
        User user = jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(User.class));

        return user;
    }


}
