package app.repository;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import app.model.dto.ChatMessage;

@Repository
public class LiveChatRepository {

    @Autowired
    private NamedParameterJdbcTemplate db;

    @Async
    public void save(ChatMessage message) {
        String sql = """
                insert into live_chat (sender_username, receiver_username, message)
                values( :sender , :receiver, :message )
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("sender", message.getFrom(), Types.VARCHAR);
        params.addValue("receiver", message.getTo(), Types.VARCHAR);
        params.addValue("message", message.getMessage(), Types.VARCHAR);

        db.update(sql, params);

    }
}
