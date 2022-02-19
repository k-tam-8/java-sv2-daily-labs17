package day01;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class ActorRepository {

    private DataSource dataSource;

    public ActorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stat = connection.prepareStatement("insert into actors(actor_name) values(?)", Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, name);
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

    public List<String> findActorsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT actor_name FROM actors WHERE actor_name LIKE ?")) {
            ps.setString(1, prefix + "%");
            result = fillList(ps);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return result;
    }

    public Optional<Actor> findActorByName(String actorName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM actors WHERE actor_name=?")) {
            ps.setString(1, actorName);
            return processSelectStatement(ps);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot connect fo select by name!");
        }
    }

    private Optional<Actor> processSelectStatement(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Actor(rs.getLong("id"), rs.getString("actor_name")));
            }
            return Optional.empty();
        }
    }

    private List<String> fillList(PreparedStatement ps) {
        List<String> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String actorName = rs.getString("actor_name");
                result.add(actorName);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return result;
    }
}
