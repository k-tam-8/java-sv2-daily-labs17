package day01;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;;

public class ActorRepository {

    private DataSource dataSource;

    public ActorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stat = connection.prepareStatement("insert into actors(actor_name) values(?)")) {
            stat.setString(1, name); //első wildcard karakter helyére megy a name változó
            stat.executeUpdate();
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
