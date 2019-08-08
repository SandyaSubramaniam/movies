package co.grandcircus.movies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.movies.dao.MovieDao;
import co.grandcircus.movies.entity.Movie;

@RestController
public class MoviesApiController {

	@Autowired
	private MovieDao dao;

	// Get a list of all movies
	// Get a list of all movies in a specific category as a query parameter
	// Get a list of all movies in a specific title as a query parameter
	@GetMapping("/movies")
	public List<Movie> listMovies(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "title", required = false) String title) {

		if (category != null) {
			return dao.findByCategoryContainsIgnoreCase(category);
		} else if (title != null) {
			return dao.findByTitleContainsIgnoreCase(title);
		} else {
			return dao.findAll();
		}
	}

	// Get info about a specific movie when user uses id as a query parameter
	@GetMapping("/movies/{id}")
	public Movie getMovie(@PathVariable("id") Integer id) {

		return dao.findById(id).get();
	}

	// Get a list of all movie categories
	@GetMapping("/categories")
	public Set<String> listCategories() {

		return dao.findAllCategories();
	}

	// Get a random movie pick
	// Get a random movie pick from a category
	@GetMapping("/movies/random")
	public Movie getRandomMovie(@RequestParam(value = "category", required = false) String category) {

		Movie randMovie = new Movie();
		if (category == null || category.isEmpty()) {
			Integer id = (int) (Math.random() * 20 + 1);
			randMovie = dao.findById(id).get();
		} else {
			List<Movie> movieList = dao.findByCategoryContainsIgnoreCase(category);
			Integer moviesCount = movieList.size();
			Integer id = (int) (Math.random() * moviesCount + 1);
			randMovie = movieList.get(id);
		}
		return randMovie;
	}
	// Get a list of random movie picks when user specifies quantity as a query
	// parameter

	@GetMapping("/movies/random-list")
	public List<Movie> getQuantity(@RequestParam(value = "quantity", required = false) Integer quantity) {

		List<Movie> movieList = dao.findAll();
		List<Movie> randMovies = new ArrayList<Movie>();
		Collections.shuffle(movieList);
		for (int i = 0; i < quantity; i++) {
			randMovies.add(movieList.get(i));
		}
		return randMovies;
	}
}