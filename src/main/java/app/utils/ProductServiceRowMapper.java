package app.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.model.pojo.ProductEntity;

@Component
public class ProductServiceRowMapper implements RowMapper<ProductEntity> {

    @Override
    public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductEntity result = new ProductEntity();
        result.setCategoryId(UUID.fromString(rs.getString("category_id")));
        result.setCategoryName(rs.getString("category_name"));

        result.setServiceId(UUID.fromString(rs.getString("service_id")));
        result.setServiceName(rs.getString("service_name"));
        result.setServiceDescription(rs.getString("service_description"));
        result.setPrice(rs.getBigDecimal("price"));
        result.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // json_agg -> List<String>
        result.setImageUrl(JsonMapperUtils.mapJsonArrayToList(rs.getString("images")));

        return result;
    }

}
