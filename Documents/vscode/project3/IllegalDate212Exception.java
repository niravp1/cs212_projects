/**
 * Exception thrown when an invalid date string is provided to Date212.
 * Extends IllegalArgumentException to indicate invalid date format or values.
 * 
 * @author Nirav Persaud
 * @version 1.0
 */
public class IllegalDate212Exception extends IllegalArgumentException {
	
	/**
	 * Constructs an IllegalDate212Exception with the specified detail message.
	 * 
	 * @param message the detail message explaining why the date is invalid
	 */
	public IllegalDate212Exception(String message) {
		super(message);
	}
}