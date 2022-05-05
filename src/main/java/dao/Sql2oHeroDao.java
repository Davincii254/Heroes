package dao;

import models.Hero;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oHeroDao implements HeroDao {

    private final Sql2o sql2o;

    public Sql2oHeroDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void getDrivers(){
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Hero> getAllHeros() {
        getDrivers();
        try(Connection con = sql2o.open()){
            return con.createQuery(" SELECT * FROM heros")
                    .executeAndFetch(Hero.class);
        }
    }

    @Override
    public void addHero(Hero hero) {
        getDrivers();
        String sql = "INSERT INTO heros(heroName, heroAge, heroPower, heroWeakness, squadId, avatarUrl) VALUES (:heroName, :heroAge, :heroPower, :heroWeakness, :sqaudId, :avatarUrl)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(hero)
                    .executeUpdate()
                    .getKey();
                    hero.setId(id);
        } catch (Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public Hero findHeroById(int id) {
        getDrivers();
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM heros WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Hero.class);
        }
    }

    @Override
    public void deleteHeroById(int id) {
        getDrivers();
        String sql = "DELETE FROM heros WHERE id -:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteAllHeros() {
        getDrivers();
        String sql = "DELETE FROM heros";
        try(Connection con =  sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println(e);
        }
    }
}
