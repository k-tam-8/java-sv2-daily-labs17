package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        MariaDbDataSource dSource = new MariaDbDataSource();
        try {
            dSource.setUrl("jdbc:mariadb://localhost:3306/music_db?useUnicode=true");
            dSource.setUserName("root");
            dSource.setPassword("root");
        }
        catch (SQLException sqle){
            throw new IllegalStateException("Database unreachable!", sqle);
        }

        Flyway fWay = Flyway.configure().locations("db/flyway_scripts").dataSource(dSource).load();
        fWay.clean();
        fWay.migrate();
        AlbumController ac = new AlbumController(dSource);
        BandsController bc = new BandsController(dSource);
        GenresController gc = new GenresController(dSource);
        LabelsController lc = new LabelsController(dSource);

        ac.addAlbumToDb("Mastodon", "Blood Mountain", LocalDate.of(2006, 9,12),"Reprise","progressive metal");
        Album mastodonLeviathan = new Album("Mastodon", "Leviathan", LocalDate.of(2004,8,31), "Relapse", "progressive metal");
        ac.addAlbumToDb(mastodonLeviathan);

        bc.addBandToDb("Mastodon", 2000, "U.S.");
        bc.addBandToDb("Metallica", 1981, "U.S.");

        gc.addGenreToDb("thrash metal");
        gc.addGenreToDb("progressive metal");

        lc.addLabelToDb("Relapse",1990);
        lc.addLabelToDb("Megaforce",1982);


    }
}
