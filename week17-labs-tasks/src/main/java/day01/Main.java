package day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors?useUnicode=true");
        dataSource.setUserName("root");
        dataSource.setPassword("root");

/*        try (Connection connection = dataSource.getConnection()) {
            Statement stat = connection.createStatement();
            //stat.executeUpdate("insert into actors(actor_name) values ('John Doe')");
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot connect", sql);
        }*/

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        //flyway.baseline();
        flyway.migrate();

        ActorRepository ar = new ActorRepository(dataSource);
        MovieRepository mr = new MovieRepository(dataSource);
        ActorsMoviesRepository amr = new ActorsMoviesRepository(dataSource);
        RatingRepository rr = new RatingRepository(dataSource);
        MovieRatingService mrs = new MovieRatingService(mr,rr);
        ActorsMoviesService ams = new ActorsMoviesService(ar, mr, amr);

        ams.insertMovieActor("Titanic", LocalDate.of(1997,11,13), List.of("Leonardo DiCaprio", "Kate Winslet"));
        ams.insertMovieActor("Jaws", LocalDate.of(1975,4,28), List.of("Roy Schneider", "Kate Winslet"));
        mrs.addRating("Titanic", 4,1,3);
        mrs.addRating("Jaws", 5,4,2);
    }
}
