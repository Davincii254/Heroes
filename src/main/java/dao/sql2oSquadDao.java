package dao;

import models.Hero;
import models.Squad;
import org.sql2o.Sql2o;

import java.util.List;

public class sql2oSquadDao implements SquadDao{

    private final Sql2o sql2o;

    public sql2oSquadDao(Sql2o sql2o) {
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
    public List<Squad> getAllSquads() {
        return null;
    }

    @Override
    public void addSquad(Squad squad) {

    }

    @Override
    public Squad findSquadById(int id) {
        return null;
    }

    @Override
    public List<Hero> getAllHeroesBySquad(int squadId) {
        return null;
    }

    @Override
    public void deleteSquadById(int id) {

    }

    @Override
    public void deleteAllSquads() {

    }
}
