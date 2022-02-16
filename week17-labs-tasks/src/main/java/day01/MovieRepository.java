package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private DataSource dataSource;

    public MovieRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveMovie(String title, LocalDate releaseDate) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stat = connection.prepareStatement("insert into movies(title, release_date) values(?,?)")) {
            stat.setString(1, title);
            stat.setDate(2, Date.valueOf(releaseDate));
            stat.executeUpdate();
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot update ", sql);
        }
    }

    public List<Movie> findAllMovies() {
        List<Movie> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies")) {
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Movie newMovie = new Movie(rs.getLong("id"), rs.getString("title"), rs.getDate("release_date").toLocalDate());
                    result.add(newMovie);
                }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
