package day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.baseline();
        flyway.migrate();

        ActorRepository ar = new ActorRepository(dataSource);
        MovieRepository mr = new MovieRepository(dataSource);
        mr.saveMovie("Titanic", LocalDate.of(1999, 05, 12));
        System.out.println(mr.findAllMovies());
    }
}
