package pl.akademiakodu.gifs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.akademiakodu.gifs.service.CategoryService;
import pl.akademiakodu.gifs.service.GifService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    public GifService gifService;
    public CategoryService categoryService;

    @Autowired
    public CategoryController(GifService gifService, CategoryService categoryService) {
        this.gifService = gifService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model, @RequestParam(required = false) String q) {
        if (q == null) {
            model.addAttribute("categories", categoryService.getCategories());
        } else {
            model.addAttribute("categories", categoryService.getCategoryByName(q));
        }
        return "categories";
    }

    @GetMapping("/{name}")
    public String getDetailCategory(Model model, @PathVariable String name) {
        model.addAttribute("gifByCategory", gifService.getGifInCategory(categoryService.getCategoryByName(name)));
        return "detailCategory";
    }
}
