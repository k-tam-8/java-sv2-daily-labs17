package day02;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;
import java.time.LocalDate;

public class AlbumController {

    private MariaDbDataSource dSource;

    public AlbumController(MariaDbDataSource dSource) {
        this.dSource = dSource;
    }

    public void addAlbumToDb(String band, String title, LocalDate releaseDate, String label, String genre) {
        try (Connection conn = dSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement("insert into albums(band, title, release_date, label, genre) values (?,?,?,?,?)")) {
            pstm.setString(1, band);
            pstm.setString(2, title);
            pstm.setDate(3, Date.valueOf(releaseDate));
            pstm.setString(4, label);
            pstm.setString(5, genre);
            pstm.execute();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }

    public void addAlbumToDb(Album album) {
        try (Connection conn = dSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement("insert into albums(band, title, release_date, label, genre) values (?,?,?,?,?)")) {
            pstm.setString(1, album.getBand());
            pstm.setString(2, album.getTitle());
            pstm.setDate(3, Date.valueOf(album.getReleaseDate()));
            pstm.setString(4, album.getLabel());
            pstm.setString(5, album.getGenre());
            pstm.execute();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }
}
