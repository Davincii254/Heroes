package dao;

import models.Hero;
import models.Squad;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSquadDao implements SquadDao{

    private final Sql2o sql2o;

    public Sql2oSquadDao(Sql2o sql2o) {
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
        getDrivers();
        String sql = "SELECT * FROM squads";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .executeAndFetch(Squad.class);
        }
    }

    @Override
    public void addSquad(Squad squad) {
        getDrivers();
        String sql = "INSERT INTO squads (squadName,squadCause,maxSize) VALUES (:squadName, :squadCause, :maxSize)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(squad)
                    .executeUpdate()
                    .getKey();
            squad.setId(id);
        }catch(Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public Squad findSquadById(int id) {
        getDrivers();
        String sql = "SELECT * FROM squads WHERE  id = :id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Squad.class);
        }
    }

    @Override
    public List<Hero> getAllHeroesBySquad(int squadId) {
        getDrivers();
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM heros WHERE squadId = :squadId")
                    .addParameter("squadId",squadId)
                    .executeAndFetch(Hero.class);
        }
    }

    @Override
    public void deleteSquadById(int id) {
        getDrivers();
        String sql = "DELETE FROM squads WHERE id = :id";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch(Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public void deleteAllSquads() {
        getDrivers();
        String sql = "DELETE FROM squads";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch(Sql2oException e){
            System.out.println(e);
        }
    }
}

