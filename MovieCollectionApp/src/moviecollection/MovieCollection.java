package moviecollection;

import java.util.ArrayList;

/**
 * Information about a Movie Collection (Model).
 * 
 * @author Olaf Wrieden
 * @version 1.0
 */
public class MovieCollection {

	// Collection Attributes
	private ArrayList<Movie> movieCollection;
	private SearchEngine searchEngine;

	/**
	 * Construct a Default Movie Collection
	 */
	public MovieCollection() {
		this(new ArrayList<Movie>());
		this.movieCollection = new ArrayList<Movie>();
	}

	/**
	 * Construct a Movie Collection
	 * 
	 * @param movieCollection the movie collection to construct from
	 */
	public MovieCollection(ArrayList<Movie> movieCollection) {
		this.setMovieCollection(movieCollection);
		this.searchEngine = new SearchEngine(this);
	}

	/**
	 * Adds a new movie to the collection
	 * 
	 * @param movie the movie to add into the collection
	 */
	public void addToMovieCollection(Movie movie) {
		this.movieCollection.add(movie);
	}

	/**
	 * Removes the movie at the specified index
	 * 
	 * @param index the index of the movie to be removed
	 */
	public void removeFromMovieCollection(int index) {
		this.movieCollection.remove(index);
	}

	/**
	 * Returns the movie collection as an array
	 * 
	 * @return an array of the movie collection
	 */
	public Movie[] getMovieCollectionArray() {
		Movie[] array = new Movie[this.movieCollection.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = this.movieCollection.get(i);
		}
		return array;
	}

	/**
	 * @return the array list of all movies in the collection
	 */
	public ArrayList<Movie> getMovieCollection() {
		return movieCollection;
	}

	/**
	 * @return the search engine object
	 */
	public SearchEngine getSearchEngine() {
		return searchEngine;
	}

	/**
	 * Sets the movie collection to the input collection
	 * 
	 * @param movieCollection a movie collection
	 */
	public void setMovieCollection(ArrayList<Movie> movieCollection) {
		this.movieCollection = movieCollection;
		this.searchEngine = new SearchEngine(this);
	}

	/**
	 * Checks if the movie already exists in the collection
	 * 
	 * @return true if movie already exists in the collection, else false
	 */
	public boolean hasMovieAlready() {
		return this.movieCollection.size() > 0;
	}
}
