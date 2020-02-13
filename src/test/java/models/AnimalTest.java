package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class AnimalTest {
    @Test
    public void animal_instatiatesCorrectly_true(){
        Animal testAnimal = new Animal("Lion");
        assertEquals("Lion",testAnimal.getName());
    }
    @Test
    public void equals_returnsTrueIfNameAndEmailAreSame_true() {
        Animal firstAnimal = new Animal("Lion");
        Animal anotherAnimal = new Animal("Lion");
        assertTrue(firstAnimal.equals(anotherAnimal));
    }
    @Test
    public void save_insertObjectIntoDatabase_Animal(){
        Animal testAnimal = new Animal("Lion");
        testAnimal.save();
        assertTrue(Animal.all().get(0).equals(testAnimal));
    }
}