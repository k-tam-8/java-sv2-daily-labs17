package day02;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class GenresController {
    private MariaDbDataSource dSource;

    public GenresController(MariaDbDataSource dSource) {
        this.dSource = dSource;
    }

    public void addGenreToDb(String genre) {
        try (Connection conn = dSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement("insert into genres(genre) values (?)")) {
            pstm.setString(1, genre);
            pstm.execute();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }
}
