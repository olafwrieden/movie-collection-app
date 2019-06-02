package moviecollection;

/**
 * Attribute-based Movie Collection Search Functions.
 * 
 * @author Olaf Wrieden
 * @version 1.0
 */
public class SearchEngine {

	// Search Engine Attributes
	private MovieCollection movieCollection;

	/**
	 * Constructs a new Search Engine
	 * 
	 * @param collection the movie collection
	 */
	public SearchEngine(MovieCollection collection) {
		this.movieCollection = collection;
	}

	/**
	 * Searches by Title
	 * 
	 * @param title the title of the movie to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection byTitle(String title) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Director
	 * 
	 * @param director the name of the director to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection byDirector(String director) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getDirector().toLowerCase().contains(director.toLowerCase())) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Genre
	 * 
	 * @param genre the movie genre to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection byGenre(Genre genre) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getGenre().equals(genre)) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Release Year
	 * 
	 * @param year the release year to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection byReleasedYear(int year) {
		return this.releasedBetween(year, year);
	}

	/**
	 * Searches by Released Prior to Year (exclusive)
	 * 
	 * @param year the upper-bound release year to search for (exclusive)
	 * @return a filtered movie collection
	 */
	public MovieCollection releasedBefore(int year) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getReleaseYear() < year) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Released Between Years (inclusive)
	 * 
	 * @param startYear the lower-bound release year to search for (inclusive)
	 * @param endYear   the upper-bound release year to search for (inclusive)
	 * @return a filtered movie collection
	 */
	public MovieCollection releasedBetween(int startYear, int endYear) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if ((movie.getReleaseYear() >= startYear) && (movie.getReleaseYear() <= endYear)) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Released After Year (exclusive)
	 * 
	 * @param year the lower-bound release year to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection releasedAfter(int year) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getReleaseYear() > year) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Rating
	 * 
	 * @param rating the rating to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection byRating(Rating rating) {
		return this.ratingBetween(rating, rating);
	}

	/**
	 * Searches by Ratings Between Two Ratings (inclusive)
	 * 
	 * @param lowestRating  the lower-bound rating to search for (inclusive)
	 * @param highestRating the upper-bound rating to search for (inclusive)
	 * @return a filtered movie collection
	 */
	public MovieCollection ratingBetween(Rating lowestRating, Rating highestRating) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getRating().compareTo(lowestRating) >= 0 && movie.getRating().compareTo(highestRating) <= 0) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Ratings Below Rating
	 * 
	 * @param rating the upper-bound rating to search for (exclusive)
	 * @return a filtered movie collection
	 */
	public MovieCollection ratingBelow(Rating rating) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if ((movie.getRating().compareTo(rating)) < 0) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Ratings Above Rating
	 * 
	 * @param rating the lower-bound rating to search for (exclusive)
	 * @return a filtered movie collection
	 */
	public MovieCollection ratingAbove(Rating rating) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if ((movie.getRating().compareTo(rating)) > 0) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}

	/**
	 * Searches by Cast Members
	 * 
	 * @param memberName the name of the cast member to search for
	 * @return a filtered movie collection
	 */
	public MovieCollection byCast(String memberName) {
		MovieCollection results = new MovieCollection();

		for (Movie movie : movieCollection.getMovieCollection()) {
			if (movie.getStringCastList().toLowerCase().contains(memberName.toLowerCase())) {
				results.addToMovieCollection(movie);
			}
		}
		return results;
	}
}
