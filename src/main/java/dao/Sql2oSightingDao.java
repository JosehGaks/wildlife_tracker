package dao;

import models.Animal;
import models.Sightings;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSightingDao implements SightingsDao{

    private final Sql2o sql2o;

    public Sql2oSightingDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Sightings sightings) {
        String sql = "INSERT INTO sightings (rangerName,location) VALUES (:rangerName,:location)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(sightings)
                    .executeUpdate()
                    .getKey();
            sightings.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Sightings> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sightings")
                    .executeAndFetch(Sightings.class);
        }
    }

    @Override
    public Sightings findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sightings WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sightings.class);
        }
    }

    @Override
    public void update(int id, String newRangerName,String newLocation){
        String sql = "UPDATE sightings SET name = :name WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("rangerName", newRangerName)
                    .addParameter("location",newLocation)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from sightings WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllSightings() {
        String sql = "DELETE from sightings"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

   /* @Override
    public List<Animal> getAllTasksByCategory(int categoryId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals WHERE sightingsId = :categoryId")
                    .addParameter("categoryId", categoryId)
                    .executeAndFetch(Animal.class);
        }
    }*/
}
