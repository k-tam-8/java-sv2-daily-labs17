package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class RatingRepository {
    private DataSource dataSource;

    public RatingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertRating(long movieId, List<Integer> ratings) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("insert into ratings (movie_id, rating) values (?,?)")) {
                for (Integer actual : ratings) {
                    if (actual < 1 || actual > 5) {
                        throw new IllegalArgumentException("invalid rating");
                    }
                    ps.setLong(1, movieId);
                    ps.setLong(2, actual);
                    ps.executeUpdate();
                }
                conn.commit();
                System.out.println(calculateAverage(movieId));
                pasteAverage(calculateAverage(movieId), movieId);
            } catch (IllegalArgumentException iae) {
                conn.rollback();
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot update ", sqle);
        }
    }

    private double calculateAverage(long movieId) {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT AVG(rating) as average FROM ratings  WHERE movie_id=?")) {
                    ps.setLong(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat(1);
                }
                return -1;
            } catch (SQLException sqle) {
                throw new IllegalStateException("Not found.");
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot get average rating", sqle);
        }
    }

    public void pasteAverage(double averageRating, long movieId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stat = connection.prepareStatement("update movies SET average = ? where id=?")) {
            stat.setFloat(1, (float) averageRating);
            stat.setLong(2, movieId);
            stat.executeUpdate();
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot paste average ", sql);
        }
    }

}
