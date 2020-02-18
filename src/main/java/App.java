import dao.Sql2oAnimalDao;
import dao.Sql2oSightingDao;
import models.Animal;
import models.Sightings;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main (String[] args){
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
        Sql2oSightingDao sightingDao = new Sql2oSightingDao(sql2o);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Sightings> allSightings = sightingDao.getAll();
            model.put("sightings", allSightings);
            List<Animal> animals = animalDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        get("/sightings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "sightings-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        post("/sightings", (request, response) -> { //new
            Map<String, Object> model = new HashMap<>();
            String location = request.queryParams("location");
            String rangerName = request.queryParams("rangerName");
            Sightings newSighting = new Sightings(rangerName,location);
            sightingDao.add(newSighting);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "animals-form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/animals",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = animalDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model,"animals.hbs");
        },new HandlebarsTemplateEngine());


        post("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            int age = Integer.parseInt(request.queryParams("age"));
            boolean endangered =Boolean.parseBoolean(request.queryParams("endangered"));
            String health = request.queryParams("health");
            Animal newAnimal = new Animal(name,age,endangered,health);        //See what we did with the hard coded categoryId?
            animalDao.add(newAnimal);

            List<Animal> animals = animalDao.getAll(); //refresh list of links for navbar.
            model.put("animals", animals);

            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sightings/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            sightingDao.clearAllSightings();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/animals/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            animalDao.clearAllAnimals();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        get("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSightingToFind = Integer.parseInt(req.params("id")); //new
            Sightings foundSighting = sightingDao.findById(idOfSightingToFind);
            model.put("sighting", foundSighting);
            //List<Task> allTasksByCategory = categoryDao.getAllTasksByCategory(idOfCategoryToFind);
            //model.put("tasks", allTasksByCategory);
            model.put("sightings", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sighting.hbs"); //new
        }, new HandlebarsTemplateEngine());


        get("/sightings/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSighting", true);
            Sightings sighting = sightingDao.findById(Integer.parseInt(req.params("id")));
            model.put("sighting", sighting);
            model.put("sightings", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sightings-form.hbs");
        }, new HandlebarsTemplateEngine());


        post("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSightingToEdit = Integer.parseInt(req.params("id"));
            String newRangerName = req.queryParams("rangerName");
            String newLocation = req.queryParams("location");
            sightingDao.update(idOfSightingToEdit, newRangerName,newLocation);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual animal
       get("/animals/:animal_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToDelete = Integer.parseInt(req.params("animal_id"));
            animalDao.deleteById(idOfAnimalToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());





     get("/animals/:animal_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Integer idOfAnimalToFind = Integer.parseInt(req.params("animal_id")); //pull id - must match route segment
            Animal foundAnimal = animalDao.findById(idOfAnimalToFind); //use it to find task

            model.put("animal", foundAnimal); //add it to model for template to display

            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());


        get("/animals/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animal animal = animalDao.findById(Integer.parseInt(req.params("id")));
            model.put("animal", animal);
            model.put("editTask", true);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/animals/:id", (request, response) -> { //URL to update animal on POST route
            Map<String, Object> model = new HashMap<>();
            int animalToEditId = Integer.parseInt(request.params("id"));
            String newName = request.queryParams("name");
            int newAge = Integer.parseInt(request.queryParams("age"));
            Boolean newEndangered =Boolean.parseBoolean(request.queryParams("endangered"));
            String newHealth = request.queryParams("health");
            animalDao.update(animalToEditId,newName,newAge,newEndangered,newHealth);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }

    }

