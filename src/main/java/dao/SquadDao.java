package dao;

import models.Hero;
import models.Squad;

import java.util.List;

public interface SquadDao {

    //list all squads
    List<Squad> getAllSquads();

    //create
    void addSquad( Squad squad);

    //find by id
    Squad findSquadById( int id);

    //get all heros in a squad
    List<Hero> getAllHeroesBySquad(int squadId);

    //delete squad
    void deleteSquadById(int id);

    //delete all squads
    void deleteAllSquads();
}
