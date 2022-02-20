package day01;

import java.util.Arrays;
import java.util.Optional;

public class MovieRatingService {
    private MovieRepository mr;
    private RatingRepository rr;

    public MovieRatingService(MovieRepository mr, RatingRepository rr) {
        this.mr = mr;
        this.rr = rr;
    }

    public void addRating(String title, Integer... ratings) {
        Optional<Movie> actual = mr.findMovieById(title);
        if (actual.isPresent()) {
            rr.insertRating(actual.get().getId(), Arrays.asList(ratings));
        } else {
            throw new IllegalArgumentException("Cannot find movie");
        }
    }
}
