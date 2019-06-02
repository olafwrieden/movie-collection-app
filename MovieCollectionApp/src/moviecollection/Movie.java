package moviecollection;

import java.io.Serializable;

/**
 * Information about an individual Movie.
 * 
 * @author Olaf Wrieden
 * @version 1.0
 */
public class Movie implements Serializable {

	// Movie Attributes
	private String title;
	private String director;
	private Genre genre;
	private int releaseYear;
	private Rating rating;
	private String[] castList;

	/**
	 * Constructs a Movie object
	 * 
	 * @param title       the name of the movie
	 * @param director    the movie's director
	 * @param genre       the genre of the movie
	 * @param releaseYear the movie's year of release
	 * @param rating      the movie's rating
	 * @param castList    the cast in this movie
	 */
	public Movie(String title, String director, Genre genre, int releaseYear, Rating rating, String[] castList) {
		this.setTitle(title);
		this.setDirector(director);
		this.setGenre(genre);
		this.setReleaseYear(releaseYear);
		this.setRating(rating);
		this.setCastList(castList);
	}

	/**
	 * @return the movie's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the movie's title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the movie's director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @param director the movie's director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return the movie's genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @param genre the movie's genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * @return the movie's releaseYear
	 */
	public int getReleaseYear() {
		return releaseYear;
	}

	/**
	 * @param releaseYear the movie's releaseYear to set
	 */
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	/**
	 * @return the movie's rating
	 */
	public Rating getRating() {
		return rating;
	}

	/**
	 * @param rating the movie's rating to set
	 */
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	/**
	 * @return the movie's castList
	 */
	public String[] getCastList() {
		return castList;
	}

	/**
	 * @param castList the movie's castList to set
	 */
	public void setCastList(String[] castList) {
		this.castList = castList;
	}

	/**
	 * @return a string representation of the movie's cast list
	 */
	public String getStringCastList() {
		String list = "";
		for (int i = 0; i < castList.length; i++) {
			list += castList[i];
			if (i < castList.length - 1) {
				list += ", ";
			}
		}
		return list;
	}

	/**
	 * @return a string representation of the movie's details
	 */
	@Override
	public String toString() {
		return "Title: " + this.getTitle() + " - Director: " + this.getDirector() + " - Genre: " + this.getGenre()
				+ " - Year: " + this.getReleaseYear() + " - Rating: " + this.getRating() + " - Cast: "
				+ this.getStringCastList();
	}
}
