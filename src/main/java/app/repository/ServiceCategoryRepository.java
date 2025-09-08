package app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.entity.ServiceCategory;

@Repository
public class ServiceCategoryRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    public ServiceCategory save(ServiceCategory serviceCategory) {
        String sql = "INSERT INTO service_category (name, description) VALUES (:name, :description) RETURNING *";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", serviceCategory.getName())
                .addValue("description", serviceCategory.getDescription());

        return template.queryForObject(sql, params,
                new BeanPropertyRowMapper<>(ServiceCategory.class));
    }

    public Optional<ServiceCategory> findById(UUID id) {
        String sql = "SELECT * FROM service_category WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            ServiceCategory serviceCategory = template.queryForObject(sql, params,
                    new BeanPropertyRowMapper<>(ServiceCategory.class));
            return Optional.of(serviceCategory);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ServiceCategory> findAll() {
        String sql = "SELECT * FROM service_category ORDER BY name";
        return template.query(sql, new BeanPropertyRowMapper<>(ServiceCategory.class));
    }

    public List<ServiceCategory> findAllByName(String name) {
        String sql = "SELECT * FROM service_category WHERE name ilike :name";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", "%" + name + "%");

        List<ServiceCategory> serviceCategory = template.query(sql, params,
                new BeanPropertyRowMapper<>(ServiceCategory.class));
        return serviceCategory;

    }

    public Optional<ServiceCategory> findByName(String name) {
        String sql = "SELECT * FROM service_category WHERE name = :name";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name);

        try {
            ServiceCategory serviceCategory = template.queryForObject(sql, params,
                    new BeanPropertyRowMapper<>(ServiceCategory.class));
            return Optional.of(serviceCategory);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public ServiceCategory update(ServiceCategory serviceCategory) {
        String sql = "UPDATE service_category SET name = :name, description = :description WHERE id = :id RETURNING *";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", serviceCategory.getId())
                .addValue("name", serviceCategory.getName())
                .addValue("description", serviceCategory.getDescription());

        return template.queryForObject(sql, params,
                new BeanPropertyRowMapper<>(ServiceCategory.class));
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM service_category WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        template.update(sql, params);
    }

    public boolean existsById(UUID id) {
        String sql = "SELECT COUNT(*) FROM service_category WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        Integer count = template.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM service_category WHERE name = :name";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name);

        Integer count = template.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
