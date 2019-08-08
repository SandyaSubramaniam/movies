package co.grandcircus.movies.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.grandcircus.movies.entity.Movie;

public interface MovieDao extends JpaRepository<Movie, Integer> {

	List<Movie> findByCategoryContainsIgnoreCase(String categoryMatch);

	@Query("SELECT DISTINCT category FROM Movie")
	Set<String> findAllCategories();

	List<Movie> findByTitleContainsIgnoreCase(String titleMatch);
}
