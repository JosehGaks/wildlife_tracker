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
    public static void main (String[] args) throws Exception{
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
        Sql2oSightingDao sightingDao = new Sql2oSightingDao(sql2o);

        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Sightings> allSightings = sightingDao.getAll();
            model.put("sightings", allSightings);
            List<Animal> animals = animalDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category
        get("/sightings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "sightings-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category
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

        //task: process new task form
        post("/animals", (request, response) -> {
            //Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            boolean endangered =Boolean.parseBoolean(request.queryParams("endangered"));
            String health = request.queryParams("health");
            int age = Integer.parseInt(request.queryParams("age"));
            Animal newAnimal = new Animal(name,age,endangered,health);        //See what we did with the hard coded categoryId?
            animalDao.add(newAnimal);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tasks
        get("/sightings/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            sightingDao.clearAllSightings();
            //taskDao.clearAllTasks();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/animals/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            animalDao.clearAllAnimals();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific category (and the tasks it contains)
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

        //get: show a form to update a category
        get("/sightings/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSighting", true);
            Sightings sighting = sightingDao.findById(Integer.parseInt(req.params("id")));
            model.put("sighting", sighting);
            model.put("sightings", sightingDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "category-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/sightings/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSightingToEdit = Integer.parseInt(req.params("id"));
            String newRangerName = req.queryParams("rangerName");
            String newLocation = req.queryParams("location");
            sightingDao.update(idOfSightingToEdit, newRangerName,newLocation);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
       /* get("/categories/:category_id/tasks/:task_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("task_id"));
            taskDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());*/

        //get: show new task form


        //get: show an individual task that is nested in a category
       /* get("/categories/:category_id/tasks/:task_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("task_id")); //pull id - must match route segment
            Task foundTask = taskDao.findById(idOfTaskToFind); //use it to find task
            int idOfCategoryToFind = Integer.parseInt(req.params("category_id"));
            Category foundCategory = categoryDao.findById(idOfCategoryToFind);
            model.put("category", foundCategory);
            model.put("task", foundTask); //add it to model for template to display
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "task-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());
*/
        //get: show a form to update a task
        /*get("/animals/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            Task task = taskDao.findById(Integer.parseInt(req.params("id")));
            model.put("task", task);
            model.put("editTask", true);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());
*/
        //task: process a form to update a task
        post("/animals/:id", (request, response) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int animalToEditId = Integer.parseInt(request.params("id"));
            String newName = request.queryParams("name");
            int newAge = Integer.parseInt(request.queryParams("age"));
            Boolean newEndangered =Boolean.parseBoolean(request.queryParams("endangered"));
            String newHealth = request.queryParams("health");
            animalDao.update(animalToEditId,newName,newAge,newEndangered,newHealth);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }

    }

