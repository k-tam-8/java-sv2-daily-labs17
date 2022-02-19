package day02;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LabelsController {
    private MariaDbDataSource dSource;

    public LabelsController(MariaDbDataSource dSource) {
        this.dSource = dSource;
    }
    public void addLabelToDb(String label, int founded) {
        try (Connection conn = dSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement("insert into labels(label, founded) values (?,?)")) {
            pstm.setString(1, label);
            pstm.setLong(2, founded);
            pstm.execute();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }
}
