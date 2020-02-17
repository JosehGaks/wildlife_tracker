package models;

import java.util.Objects;

public class Sightings {
    private String rangerName;
    private int id;
    private String location;

    public Sightings(String rangerName, String location) {
        this.rangerName = rangerName;
        this.location = location;
    }

    public String getRangerName() {
        return rangerName;
    }

    public void setRangerName(String rangerName) {
        this.rangerName = rangerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sightings sightings = (Sightings) o;
        return id == sightings.id &&
                Objects.equals(rangerName, sightings.rangerName) &&
                Objects.equals(location, sightings.location);
    }
    @Override
    public int hashCode() {
        return Objects.hash(rangerName, id, location);
    }
}
