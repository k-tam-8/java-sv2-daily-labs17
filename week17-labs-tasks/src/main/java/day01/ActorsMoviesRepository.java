package day01;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActorsMoviesRepository {
    private DataSource dataSource;

    public ActorsMoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void insertActorMovie(long actor_id, long movie_id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stat = connection.prepareStatement("insert into actors_movies(actor_id, movie_id) values(?,?)")) {
            stat.setLong(1, actor_id);
            stat.setLong(2,movie_id);
            stat.executeUpdate();
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot update ", sql);
        }
    }

}
