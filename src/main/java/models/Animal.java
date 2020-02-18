package models;

import org.sql2o.*;

import java.util.List;
import java.util.Objects;

public class Animal {
    private String name;
    private String health;
    private int age;
    private int id;
    private boolean endangered;

    public Animal(String name,int age,boolean endangered,String health)
    {
        this.name = name;
        this.age = age;
        this.endangered= endangered;
        this.health  =health;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEndangered() {
        return endangered;
    }

    public void setEndangered(boolean endangered) {
        this.endangered = endangered;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                id == animal.id &&
                endangered == animal.endangered &&
                name.equals(animal.name) &&
                health.equals(animal.health);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, health, age, id, endangered);
    }
}
