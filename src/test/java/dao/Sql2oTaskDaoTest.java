package dao;

import models.Animal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oAnimalDaoTest {
    private Sql2oAnimalDao animalDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        animalDao = new Sql2oAnimalDao(sql2o); //ignore me for now
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingAnimalSetsId() throws Exception {
        Animal animal = setupNewAnimal();
        int originalAnimalId = animal.getId();
        animalDao.add(animal);
        assertNotEquals(originalAnimalId, animal.getId()); //how does this work?
    }

    @Test
    public void existingAnimalsCanBeFoundById() throws Exception {
        Animal animal = setupNewAnimal();
        animalDao.add(animal); //add to dao (takes care of saving)
        Animal foundAnimal = animalDao.findById(animal.getId()); //retrieve
        assertEquals(animal, foundAnimal); //should be the same
    }

    @Test
    public void addedAnimalsAreReturnedFromgetAll() throws Exception {
        Animal animal = setupNewAnimal();
        animalDao.add(animal);
        assertEquals(1, animalDao.getAll().size());
    }

    @Test
    public void noAnimalsReturnsEmptyList() throws Exception {
        assertEquals(0, animalDao.getAll().size());
    }

    @Test
    public void updateChangesAnimalContent() throws Exception {
        String initialDescription = "mow the lawn";
        Animal animal = setupNewAnimal();
        animalDao.add(animal);

        animalDao.update(animal.getId(),"crocodile", 1,true,"ill");
        Animal updatedAnimal = animalDao.findById(animal.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedAnimal.get());
    }

    @Test
    public void deleteByIdDeletesCorrectAnimal() throws Exception {
        Animal animal = setupNewAnimal();
        animalDao.add(animal);
        animalDao.deleteById(animal.getId());
        assertEquals(0, animalDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Animal animal = setupNewAnimal();
        Animal otherAnimal = new Animal("crocodile", 1,true,"ill");
        animalDao.add(animal);
        animalDao.add(otherAnimal);
        int daoSize = animalDao.getAll().size();
        animalDao.clearAllAnimals();
        assertTrue(daoSize > 0 && daoSize > animalDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }


    //define the following once and then call it as above in your tests.
    public Animal setupNewAnimal(){
        return new Animal("lion", 1,false,"sick");
    }
}
