package pl.akademiakodu.gifs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.akademiakodu.gifs.model.Category;
import pl.akademiakodu.gifs.model.Gif;
import pl.akademiakodu.gifs.repository.GifDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GifServiceImpl implements GifService {
    private GifDao gifDao;
   // private List<Gif> gifs;


    @Autowired
    public GifServiceImpl(GifDao gifDao) {
        this.gifDao = gifDao;
       // gifs = gifDao.findAllGifs();
       // updateGif();
    }
    private void updateGif() {
        //gifs = gifDao.findAllGifs();
    }

    @Override
    public List<Gif> getGifs() {
        return gifDao.findAllGifs();
    }

    @Override
    public List<Gif> findGif(String name) {
        return getGifs().stream().filter(gif ->
                gif.getTag() != null ? gif.getTag().equals(name) : gif.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public Gif findGifByName(String name) {
        //return gifs.stream().filter(gif -> gif.getName().equals(name)).findFirst().get();
        return gifDao.findGifByNameSQL(name);
    }

    @Override
    public void changeTag(Gif gif, String tag) {
        gif.setTag(tag);
        gif.setFavorite(null);
        gif.setName(null);
        gif.setCategoryId(null);
        gifDao.updateGif(gif);
       // updateGif();
    }
    @Override
    public void changeCategory(Gif gif) {
        gif.setFavorite(null);
        gif.setName(null);
        gifDao.updateGif(gif);
        // updateGif();
    }

    @Override
    public void toggleFavorite(Gif gif) {

        gif.setFavorite(!gif.isFavorite());
        gifDao.updateGif(gif);
    }

    @Override
    public List<Gif> findFavorites() {
        return getGifs().stream().filter(Gif::isFavorite).collect(Collectors.toList());
    }
    @Override
    public List<Gif> searchInFavorites(String q){
        List<Gif> favorites = findFavorites();
        return favorites.stream().filter(gif -> gif.getName().equals(q)).collect(Collectors.toList());
    }
    @Override
    public List<Gif> getGifInCategory(Category category) {
        return getGifs().stream().filter(gif -> gif.getCategoryId().equals(category.getId())).collect(Collectors.toList());
    }
}