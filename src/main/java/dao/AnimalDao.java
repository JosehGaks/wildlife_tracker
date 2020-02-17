package dao;

import java.util.List;
import models.*;

public interface AnimalDao {
    List<Animal> getAll();

    //CREATE
    void add (Animal animal);

    //READ
    Animal findById(int id);


    //UPDATE
    void update(int id,String newName,int newAge,boolean newEndangered,String newHealth);

    //DELETE
    void deleteById(int id);
    void clearAllAnimals();
}
