import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class AnimalTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();
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
    @Test
    public void all_returnsAllInstancesOfAnimal_true() {
        Animal firstAnimal = new Animal("lion");
        firstAnimal.save();
        Animal secondAnimal = new Animal("monkey");
        secondAnimal.save();
        assertEquals(true, Animal.all().get(0).equals(firstAnimal));
        assertEquals(true, Animal.all().get(1).equals(secondAnimal));
    }
    @Test
    public void save_assignsIdToObject() {
        Animal testAnimal = new Animal("lion");
        testAnimal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(testAnimal.getId(), savedAnimal.getId());
    }
    @Test
    public void save_assignsIdToObject() {
        Animal testAnimal = new Animal("lion");
        testAnimal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(testAnimal.getId(), savedAnimal.getId());
    }
    @Test
    public void find_returnsAnimalWithSameId_secondAnimal() {
        Animal firstAnimal = new Animal("lion");
        firstAnimal.save();
        Animal secondAnimal = new Animal("monkey
        secondAnimal.save();
        assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
    }
}