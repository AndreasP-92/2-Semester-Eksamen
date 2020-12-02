package com.example.wearegantt.repository;


import com.example.wearegantt.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProfileDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public ProfileDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    // GET ALL PROFILES

    public List<Profile> list() {
        String sql = "SELECT * FROM wag_profile";

        List<Profile> listProfile = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Profile.class));

        return listProfile;


    }

    //SAVE PROFILE
    public void save(Profile profile) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);

        insertActor.withTableName("wag_profile").usingColumns("profile_firstname","profile_lastname","profile_adress","profile_phone", "profile_country", "profile_zip", "profile_desc", "profile_jobTitle", "fk_userId");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(profile);

        insertActor.execute(param);
    }

}
