package moviecollection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Movie Collection Controller.
 * 
 * @author Olaf Wrieden
 * @version 1.0
 */
public class MovieCollectionApp extends JFrame {

	// Controller Attributes
	private MovieCollection model;
	private MovieCollectionView view;

	/**
	 * Handle actions when window opens (application starts)
	 */
	private void eventHandleWindowOpened() {
		System.out.println("The Movie Collection Application has launched.");
		// Read the contents of the database file into the collection
		try {
			ObjectInputStream out = new ObjectInputStream(new FileInputStream("moviecollection.bin"));
			boolean EOF = false;
			while (!EOF) {
				try {
					Movie movie = (Movie) out.readObject();
					System.out.println("IMPORTED: " + movie.toString());
					this.model.addToMovieCollection(movie);
				} catch (EOFException eof) {
					out.close();
					EOF = true;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this,
					"Your collection data file could not be loaded, due to the following error:\n" + e.getMessage(),
					"Error Loading Data", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			this.view.update(this.model.getMovieCollectionArray());
		}
	}

	/**
	 * Handle actions when window closes (application terminates)
	 */
	private void eventHandleWindowClosing() {
		System.out.println("The Movie Collection Application is terminating.");
		// Save the contents of the movie collection to a data file
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("moviecollection.bin", false));
			for (int i = 0; i < this.model.getMovieCollection().size(); i++) {
				out.writeObject(this.model.getMovieCollection().get(i));
			}
			out.close();
			System.out.println("EXPORTED: All movies in the collection were successfully saved to the data file...");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Your collection could not be saved, due to the following error:\n" + e.getMessage(),
					"Error Saving Data", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Handles adding a new movie to the collection
	 */
	private void eventHandleAddBtn() {
		// Check if all fields have data
		if (isNewMovieMetadataComplete()) {
			// Check if the new movie is already in the collection
			if (newMovieAlreadyExists(getNewMovieDetails().getTitle())) {
				int option = JOptionPane.showConfirmDialog(this,
						"A movie with this title already exists in your collection.\nWould you like to add it anyway?",
						"Duplicate Movie?", JOptionPane.YES_NO_OPTION);
				switch (option) {
				case (JOptionPane.YES_OPTION):
					// Add to the collection
					this.model.addToMovieCollection(getNewMovieDetails());
					break;
				default:
					break;
				}
			} else {
				// Add to the collection
				this.model.addToMovieCollection(getNewMovieDetails());
			}

			// Reset New Movie Attribute Fields
			clearNewMovieFields();
		} else {
			System.out.println("Movie not added due to insufficient metadata.");
			JOptionPane.showMessageDialog(this,
					"Prior to adding this movie to your collection,\nplease complete the required metadata fields.",
					"Incomplete Movie Data", JOptionPane.INFORMATION_MESSAGE);
		}
		this.view.update(this.model.getMovieCollectionArray());
	}

	/**
	 * Handles removing a movie from the collection
	 */
	private void eventHandleRemoveBtn() {
		int index = this.view.getMovieCollectionTable().getSelectedRow();
		if (index != -1) {
			System.out.println("DELETED: " + this.model.getMovieCollection().get(index).toString());
			this.model.removeFromMovieCollection(index);
		}
		this.view.update(this.model.getMovieCollectionArray());
	}

	/**
	 * Handles active movie selection in the collection table
	 */
	private void eventHandleTableSelection() {
		// Enable/Disable remove button depending on active selection
		this.view.getRemoveBtn().setEnabled(this.view.getMovieCollectionTable().getSelectedRow() != -1);
	}

	/**
	 * Handles executing the advanced search
	 */
	private void eventHandleAdvancedSearchBtn() {
		advancedSearch();
	}

	/**
	 * Handles advanced search clear button
	 */
	private void eventHandleAdvancedSearchClearBtn() {
		this.view.update(this.model.getMovieCollectionArray());
		this.view.getAdvancedSearchBtn().setEnabled(true);
		this.view.getAdvancedSearchClearBtn().setEnabled(false);
	}

	/**
	 * Handles advanced search rating selection
	 */
	private void eventHandleSearchRatingSelection() {
		switch (this.view.getSearchRatingComBx().getSelectedItem().toString().toLowerCase()) {
		case ("of"): // Falls through
		case ("above"): // Falls through
		case ("below"):
			this.view.getSearchRatingMaxComBx().setEnabled(false);
			break;
		case ("between"):
			this.view.getSearchRatingMaxComBx().setEnabled(true);
			break;
		}
	}

	/**
	 * Checks if the new movie metadata is complete
	 * 
	 * @return true if complete, else false
	 */
	public boolean isNewMovieMetadataComplete() {
		if (!this.view.getNewMovieTitle().getText().trim().isEmpty()) {
			if (!this.view.getNewMovieDirector().getText().trim().isEmpty()) {
				if (!this.view.getNewMovieCastList().getText().trim().isEmpty()) {
					if (!(this.view.getNewMovieGenre().getSelectedItem().toString() == "")) {
						if (!(this.view.getNewMovieRating().getSelectedItem().toString() == "")) {
							if (!(this.view.getNewMovieReleaseYear().getValue().toString() == "")) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Gathers new movie's metadata to construct and return a new movie
	 * 
	 * @return a new movie based on the metadata
	 */
	private Movie getNewMovieDetails() {
		return new Movie(this.view.getNewMovieTitle().getText().trim(),
				this.view.getNewMovieDirector().getText().trim(),
				Genre.valueOf(this.view.getNewMovieGenre().getSelectedItem().toString()),
				(Integer) this.view.getNewMovieReleaseYear().getValue(),
				Rating.valueOf(this.view.getNewMovieRating().getSelectedItem().toString()),
				new String[] { this.view.getNewMovieCastList().getText().trim() });
	}

	/**
	 * Resets the new movie attribute fields
	 */
	private void clearNewMovieFields() {
		this.view.getNewMovieTitle().setText("");
		this.view.getNewMovieDirector().setText("");
		this.view.getNewMovieCastList().setText("");
		this.view.getNewMovieGenre().setSelectedIndex(0);
		this.view.getNewMovieRating().setSelectedIndex(0);
		this.view.getNewMovieReleaseYear().setValue(2017); // TODO: Set to current year
	}

	/**
	 * Checks if a movie with this title already exists in the collection
	 * 
	 * @param title the title of the movie to be added
	 * @return true if movie with this title already exists, else false
	 */
	private boolean newMovieAlreadyExists(String title) {
		// Search collection to find potential duplicate
		for (Movie movie : this.model.getMovieCollection()) {
			if (movie.getTitle().toLowerCase().equals(title.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Constructs the Movie Collection Application.
	 * 
	 * @param name the title of the application
	 */
	public MovieCollectionApp(String name) {
		super(name);
		this.model = new MovieCollection();
		this.view = new MovieCollectionView();
		this.getContentPane().add(this.view);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setResizable(false);

		// Sample Data
//		this.model.addToMovieCollection(new Movie("Titanic", "James Cameron", Genre.DRAMA, 1997, Rating.ONE,
//				new String[] { "Leonardo DiCaprio", "Kate Winslet", "Billy Zane" }));
//		this.model.addToMovieCollection(new Movie("Avatar", "James Cameron", Genre.FANTASY, 2009, Rating.TWO,
//				new String[] { "Sam Worthington", "Zoe Saldana", "Sigourney Weaver" }));
//		this.model.addToMovieCollection(new Movie("Life of Pi", "Ang Lee", Genre.ADVENTURE, 2012, Rating.THREE,
//				new String[] { "Suraj Sharma", "Irrfan Khan", "Adil Hussain" }));
//		this.model.addToMovieCollection(new Movie("Slumdog Millionaire", "Danny Boyle", Genre.DRAMA, 2008, Rating.FOUR,
//				new String[] { "Dev Patel", "Freida Pinto", "Saurabh Shukla" }));
//		this.model.addToMovieCollection(new Movie("The King's Speech", "Tom Hooper", Genre.DRAMA, 2010, Rating.FIVE,
//				new String[] { "Colin Firth", "Geoffrey Rush", "Helena Bonham Carter" }));

		// Window Action Listeners
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				eventHandleWindowOpened();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				eventHandleWindowClosing();
			}
		});

		this.view.getAddBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleAddBtn();
			}
		});

		this.view.getRemoveBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleRemoveBtn();
			}
		});

		this.view.getMovieCollectionTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				eventHandleTableSelection();
			}
		});

		this.view.getQuickFilterTerm().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String query = view.getQuickFilterTerm().getText().toLowerCase();
				String type = view.getQuickFilterComboBox().getSelectedItem().toString();
				filter(type, query);
			}
		});

		this.view.getAdvancedSearchBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleAdvancedSearchBtn();
			}
		});

		this.view.getAdvancedSearchClearBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleAdvancedSearchClearBtn();
			}
		});

		this.view.getSearchRatingComBx().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleSearchRatingSelection();
			}
		});
	}

	/**
	 * Displays filtered contents of the movie collection
	 */
	private void advancedSearch() {

		MovieCollection results = null;

		// Cast or Director
		if (this.view.getSearchDirCastChBx().isSelected()) {
			switch (this.view.getSearchDirCastComBx().getSelectedItem().toString().toLowerCase()) {
			case ("directed by"):
				results = this.model.getSearchEngine()
						.byDirector(this.view.getSearchDirCastTerm().getText().trim().toLowerCase());
				break;
			case ("casting"):
				results = this.model.getSearchEngine()
						.byCast(this.view.getSearchDirCastTerm().getText().trim().toLowerCase());
				break;
			}
		}

		// Genre
		if (this.view.getSearchGenreChBx().isSelected()) {
			if (this.view.getSearchDirCastChBx().isSelected()) {
				results = results.getSearchEngine()
						.byGenre(Genre.valueOf(this.view.getSearchGenreItem().getSelectedItem().toString()));
			} else {
				results = this.model.getSearchEngine()
						.byGenre(Genre.valueOf(this.view.getSearchGenreItem().getSelectedItem().toString()));
			}
		}

		// Rating
		if (this.view.getSearchRatingChBx().isSelected()) {
			if (this.view.getSearchDirCastChBx().isSelected() || this.view.getSearchDirCastChBx().isSelected()) {
				switch (this.view.getSearchRatingComBx().getSelectedItem().toString().toLowerCase()) {
				case ("of"):
					results = results.getSearchEngine()
							.byRating(Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()));
					break;
				case ("above"):
					results = results.getSearchEngine().ratingAbove(
							Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()));
					break;
				case ("below"):
					results = results.getSearchEngine().ratingBelow(
							Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()));
					break;
				case ("between"):
					results = results.getSearchEngine().ratingBetween(
							Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()),
							Rating.valueOf(this.view.getSearchRatingMaxComBx().getSelectedItem().toString()));
					break;
				}
			} else {
				switch (this.view.getSearchRatingComBx().getSelectedItem().toString().toLowerCase()) {
				case ("of"):
					results = this.model.getSearchEngine()
							.byRating(Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()));
					break;
				case ("above"):
					results = this.model.getSearchEngine().ratingAbove(
							Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()));
					break;
				case ("below"):
					results = this.model.getSearchEngine().ratingBelow(
							Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()));
					break;
				case ("between"):
					results = this.model.getSearchEngine().ratingBetween(
							Rating.valueOf(this.view.getSearchRatingMinComBx().getSelectedItem().toString()),
							Rating.valueOf(this.view.getSearchRatingMaxComBx().getSelectedItem().toString()));
					break;
				}
			}
		}

		// If parameters matched, show movies
		if (results != null && results.hasMovieAlready()) {
			this.view.update(results.getMovieCollectionArray());
			this.view.getAdvancedSearchBtn().setEnabled(false);
			this.view.getAdvancedSearchClearBtn().setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(this,
					"There are no movies matching these criteria.\nTry adjusting your search parameters.",
					"No Match Found", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Acts on the quick search parameters entered by the user
	 * 
	 * @param type  the selected combo box item
	 * @param query the text field's content
	 */
	private void filter(String type, String query) {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(this.view.getTableModel());
		this.view.getMovieCollectionTable().setRowSorter(sorter);

		// Evaluates parameters to act on appropriate column
		switch (type.toLowerCase()) {
		case ("title"):
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0));
			break;
		case ("director"):
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 1));
			break;
		case ("genre"):
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 2));
			break;
		case ("year"):
			if (query.contains("-")) {
				int[] dateRange = dateRange(query);
				if ((dateRange != null)) {
					this.view.update(this.model.getSearchEngine().releasedBetween(dateRange[0], dateRange[1])
							.getMovieCollectionArray());
				}
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 3));
			}
			break;
		case ("rating"):
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 4));
			break;
		case ("cast"):
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 5));
			break;
		}
	}

	/**
	 * Retrieves the numeric values of the input string and returns them as an array
	 * 
	 * @param query The input string to retrieve dates from
	 * @return an array containing 1: Start 2: End date to search between
	 */
	private int[] dateRange(String query) {
		int[] dates = new int[2];

		String re1 = "((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])"; // Year 1
		String re2 = ".*?"; // Non-greedy match on filler
		String re3 = "(-)"; // Any Single Character 1
		String re4 = ".*?"; // Non-greedy match on filler
		String re5 = "((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])"; // Year 2

		Pattern pattern = Pattern.compile(re1 + re2 + re3 + re4 + re5, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher mmatcher = pattern.matcher(query);
		if (mmatcher.find()) {
			dates[0] = Integer.parseInt(mmatcher.group(1));
			dates[1] = Integer.parseInt(mmatcher.group(3));
		}
		return dates;
	}

	/**
	 * Application entry point.
	 * 
	 * @param args Optional startup arguments
	 * @see Icon made by Freepik from www.flaticon.com
	 */
	public static void main(String[] args) {
		JFrame frame = new MovieCollectionApp("My Movie Collection");
		ImageIcon img = new ImageIcon("icon.png");
		frame.setIconImage(img.getImage());
		frame.setVisible(true);
		frame.setSize(720, 455);
	}
}
