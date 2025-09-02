package app.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.entity.BaseUserDataLiveChat;
import app.model.entity.UserAuthDetails;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate db;

    public UserAuthDetails findDetailsUsername(String username) {
        var sql = """
                select username, password_hash, role, full_name, email from users
                where username = :username
                """;

        MapSqlParameterSource source = new MapSqlParameterSource().addValue("username", username, Types.VARCHAR);

        UserAuthDetails rs = db.queryForObject(sql, source, new BeanPropertyRowMapper<>(UserAuthDetails.class));

        return rs;
    }

    public boolean save(String username, String password_hash, String fullName, String email) {
        String sql = """
                INSERT INTO users (username, password_hash, full_name, email)
                VALUES (:username, :password, :fullName, :email)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username, Types.VARCHAR)
                .addValue("password", password_hash, Types.VARCHAR)
                .addValue("fullName", fullName, Types.VARCHAR)
                .addValue("email", email, Types.VARCHAR);

        int rowsAffected = db.update(sql, params); // <--- ini yg benar

        return rowsAffected > 0; // kalau berhasil insert, return true
    }

    public List<BaseUserDataLiveChat> getAdminData() {
        String sql = """
                select
                    username,
                    full_name,
                    profile_picture as image_url,
                    role
                from users
                where role = 'admin'
                """;

        List<BaseUserDataLiveChat> rs = db.query(
                sql,
                new BeanPropertyRowMapper<>(BaseUserDataLiveChat.class));

        return rs;
    }
}
