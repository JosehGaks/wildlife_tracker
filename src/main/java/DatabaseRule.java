import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.junit.rules.ExternalResource;


public class DatabaseRule extends ExternalResource {

    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/animals_test", null, null);  //Those with linux or windows use two strings for username and password
    }

    @Override
    protected void after() {
        try(Connection con = DB.sql2o.open()) {
            String deletePersonsQuery = "DELETE FROM animal *;";
            con.createQuery(deleteAnimalsQuery).executeUpdate();
        }
    }
}
