package app.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.dto.ChatMessage;
import app.model.pojo.LatestChatEntity;
import app.model.pojo.LiveChatHistoryData;

@Repository
public class LiveChatRepository {

    @Autowired
    private NamedParameterJdbcTemplate db;

    public String save(ChatMessage message) {
        String sql = """
                WITH cmail AS (
                  INSERT INTO live_chat (sender_username, receiver_username, message)
                  VALUES (:sender, :receiver, :message)
                  RETURNING sender_username
                )
                SELECT u.email
                FROM cmail
                JOIN users u ON u.username = cmail.sender_username;
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sender", message.getFrom(), Types.VARCHAR)
                .addValue("receiver", message.getTo(), Types.VARCHAR)
                .addValue("message", message.getMessage(), Types.VARCHAR);

        return db.queryForObject(sql, params, String.class);
    }

    public List<LatestChatEntity> getLastMessage(String username) {

        String query = """
                WITH user_chats AS (
                    SELECT
                        sender_username,
                        receiver_username,
                        message,
                        created_at,
                        CASE
                            WHEN sender_username = :username THEN receiver_username
                            ELSE sender_username
                        END AS chat_partner
                    FROM live_chat
                    WHERE sender_username = :username OR receiver_username = :username
                ),
                latest_chats AS (
                    SELECT DISTINCT ON (chat_partner)
                        sender_username,
                        receiver_username,
                        message,
                        created_at,
                        chat_partner
                    FROM user_chats
                    ORDER BY chat_partner, created_at DESC
                )
                SELECT
                    lc.message,
                    lc.created_at,
                    u.username,
                    u.full_name,
                    u.profile_picture,
                    u.role
                FROM latest_chats lc
                JOIN users u ON u.username = lc.chat_partner
                ORDER BY lc.created_at DESC
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("username", username, Types.VARCHAR);

        List<LatestChatEntity> rs = db.query(query, params, new BeanPropertyRowMapper<>(LatestChatEntity.class));

        return rs;

    }

    public List<LiveChatHistoryData> getChatHistory(String currentUser, String withUser) {
        String sql = """
                SELECT
                    lc.id,
                    lc.message,
                    lc.created_at,
                    CASE
                        WHEN lc.sender_username = :currentUser THEN true
                        ELSE false
                    END AS own_message,
                    u.username   AS other_username,
                    u.full_name  AS other_full_name,
                    u.profile_picture AS other_profile_picture,
                    u.role       AS other_role
                FROM live_chat lc
                JOIN users u
                    ON u.username = :withUser
                WHERE (lc.sender_username = :currentUser AND lc.receiver_username = :withUser)
                    OR (lc.sender_username = :withUser AND lc.receiver_username = :currentUser)
                ORDER BY lc.created_at ASC

                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("currentUser", currentUser, Types.VARCHAR)
                .addValue("withUser", withUser, Types.VARCHAR);

        List<LiveChatHistoryData> rs = db.query(sql, params, new BeanPropertyRowMapper<>(LiveChatHistoryData.class));

        return rs;
    }

}
