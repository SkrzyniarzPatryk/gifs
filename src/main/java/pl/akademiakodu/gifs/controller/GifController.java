package pl.akademiakodu.gifs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.gifs.model.Gif;
import pl.akademiakodu.gifs.repository.GifDao;
import pl.akademiakodu.gifs.service.CategoryService;
import pl.akademiakodu.gifs.service.GifService;

//toDo Zakładka: 'Categories' -> W kontrolerze, nowy dla Category, który obsługuje '/categoie' i '/categorie/{id}'.

@Controller
public class GifController {
    private GifService gifService;
    private GifDao gifDao;
    public CategoryService categoryService;

    //@Autowired tworzy nowy obiekt zamista słow new GifService().
    @Autowired
    public GifController(GifService gifService, CategoryService categoryService) {
        this.gifService = gifService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String q) {
        if (q == null) {
            model.addAttribute("gifs", gifService.getGifs());
        } else {
            model.addAttribute("gifs", gifService.findGif(q));
        }
        return "home";
    }

    @GetMapping("/gif/{name}")
    public String getGif(Model model, @PathVariable String name) {
        model.addAttribute("categoryList", categoryService.getCategories());
        model.addAttribute("editedGif", gifService.findGifByName(name));
        return "gif-details";
    }

    @PostMapping("/gif/{name}")
    public String changeTag(@ModelAttribute Gif editedGif, @PathVariable String name) {
        Gif gif = gifService.findGifByName(name);
        gif.setTag(editedGif.getTag());
        gifService.changeTag(gif, editedGif.getTag());
        return "redirect:/gif/{name}";
    }
    @PostMapping("/gif/{name}/updateCategory")
    public String changeCategory(@ModelAttribute Gif editedGif, @PathVariable String name) {
        Gif gif = gifService.findGifByName(name);
        gif.setCategoryId(editedGif.getCategoryId());
        gifService.changeCategory(gif);

        //gdyby operować na {id} a nie na name, to wyglądało bvy to tak,
        //gifService.changeCategory(editedGif)
        return "redirect:/gif/{name}";
    }

    @GetMapping("/gif/{name}/favorite")
    public String toggleFav(@PathVariable String name, @RequestParam(required = false, defaultValue = "") String r) {
        Gif gif = gifService.findGifByName(name);
        gifService.toggleFavorite(gif);
        if (r.equals("details")) {
            return "redirect:/gif/{name}";
        } else if (r.equals("favorites")) {
            return "redirect:/favorites";
        } else if (r.equals("categories")) {
            return "redirect:/categories";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/favorites")
    public String getFavorites(Model model, @RequestParam(required = false) String q) {
        if (q == null) {
            model.addAttribute("favoritesGifs", gifService.findFavorites());
        } else {
            model.addAttribute("favoritesGifs", gifService.searchInFavorites(q));
        }
        return "favorites";
    }

}
