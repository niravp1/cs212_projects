import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/**
 * Main driver class for Project 3.
 * Creates a GUI with a File menu that allows users to open date files.
 * Uses TreeMap to automatically sort dates and remove duplicates.
 * Handles invalid dates with IllegalDate212Exception.
 * 
 * @author Nirav Persaud
 * @version 1.0
 */
public class Project3 {
	
	/** TreeMap to store dates (automatically sorted, no duplicates) */
	private TreeMap<Date212, String> dateMap;
	
	/** Text area to display unsorted dates */
	private JTextArea unsortedArea;
	
	/** Text area to display sorted dates */
	private JTextArea sortedArea;
	
	/** The main frame */
	private JFrame frame;
	
	/**
	 * Constructor that initializes the GUI.
	 */
	public Project3() {
		dateMap = new TreeMap<>();
		createAndShowGUI();
	}
	
	/**
	 * Main method to launch the application.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Project3());
	}
	
	/**
	 * Creates and displays the GUI with menu bar and text areas.
	 */
	private void createAndShowGUI() {
		// Create and set up the window
		frame = new JFrame("Date Display - Project 3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setLocation(200, 200);
		frame.setLayout(new GridLayout(1, 2));
		
		// Create menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		// Create Open menu item
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(e -> openFile());
		fileMenu.add(openItem);
		
		fileMenu.addSeparator();
		
		// Create Quit menu item
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(quitItem);
		
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		
		// Create left text area for unsorted dates
		unsortedArea = new JTextArea(5, 20);
		unsortedArea.setEditable(false);
		unsortedArea.setText("Unsorted:\n");
		JScrollPane leftScrollPane = new JScrollPane(unsortedArea);
		frame.add(leftScrollPane);
		
		// Create right text area for sorted dates
		sortedArea = new JTextArea(5, 20);
		sortedArea.setEditable(false);
		sortedArea.setText("Sorted:\n");
		JScrollPane rightScrollPane = new JScrollPane(sortedArea);
		frame.add(rightScrollPane);
		
		// Display the window
		frame.setVisible(true);
	}
	
	/**
	 * Opens a file chooser dialog and processes the selected file.
	 */
	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		
		int result = fileChooser.showOpenDialog(frame);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			processFile(selectedFile.getAbsolutePath());
		}
	}
	
	/**
	 * Processes the date file and displays dates in both text areas.
	 * Reads dates from file, validates them, and stores in TreeMap.
	 * Invalid dates are caught and their error messages are printed to console.
	 * 
	 * @param filename the name of the file to process
	 */
	private void processFile(String filename) {
		// Clear previous data
		dateMap.clear();
		unsortedArea.setText("Unsorted:\n");
		sortedArea.setText("Sorted:\n");
		
		// List to maintain order for unsorted display
		ArrayList<Date212> unsortedList = new ArrayList<>();
		
		TextFileInput inFile = new TextFileInput(filename);
		String line = inFile.readLine();
		
		while (line != null) {
			StringTokenizer tokens = new StringTokenizer(line, ",");
			
			while (tokens.hasMoreTokens()) {
				String dateString = tokens.nextToken().trim();
				
				try {
					Date212 date = new Date212(dateString);
					unsortedList.add(date);
					// TreeMap automatically handles duplicates (key overwrites)
					dateMap.put(date, null);
				} catch (IllegalDate212Exception e) {
					// Print error message to console
					System.out.println("Invalid date: " + dateString);
					System.out.println("Exception: " + e.getMessage());
				}
			}
			
			line = inFile.readLine();
		}
		
		inFile.close();
		
		// Display unsorted dates
		for (Date212 date : unsortedList) {
			unsortedArea.append(date.toString() + "\n");
		}
		
		// Display sorted dates (TreeMap automatically sorts)
		for (Date212 date : dateMap.keySet()) {
			sortedArea.append(date.toString() + "\n");
		}
	}
}