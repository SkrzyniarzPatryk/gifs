package pl.akademiakodu.gifs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.akademiakodu.gifs.model.Gif;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GifDaoImpl implements GifDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GifDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createGif(Gif gif) {
        String sql = "SELECT INTO gifs VALUES (null, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, gif.getName(), gif.getTag(), gif.isFavorite(), gif.getCategoryId());
    }

    @Override
    public List<Gif> findAllGifs() {
        String sql = "SELECT * FROM gifs";

        List<Gif> gifs = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Gif(rs.getLong("ID"),
                        rs.getString("name"),
                        rs.getString("tag"),
                        rs.getBoolean("favorite"),
                        rs.getLong("category_id"))
        );
        return gifs;
    }
    @Override
    public Gif findGifByNameSQL(String name) {
        String sql = "SELECT * FROM gifs g WHERE g.NAME = '" + name + "'";
        Gif gif;
        gif = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Gif(rs.getLong("ID"),
                        rs.getString("name"),
                        rs.getString("tag"),
                        rs.getBoolean("favorite"),
                        rs.getLong("category_id"))
        );
        return gif;
    }
/*

(rs, rowNum) ->
                        new Gif(rs.getString("name"),
                            rs.getString("tag"),
                            rs.getBoolean("favorite"),
                            rs.getLong("category_id"))

    jest równoważne stworzeniu nowej klasy mapującej elementy z repo do javy.
    w nawiasach dajemy argumenty które przyjmuje metoda a po -> piszemy ciało metody

    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = new Customer();
        customer.setID(rs.getLong("ID"));
        customer.setName(rs.getString("NAME"));
        customer.setAge(rs.getInt("AGE"));
        customer.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());

        return customer;

    }
 */

    @Override
    public void updateGif(Gif gif) {
        if (gif.getTag() != null) {
            String sql = "UPDATE gifs g SET g.TAG = '" + gif.getTag() + "' WHERE g.ID = ?";
            jdbcTemplate.update(sql, gif.getId());
        }
        if (gif.isFavorite() != null) {
            String sql = "UPDATE gifs g SET g.FAVORITE = " + gif.isFavorite() + " WHERE g.ID = ?";
            jdbcTemplate.update(sql, gif.getId());
        }
        if (gif.getCategoryId() != null) {
            String sql = "UPDATE gifs g SET g.CATEGORY_ID = " + gif.getCategoryId() + " WHERE g.ID = ?";
            jdbcTemplate.update(sql, gif.getId());
        }
    }
//    @Override
//    public void updateGif(Gif gif) {
//        String sql = "UPDATE gifs g SET ";
//        if (gif.getTag() != null) {
//            sql = sql + "g.tag = '" + gif.getTag() + "', ";
//        }
//        if (gif.isFavorite() != null) {
//            sql = sql + "g.FAVORITE = " + gif.isFavorite() + ", ";
//        }
//        if (gif.getCategoryId() != null) {
//            sql = sql + "g.CATEGORY_ID = " + gif.getCategoryId() + "";
//        }
//        sql = sql + " WHERE id = " + gif.getId();
//        jdbcTemplate.update(sql);
//    }

    //ToDo na podstawie metody createGif napisz metodę deletGif po id/ Query bylło na ćwiczemoach
    @Override
    public void deleteGif(Long id) {
        String sql = "DELETE FROM gifs WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    //tylko do inicializacji
    //@EventListener(ApplicationReadyEvent.class)

    public void createGifs() {
        List<Gif> gifs = new ArrayList<>();
        gifs.add(new Gif("android-explosion", "explode", false, 1l));
        gifs.add(new Gif("ben-and-mike", true, 2l));
        gifs.add(new Gif("book-dominos", true, 3l));
        gifs.add(new Gif("compiler-bot", true, 2l));
        gifs.add(new Gif("cowboy-coder", false, 1l));
        gifs.add(new Gif("infinite-andrew", true, 1l));
        gifs.add(new Gif("0e9b8ad60c9e93bd35fc86936fe9ad6c", false, 2l));

        for (Gif gif : gifs) {
            String sql = "INSERT INTO gifs VALUES (null, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, gif.getName(), gif.getTag(), gif.isFavorite(), gif.getCategoryId());
        }
    }
}
