package pl.akademiakodu.gifs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.akademiakodu.gifs.model.Category;
import pl.akademiakodu.gifs.model.Gif;
import pl.akademiakodu.gifs.repository.CategoryDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
   // private List<Category> categories;
    private CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        //categories = categoryDao.findAllCategoriess();
    }

    //bez bazy danych
//    public CategoryServiceImpl() {
//        createCategories();
//    }

    @Override
    public List<Category> getCategories() {
        //return categories;
        return categoryDao.findAllCategoriess();
    }

    @Override
    public Category getCategoryByName(String name) {
        return getCategories().stream().filter(category -> category.getName().equals(name)).findFirst().get();
    }


//    private void createCategories() {
//        categories = new ArrayList<>();
//        categories.add(new Category(1l, "Kategoria1"));
//        categories.add(new Category(2l, "Kategoria2"));
//        categories.add(new Category(3l, "Kategoria3"));
//    }
}
