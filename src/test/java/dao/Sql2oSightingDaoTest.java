package dao;

import models.Sightings;
import models.Animal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oSightingDaoTest {
    private Sql2oSightingDao sightingDao;
    private Sql2oAnimalDao animalDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sightingDao = new Sql2oSightingDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingSightingsSetsId() throws Exception {
        Sightings sighting = setupNewSightings();
        int originalSightingsId = sighting.getId();
        sightingDao.add(sighting);
        assertNotEquals(originalSightingsId, sighting.getId());
    }

    @Test
    public void existingCategoriesCanBeFoundById() throws Exception {
        Sightings sighting = setupNewSightings();
        sightingDao.add(sighting);
        Sightings foundSightings = sightingDao.findById(sighting.getId());
        assertEquals(sighting, foundSightings);
    }

    @Test
    public void addedCategoriesAreReturnedFromGetAll() throws Exception {
        Sightings sighting = setupNewSightings();
        sightingDao.add(sighting);
        assertEquals(1, sightingDao.getAll().size());
    }

    @Test
    public void noCategoriesReturnsEmptyList() throws Exception {
        assertEquals(0, sightingDao.getAll().size());
    }

    @Test
    public void updateChangesSightingsContent() throws Exception {
        String initialRangerNmae = "Yardwork";
        Sightings sighting = new Sightings (initialRangerNmae,"west");
        sightingDao.add(sighting);
        sightingDao.update(sighting.getId(),"Joe","west");
        Sightings updatedSightings = sightingDao.findById(sighting.getId());
        assertNotEquals(initialRangerNmae, updatedSightings.getRangerName());
    }

    @Test
    public void deleteByIdDeletesCorrectSightings() throws Exception {
        Sightings sighting = setupNewSightings();
        sightingDao.add(sighting);
        sightingDao.deleteById(sighting.getId());
        assertEquals(0, sightingDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllCategories() throws Exception {
        Sightings sighting = setupNewSightings();
        Sightings otherSightings = new Sightings("joe","arizona");
        sightingDao.add(sighting);
        sightingDao.add(otherSightings);
        int daoSize = sightingDao.getAll().size();
        sightingDao.clearAllSightings();
        assertTrue(daoSize > 0 && daoSize > sightingDao.getAll().size());
    }


    // helper method
    public Sightings setupNewSightings(){
        return new Sightings("Yardwork","newyork");
    }
}