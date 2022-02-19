package day01;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ActorsMoviesService {

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;
    private ActorsMoviesRepository actorsMoviesRepository;

    public ActorsMoviesService(ActorRepository actorRepository, MovieRepository movieRepository, ActorsMoviesRepository actorsMoviesRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.actorsMoviesRepository = actorsMoviesRepository;
    }

    public void insertMovieActor(String title, LocalDate releaseTime, List<String> actorName) {
        long movieId = movieRepository.saveMovie(title, releaseTime);
        for (String actual : actorName) {
            long actorId;
            Optional<Actor> found = actorRepository.findActorByName(actual);
            if (found.isPresent()) {
                actorId = found.get().getId();
            } else {
                actorId = actorRepository.saveActor(actual);
            }
            actorsMoviesRepository.insertActorMovie(actorId,movieId);
        }
    }
}
