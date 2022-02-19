package day02;

import java.time.LocalDate;

public class Album {
    private int id;
    private String band;
    private String title;
    private LocalDate releaseDate;
    private String label;
    private String genre;

    public Album(String band, String title, LocalDate releaseDate, String label, String genre) {
        this.band = band;
        this.title = title;
        this.releaseDate = releaseDate;
        this.label = label;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getBand() {
        return band;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getLabel() {
        return label;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", band='" + band + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", label='" + label + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
