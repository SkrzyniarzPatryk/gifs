package pl.akademiakodu.gifs.service;

import pl.akademiakodu.gifs.model.Category;
import pl.akademiakodu.gifs.model.Gif;

import java.util.List;

public interface GifService {
    List<Gif> getGifs();
    List<Gif> findGif(String name);
    Gif findGifByName(String name);
    void changeTag(Gif gif, String tag);
    void toggleFavorite(Gif gif);
    List<Gif> findFavorites();
    List<Gif> searchInFavorites(String q);

    List<Gif> getGifInCategory(Category category);

    void changeCategory(Gif gif);
}
