package movies.spring.data.neo4j.services;

import java.util.*;

import movies.spring.data.neo4j.repositories.MovieRepository;
import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

	private MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Transactional
	public Movie createMovie(String title, int released) {
		Movie movie = new Movie(title, released);
		movieRepository.save(movie);
		return movie;
	}

	@Transactional(readOnly = true)
	public Movie findById(Long id) {
		Movie movie = movieRepository.findById(id).get();
//		Movie movie = movieRepository.findOne(id).get();
		return movie;
	}

	@Transactional(readOnly = true)
	public Map<String, Object>  graph(int limit) {
		Collection<Movie> result = movieRepository.graph(limit);
		return toD3Format(result);
	}

	private Map<String, Object> toD3Format(Collection<Movie> movies) {
		List<Map<String, Object>> nodes = new ArrayList<>();
		List<Map<String, Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<Movie> result = movies.iterator();
		while (result.hasNext()) {
			Movie movie = result.next();
			nodes.add(map("title", movie.getTitle(), "label", "movie"));
			int target = i;
			i++;
			for (Role role : movie.getRoles()) {
				Map<String, Object> actor = map("title", role.getPerson().getName(), "label", "actor");
				int source = nodes.indexOf(actor);
				if (source == -1) {
					nodes.add(actor);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
		}
		return map("nodes", nodes, "links", rels);
	}

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}
}