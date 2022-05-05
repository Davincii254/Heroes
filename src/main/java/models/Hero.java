package models;

import java.util.Objects;

public class Hero {

    private String heroName;
    private int heroAge;
    private String heroPower;
    private String heroWeakness;
    private String avatarUrl;
    private int id;
    private int squadId;

    public Hero(String heroName, int heroAge, String heroPower, String heroWeakness, String avatarUrl, int squadId) {
        this.heroName = heroName;
        this.heroAge = heroAge;
        this.heroPower = heroPower;
        this.heroWeakness = heroWeakness;
        this.avatarUrl = avatarUrl;
        this.squadId = this.squadId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if( o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return heroAge == hero.heroAge && heroName.equals(hero.heroName) && heroPower.equals(hero.heroPower) && heroWeakness.equals(hero.heroWeakness) && avatarUrl.equals(hero.avatarUrl);
    }

    @Override
    public int hashCode() {return Objects.hash(heroName, heroAge,heroPower,heroWeakness,avatarUrl); }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public void setHeroAge(int heroAge) {
        this.heroAge = heroAge;
    }

    public void setHeroPower(String heroPower) {
        this.heroPower = heroPower;
    }

    public void setHeroWeakness(String heroWeakness) {
        this.heroWeakness = heroWeakness;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSquadId(int squadId) {
        this.squadId = squadId;
    }

    public String getHeroName() {
        return heroName;
    }

    public int getHeroAge() {
        return heroAge;
    }

    public String getHeroPower() {
        return heroPower;
    }

    public String getHeroWeakness() {
        return heroWeakness;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getId() {
        return id;
    }

    public int getSquadId() {
        return squadId;
    }
}
