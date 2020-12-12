package pl.akademiakodu.gifs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.akademiakodu.gifs.model.Category;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao{

    private  JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createCategory(Category category) {
        String sql = "SELECT INTO category VALUES (null, ?)";
        jdbcTemplate.update(sql, category.getName());
    }

    @Override
    public List<Category> findAllCategoriess() {
        String sql = "SELECT * FROM category";
        List<Category> categories = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Category(
                        rs.getLong("id"),
                        rs.getString("name")
                ));
        return categories;
    }

    @Override
    public void updateCategory(Long id) {
    }

    @Override
    public void deleteCategory(Long id) {
        String sql = "DELETE FROM category WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }
}
