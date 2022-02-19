package day02;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class BandsController {

    private MariaDbDataSource dSource;

    public BandsController(MariaDbDataSource dSource) {
        this.dSource = dSource;
    }

    public void addBandToDb(String bandTitle, int founded, String origin) {
        try (Connection conn = dSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement("insert into bands(band_name, founded, origin) values (?,?,?)")) {
            pstm.setString(1, bandTitle);
            pstm.setLong(2, founded);
            pstm.setString(3, origin);
            pstm.execute();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }
}
