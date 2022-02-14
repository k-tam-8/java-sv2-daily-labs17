package day01;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors?useUnicode=true");
        dataSource.setUserName("root");
        dataSource.setPassword("root");

        try (Connection connection = dataSource.getConnection()) {
            Statement stat = connection.createStatement();
            //stat.executeUpdate("insert into actors(actor_name) values ('John Doe')");
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot connect", sql);
        }

        ActorRepository ar = new ActorRepository(dataSource);
        //ar.saveActor("Jack Doe");
        System.out.println(ar.findActorsWithPrefix("Jo"));
    }
}
