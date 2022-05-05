import dao.Sql2oHeroDao;
import dao.Sql2oSquadDao;
import models.Hero;
import models.Squad;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;
public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/hero";      //connect to todolist, not todolist_test!
        Sql2o sql2o = new Sql2o(connectionString, "kushman", "h");
        Sql2oHeroDao heroDao = new Sql2oHeroDao(sql2o);
        Sql2oSquadDao squadDao = new Sql2oSquadDao(sql2o);

        //show all heros in all squads and show all squads
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad>allSquads = squadDao.getAllSquads();
            model.put("squads",allSquads);
            List<Hero> heros = heroDao.getAllHeros();
            model.put("heros",heros);
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        //display form to create a new squad
        get("/squads/new",(request,response)->{
            Map<String, Object>model = new HashMap<>();
            List<Squad>squads = squadDao.getAllSquads();
            model.put("squads",squads);
            return new ModelAndView(model,"new-squad.hbs");
        },new HandlebarsTemplateEngine());

        //process new squad
        post("/squads",(request,response)->{
            Map<String, Object>model = new HashMap<>();
            String squadName = request.queryParams("squadName");
            String squadCause = request.queryParams("squadCause");
            int maxSize = Integer.parseInt(request.queryParams("maxSize"));
            Squad newSquad = new Squad(squadName,squadCause,maxSize);
            squadDao.addSquad(newSquad);
            response.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        //clear everything
        get("/squads/delete",(request, response)->{
            Map<String,Object>model = new HashMap<>();
            heroDao.deleteAllHeros();
            squadDao.deleteAllSquads();
            response.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        //delete squad by id
        get("/squads/:id/delete",(request, response)->{
            Map<String, Object>model = new HashMap<>();
            int idOfSquadToDelete = Integer.parseInt(request.params("id"));
            squadDao.deleteSquadById(idOfSquadToDelete);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //delete all heros
        get("/heros/delete",(request, response)->{
            Map<String,Object>model = new HashMap<>();
            heroDao.deleteAllHeros();
            response.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        //get a squad and the heros in the squad
        get("/squads/:id",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            int squadToFindId = Integer.parseInt(request.params("id"));
            Squad foundSquad = squadDao.findSquadById(squadToFindId);
            model.put("squad",foundSquad);
            List<Hero>allHerosBySquad = squadDao.getAllHeroesBySquad(squadToFindId);
            model.put("heros",allHerosBySquad);
            model.put("squads",squadDao.getAllSquads());
            return new ModelAndView(model,"squad-details.hbs");
        }, new HandlebarsTemplateEngine());

        //delete hero by id
        get("squads/:squadId/heros/:heroId/delete",(request, response)->{
            Map<String,Object>model = new HashMap<>();
            int idOfHeroToDelete = Integer.parseInt(request.params("heroId"));
            heroDao.deleteHeroById(idOfHeroToDelete);
            response.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        //show new hero form
        get("/heros/new",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            List<Squad> squads = squadDao.getAllSquads();
            model.put("squads",squads);
            return new ModelAndView(model,"new-hero.hbs");
        },new HandlebarsTemplateEngine());

        //process new hero
        post("/heros",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            String heroName = request.queryParams("heroName");
            int heroAge = Integer.parseInt(request.queryParams("heroAge"));
            String heroPower = request.queryParams("heroPower");
            String heroWeakness = request.queryParams("heroWeakness");
            String avatarUrl = request.queryParams("avatarUrl");
            int squadId = Integer.parseInt(request.queryParams("squadId"));
            Hero newHero = new Hero(heroName,heroAge,heroPower,heroWeakness, avatarUrl, squadId );
            heroDao.addHero(newHero);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //display a single hero from a squad
        get("squads/:squadId/heros/:id",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            int heroId = Integer.parseInt(request.params("id"));
            Hero foundHero = heroDao.findHeroById(heroId);
            model.put("hero",foundHero);
            int squadId = Integer.parseInt(request.params("squadId"));
            Squad foundSquad = squadDao.findSquadById(squadId);
            model.put("squad",foundSquad);
            model.put("squads",squadDao.getAllSquads());
            return new ModelAndView(model,"hero-details.hbs");
        },new HandlebarsTemplateEngine());

    }
}
