package app.repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.pojo.ImageProduct;

@Repository
public class ProductImageRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public ImageProduct save(ImageProduct serviceImages) {
        String sql = "INSERT INTO service_images (service_id, image_url) VALUES (:serviceId, :imageUrl) RETURNING *";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("serviceId", serviceImages.getServiceId())
                .addValue("imageUrl", serviceImages.getImageUrl());

        return template.queryForObject(sql, params, new BeanPropertyRowMapper<>(ImageProduct.class));
    }

    public Optional<ImageProduct> findById(UUID id) {
        String sql = "SELECT * FROM service_images WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            ImageProduct imageProduct = template.queryForObject(sql, params,
                    new BeanPropertyRowMapper<>(ImageProduct.class));
            return Optional.of(imageProduct);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ImageProduct> findByServiceId(UUID serviceId) {
        String sql = "SELECT * FROM service_images WHERE service_id = :serviceId ORDER BY id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("serviceId", serviceId);

        return template.query(sql, params, new BeanPropertyRowMapper<>(ImageProduct.class));
    }

    public ImageProduct update(ImageProduct imageProduct) {
        String sql = "UPDATE service_images image_url = :imageUrl WHERE id = :id RETURNING *";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", imageProduct.getId())
                .addValue("imageUrl", imageProduct.getImageUrl());

        return template.queryForObject(sql, params, new BeanPropertyRowMapper<>(ImageProduct.class));
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM service_images WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        template.update(sql, params);
    }

    public void deleteByServiceId(UUID serviceId) {
        String sql = "DELETE FROM service_images WHERE service_id = :serviceId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("serviceId", serviceId);

        template.update(sql, params);
    }

    public Optional<ImageProduct> findByUrl(String url) {
        String sql = """
                select * from service_images where image_url = :url limit 1
                """;

        MapSqlParameterSource params = new MapSqlParameterSource().addValue("url", url, Types.VARCHAR);

        try {
            ImageProduct result = template.queryForObject(sql, params, new BeanPropertyRowMapper<>(ImageProduct.class));

            return Optional.of(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public long countByServiceId(UUID serviceId) {
        String sql = "SELECT COUNT(*) FROM service_images WHERE service_id = :serviceId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("serviceId", serviceId);

        Integer count = template.queryForObject(sql, params, Integer.class);
        return count != null ? count : 0;
    }
}
