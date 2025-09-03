package app.repository;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.dto.ChatMessage;

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

}
