package app.repository;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.dto.NewServiceProduct;
import app.model.pojo.BaseServiceProduct;
import app.model.pojo.ProductEntity;
import app.utils.ProductServiceRowMapper;

@Repository
public class ProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate db;

    @Autowired
    private ProductServiceRowMapper productMapper;

    public List<ProductEntity> getAll() {
        String sql = """
                SELECT
                    sc.id AS category_id,
                    sc.name AS category_name,
                    sc.description AS category_description,
                    s.id AS service_id,
                    s.name AS service_name,
                    s.description AS service_description,
                    s.price,
                    s.created_at,
                    COALESCE(json_agg(si.image_url) FILTER (WHERE si.image_url IS NOT NULL), '[]') AS images
                FROM service_category sc
                JOIN services s ON s.category_id = sc.id
                LEFT JOIN service_images si ON si.service_id = s.id
                GROUP BY sc.id, sc.name, sc.description, s.id, s.name, s.description, s.price, s.created_at
                ORDER BY sc.name, s.name
                """;
        List<ProductEntity> rs = db.query(sql, productMapper);
        return rs;
    }

    public ProductEntity findDetails(String id) {
        String sql = """
                SELECT
                    sc.id AS category_id,
                    sc.name AS category_name,
                    sc.description AS category_description,
                    s.id AS service_id,
                    s.name AS service_name,
                    s.description AS service_description,
                    s.price,
                    s.created_at,
                    COALESCE(json_agg(si.image_url) FILTER (WHERE si.image_url IS NOT NULL), '[]') AS images
                FROM service_category sc
                JOIN services s ON s.category_id = sc.id
                LEFT JOIN service_images si ON si.service_id = s.id
                where s.id = :id
                GROUP BY sc.id, sc.name, sc.description, s.id, s.name, s.description, s.price, s.created_at
                ORDER BY sc.name, s.name
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id, Types.OTHER);
        ProductEntity rs = db.queryForObject(sql, params, productMapper);

        return rs;
    }

    public BaseServiceProduct save(NewServiceProduct newProducts) {
        String sql = """
                    insert into services (category_id, name, description, price)
                values (:categoryId, :name, :desc, :price)
                returning *
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("categoryId", newProducts.getCategoryId().toString(), Types.OTHER)
                .addValue("name", newProducts.getName(), Types.VARCHAR)
                .addValue("desc", newProducts.getDesc(), Types.VARCHAR)
                .addValue("price", newProducts.getPrice(), Types.NUMERIC);

        BaseServiceProduct rs = db.queryForObject(sql, params, new BeanPropertyRowMapper<>(BaseServiceProduct.class));

        return rs;
    }

    public void remove(UUID id) {
        String sql = """
                delete from services where id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id, Types.OTHER);

        db.update(sql, params);

    }
}
