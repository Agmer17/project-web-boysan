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
import app.model.entity.UserProfileData;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate db;

    public UserAuthDetails findDetailsUsername(String username) {
        var sql = """
                select id, username, password_hash, role, full_name, email from users
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

    public UserProfileData getUserProfile(String username) {
        String sql = """
                select username, full_name, email, profile_picture, created_at, gender,
                phone_number
                from users
                where username = :username
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("username", username, Types.VARCHAR);

        UserProfileData rs = db.queryForObject(sql, params, new BeanPropertyRowMapper<>(UserProfileData.class));

        return rs;
    }

    public UserProfileData getUserProfile(int id) {
        String sql = """
                select username, full_name, email, profile_picture, created_at, gender,
                phone_number
                from users
                where id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id, Types.BIGINT);

        UserProfileData rs = db.queryForObject(sql, params, new BeanPropertyRowMapper<>(UserProfileData.class));

        return rs;
    }

    public UserProfileData updateUser(UserProfileData data, int id) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (isValidStringUpdate(data.getFullName())) {
            sql.append("full_name = :full_name, ");
            params.addValue("full_name", data.getFullName(), Types.VARCHAR);
        }

        if (isValidStringUpdate(data.getEmail())) {
            sql.append("email = :email, ");
            params.addValue("email", data.getEmail(), Types.VARCHAR);
        }

        if (isValidStringUpdate(data.getGender())) {
            sql.append("gender = :gender::gender_enum, ");
            params.addValue("gender", data.getGender(), Types.OTHER);
        }

        if (isValidStringUpdate(data.getProfilePicture())) {
            sql.append("profile_picture = :profile_picture, ");
            params.addValue("profile_picture", data.getProfilePicture(), Types.VARCHAR);
        }

        if (isValidStringUpdate(data.getPhoneNumber())) {
            sql.append("phone_number = :phone, ");
            params.addValue("phone", data.getPhoneNumber(), Types.VARCHAR);
        }

        int lastComma = sql.lastIndexOf(",");
        if (lastComma != -1) {
            sql.deleteCharAt(lastComma);
        }

        sql.append(
                " WHERE id = :id returning username, full_name, email, profile_picture, created_at, gender, created_at");
        params.addValue("id", id, Types.BIGINT);

        UserProfileData result = db.queryForObject(sql.toString(), params,
                new BeanPropertyRowMapper<>(UserProfileData.class));

        return result;
    }

    private boolean isValidStringUpdate(String data) {
        if (data == null) {
            return false;
        }

        return data != null || data.trim() != "";
    }
}
