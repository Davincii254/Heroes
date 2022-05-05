package dao;

import models.Hero;

import java.util.List;

public interface HeroDao {

    // list all heroes
    List<Hero> getAllHeros();

    // create a new hero
    void addHero(Hero hero);

    // get a speciffic hero
    Hero findHeroById(int id);

    // Delete a hero
    void deleteHeroById( int id);

    // Update the hero

    //Delete all heroes
    void deleteAllHeros();
}
