package moviecollection;

import java.awt.Font;
import java.awt.Image;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 * View Controller for the Movie Collection.
 * 
 * NOTE: Alignments were made on a computer running macOS, alignment mismatches
 * might appear in Windows
 * 
 * @author Olaf Wrieden
 * @version 1.0
 */
public class MovieCollectionView extends JPanel {

	// Table Components
	private JTable movieCollectionTable;
	private DefaultTableModel tableModel;

	// Buttons
	private JButton addBtn;
	private JButton removeBtn;
	private JButton advancedSearchBtn;
	private JButton advancedSearchClearBtn;

	// New Movie Components
	private JTextField newMovieTitle;
	private JSpinner newMovieReleaseYear;
	private JComboBox<Rating> newMovieRating;
	private JComboBox<Genre> newMovieGenre;
	private JTextField newMovieDirector;
	private JTextField newMovieCastList;

	// Quick Filter Collection Components
	private JComboBox<String> quickFilterComboBox;
	private JTextField quickFilterTerm;

	// Advanced Search Components
	private JCheckBox searchDirCastChBx;
	private JComboBox<String> searchDirCastComBx;
	private JTextField searchDirCastTerm;

	private JCheckBox searchGenreChBx;
	private JComboBox<Genre> searchGenreItem;

	private JCheckBox searchRatingChBx;
	private JComboBox<String> searchRatingComBx;
	private JComboBox<Rating> searchRatingMinComBx;
	private JComboBox<Rating> searchRatingMaxComBx;

	// Cast / Director
	/**
	 * @return the check box to activate cast / director filter
	 */
	public JCheckBox getSearchDirCastChBx() {
		return searchDirCastChBx;
	}

	/**
	 * @return the combo box to choose cast / director filter
	 */
	public JComboBox<String> getSearchDirCastComBx() {
		return searchDirCastComBx;
	}

	/**
	 * @return the search term for cast / director
	 */
	public JTextField getSearchDirCastTerm() {
		return searchDirCastTerm;
	}

	// Genre
	/**
	 * @return the check box to activate genre filter
	 */
	public JCheckBox getSearchGenreChBx() {
		return searchGenreChBx;
	}

	/**
	 * @return the combo box to choose genre to filter
	 */
	public JComboBox<Genre> getSearchGenreItem() {
		return searchGenreItem;
	}

	// Rating
	/**
	 * @return the check box to activate rating filter
	 */
	public JCheckBox getSearchRatingChBx() {
		return searchRatingChBx;
	}

	/**
	 * @return the combo box to filter rating type
	 */
	public JComboBox<String> getSearchRatingComBx() {
		return searchRatingComBx;
	}

	/**
	 * @return the minimum rating to filter
	 */
	public JComboBox<Rating> getSearchRatingMinComBx() {
		return searchRatingMinComBx;
	}

	/**
	 * @return the maximum rating to filter
	 */
	public JComboBox<Rating> getSearchRatingMaxComBx() {
		return searchRatingMaxComBx;
	}

	// Movie Collection Table
	/**
	 * @return the table in which to show the collection
	 */
	public JTable getMovieCollectionTable() {
		return movieCollectionTable;
	}

	/**
	 * @return the table's default model
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	// Buttons
	/**
	 * @return the add-to-collection button
	 */
	public JButton getAddBtn() {
		return addBtn;
	}

	/**
	 * @return the remove-from-collection button
	 */
	public JButton getRemoveBtn() {
		return removeBtn;
	}

	/**
	 * @return the advanced-search button
	 */
	public JButton getAdvancedSearchBtn() {
		return advancedSearchBtn;
	}

	/**
	 * @return the clear-advanced-search-results button
	 */
	public JButton getAdvancedSearchClearBtn() {
		return advancedSearchClearBtn;
	}

	// New Movie Attribute Fields
	/**
	 * @return the movie title text field
	 */
	public JTextField getNewMovieTitle() {
		return newMovieTitle;
	}

	/**
	 * @return the movie release year spinner
	 */
	public JSpinner getNewMovieReleaseYear() {
		return newMovieReleaseYear;
	}

	/**
	 * @return the movie rating combo box
	 */
	public JComboBox<Rating> getNewMovieRating() {
		return newMovieRating;
	}

	/**
	 * @return the movie genre combo box
	 */
	public JComboBox<Genre> getNewMovieGenre() {
		return newMovieGenre;
	}

	/**
	 * @return the movie director text field
	 */
	public JTextField getNewMovieDirector() {
		return newMovieDirector;
	}

	/**
	 * @return the movie cast list text field
	 */
	public JTextField getNewMovieCastList() {
		return newMovieCastList;
	}

	// Quick Filter
	/**
	 * @return the quick-filter term field
	 */
	public JTextField getQuickFilterTerm() {
		return quickFilterTerm;
	}

	/**
	 * @return the quick-filter type combo box
	 */
	public JComboBox<String> getQuickFilterComboBox() {
		return quickFilterComboBox;
	}

	/**
	 * Constructs a Movie Collection View.
	 */
	public MovieCollectionView() {
		setLayout(null);

		// Header Label
		JLabel collectionLabel = new JLabel("My Movie Collection");
		ImageIcon icon = new ImageIcon(
				new ImageIcon("icon.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		collectionLabel.setFont(this.getFont().deriveFont(22.0f));
		collectionLabel.setIcon(icon);
		collectionLabel.setLocation(15, 12);
		collectionLabel.setSize(240, 40);
		this.add(collectionLabel);
		// ---

		// Quick Search Label
		JLabel titleSearchLabel = new JLabel("Quick Filter");
		titleSearchLabel.setLocation(490, 15);
		titleSearchLabel.setSize(150, 25);
		this.add(titleSearchLabel);

		// Combo Box Criteria
		this.quickFilterComboBox = new JComboBox<String>(
				new String[] { "Title", "Director", "Genre", "Year", "Rating", "Cast" });
		quickFilterComboBox.setLocation(485, 35);
		quickFilterComboBox.setSize(105, 25);
		this.add(quickFilterComboBox);

		// Query Text Field
		this.quickFilterTerm = new JTextField();
		quickFilterTerm.setToolTipText("For a range of years, use '-' (2005 - 2010)");
		quickFilterTerm.setLocation(588, 35);
		quickFilterTerm.setSize(125, 25);
		this.add(quickFilterTerm);
		// ---

		// Movie Collection Table
		this.tableModel = new DefaultTableModel(new String[] { "Title", "Director", "Genre", "Year", "Rating", "Cast" },
				0);
		this.movieCollectionTable = new JTable(tableModel) {
			// Prevent Cell Editing
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.movieCollectionTable.getTableHeader()
				.setFont(new Font(this.getFont().getFontName(), Font.BOLD, this.getFont().getSize()));
		this.movieCollectionTable.getTableHeader().setReorderingAllowed(false);
		this.movieCollectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.movieCollectionTable.setAutoCreateRowSorter(true);
		JScrollPane pane = new JScrollPane(this.movieCollectionTable);
		pane.setLocation(10, 65);
		pane.setSize(700, 200);
		this.add(pane);
		// ---

		// Movie Title
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setLocation(10, 270);
		titleLabel.setSize(50, 25);
		this.add(titleLabel);
		this.newMovieTitle = new JTextField();
		newMovieTitle.setLocation(10, 290);
		newMovieTitle.setSize(150, 25);
		this.add(newMovieTitle);
		// ---

		// Movie Director
		JLabel directorLabel = new JLabel("Director");
		directorLabel.setLocation(170, 270);
		directorLabel.setSize(75, 25);
		this.add(directorLabel);
		this.newMovieDirector = new JTextField();
		newMovieDirector.setLocation(170, 290);
		newMovieDirector.setSize(150, 25);
		this.add(newMovieDirector);
		// ---

		// Movie Release Year
		JLabel yearLabel = new JLabel("Year");
		yearLabel.setLocation(270, 315);
		yearLabel.setSize(50, 25);
		this.add(yearLabel);
		this.newMovieReleaseYear = new JSpinner(new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900,
				Calendar.getInstance().get(Calendar.YEAR) + 2, 1));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this.newMovieReleaseYear, "#");
		this.newMovieReleaseYear.setEditor(editor);
		newMovieReleaseYear.setLocation(270, 335);
		newMovieReleaseYear.setSize(65, 25);
		this.add(newMovieReleaseYear);
		// ---

		// Movie Rating
		JLabel ratingLabel = new JLabel("Rating");
		ratingLabel.setLocation(170, 315);
		ratingLabel.setSize(50, 25);
		this.add(ratingLabel);
		this.newMovieRating = new JComboBox<Rating>(Rating.values());
		newMovieRating.setLocation(170, 335);
		newMovieRating.setSize(90, 25);
		this.add(newMovieRating);
		// ---

		// Movie Genre
		JLabel genreLabel = new JLabel("Genre");
		genreLabel.setLocation(10, 315);
		genreLabel.setSize(50, 25);
		this.add(genreLabel);
		this.newMovieGenre = new JComboBox<Genre>(Genre.values());
		newMovieGenre.setLocation(10, 335);
		newMovieGenre.setSize(150, 25);
		this.add(newMovieGenre);
		// ---

		// Movie Cast List
		JLabel castLabel = new JLabel("Cast List");
		castLabel.setLocation(10, 360);
		castLabel.setSize(100, 25);
		this.add(castLabel);
		this.newMovieCastList = new JTextField();
		newMovieCastList.setLocation(10, 380);
		newMovieCastList.setSize(250, 25);
		this.add(newMovieCastList);
		// ---

		// Search Function Components
		JLabel multiCriteriaFilterLabel = new JLabel("Multi-Critera Filter");
		multiCriteriaFilterLabel.setLocation(400, 270);
		multiCriteriaFilterLabel.setSize(120, 25);
		this.add(multiCriteriaFilterLabel);

		this.searchDirCastChBx = new JCheckBox();
		searchDirCastChBx.setLocation(400, 305);
		searchDirCastChBx.setSize(25, 25);
		this.add(searchDirCastChBx);

		this.searchDirCastComBx = new JComboBox<String>(new String[] { "directed by", "casting" });
		searchDirCastComBx.setLocation(425, 305);
		searchDirCastComBx.setSize(120, 25);
		this.add(searchDirCastComBx);

		this.searchDirCastTerm = new JTextField();
		searchDirCastTerm.setLocation(400, 330);
		searchDirCastTerm.setSize(145, 25);
		this.add(searchDirCastTerm);

		this.searchRatingChBx = new JCheckBox("Rating");
		searchRatingChBx.setLocation(400, 375);
		searchRatingChBx.setSize(75, 25);
		this.add(searchRatingChBx);

		this.searchRatingComBx = new JComboBox<String>(new String[] { "of", "above", "below", "between" });
		searchRatingComBx.setLocation(470, 375);
		searchRatingComBx.setSize(120, 25);
		this.add(searchRatingComBx);

		this.searchRatingMinComBx = new JComboBox<Rating>(Rating.values());
		searchRatingMinComBx.setLocation(400, 400);
		searchRatingMinComBx.setSize(90, 25);
		this.add(searchRatingMinComBx);

		JLabel seperation = new JLabel("-");
		seperation.setLocation(490, 400);
		seperation.setSize(10, 25);
		this.add(seperation);

		this.searchRatingMaxComBx = new JComboBox<Rating>(Rating.values());
		searchRatingMaxComBx.setLocation(500, 400);
		searchRatingMaxComBx.setSize(90, 25);
		searchRatingMaxComBx.setEnabled(false);
		this.add(searchRatingMaxComBx);

		this.searchGenreChBx = new JCheckBox("Genre");
		searchGenreChBx.setLocation(570, 305);
		searchGenreChBx.setSize(75, 25);
		this.add(searchGenreChBx);

		this.searchGenreItem = new JComboBox<Genre>(Genre.values());
		searchGenreItem.setLocation(570, 330);
		searchGenreItem.setSize(145, 25);
		this.add(searchGenreItem);

		this.advancedSearchBtn = new JButton("Search");
		advancedSearchBtn.setLocation(615, 375);
		advancedSearchBtn.setSize(100, 25);
		this.add(advancedSearchBtn);

		this.advancedSearchClearBtn = new JButton("Return");
		advancedSearchClearBtn.setLocation(615, 400);
		advancedSearchClearBtn.setSize(100, 25);
		advancedSearchClearBtn.setEnabled(false);
		this.add(advancedSearchClearBtn);
		// ---

		// Add Button
		this.addBtn = new JButton("Add");
		addBtn.setLocation(265, 380);
		addBtn.setSize(60, 25);
		this.add(addBtn);
		// ---

		// Remove Button
		this.removeBtn = new JButton("Remove");
		removeBtn.setLocation(615, 270);
		removeBtn.setSize(100, 25);
		this.add(removeBtn);
		// ---
	}

	/**
	 * Refreshes view controller's elements
	 * 
	 * @param movieCollection the movie array to display in the collection table
	 */
	public void update(Movie[] movieCollection) {
		// Clear the Table
		this.tableModel.setRowCount(0);

		// Add Movies to Table
		for (Movie movie : movieCollection) {
			this.tableModel.addRow(movieToObjArray(movie));
		}

		// Enable/Disable Remove Button
		this.removeBtn.setEnabled(this.movieCollectionTable.getSelectedRow() != -1);
	}

	/**
	 * Converts a Movie into an Object Array
	 * 
	 * @param movie the input movie to convert
	 * @return an object array of the input movie
	 */
	public static Object[] movieToObjArray(Movie movie) {
		// Split Movie into respective sections
		Object[] initList = new Object[6];

		initList[0] = movie.getTitle();
		initList[1] = movie.getDirector();
		initList[2] = movie.getGenre();
		initList[3] = movie.getReleaseYear();
		initList[4] = movie.getRating();
		initList[5] = movie.getStringCastList();

		return initList;
	}
	
}
