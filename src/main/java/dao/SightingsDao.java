package dao;

import models.Animal;
import models.Sightings;

import java.util.List;

public interface SightingsDao {
    //LIST
    List<Sightings> getAll();

    //CREATE
    void add (Sightings sightings);

    //READ
    Sightings findById(int id);
    //List<Animal> getAllTasksByCategory(int sightingId);

    //UPDATE
    void update(int id, String newRangerName,String newLocation);

    //DELETE
    void deleteById(int id);
    void clearAllSightings();
}
