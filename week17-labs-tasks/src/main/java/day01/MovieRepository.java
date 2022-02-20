package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository {
    private DataSource dataSource;

    public MovieRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveMovie(String title, LocalDate releaseDate) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stat = connection.prepareStatement("insert into movies(title, release_date) values(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, title);
            stat.setDate(2, Date.valueOf(releaseDate));
            stat.executeUpdate();

            try (ResultSet rs = stat.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("Insert failed");
            }
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

    public Optional<Movie> findMovieById(String title) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement("select * from movies where title =?")) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(rs.getLong("id"), rs.getString("title"), rs.getDate("release_date").toLocalDate()));
                }
                return Optional.empty();
            } catch (SQLException sqle) {
                throw new IllegalStateException("Not found.");
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Not found.");
        }
    }
}
