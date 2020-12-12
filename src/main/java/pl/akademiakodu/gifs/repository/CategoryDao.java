package pl.akademiakodu.gifs.repository;

import pl.akademiakodu.gifs.model.Category;
import pl.akademiakodu.gifs.model.Gif;

import java.util.List;

public interface CategoryDao {

    void createCategory(Category category);
    List<Category> findAllCategoriess();
    void updateCategory(Long id);
    void deleteCategory(Long id);
}
