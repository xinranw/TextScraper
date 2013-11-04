import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * An class to hold all results of a search results page. Each search result 
 * is converted into an Item object. 
 * A result object can import an Elements list as an ArrayList&ltItem&gt, 
 * print its contents to screen, and return the number of Items. 
 */

/**
 * @author User
 *
 */
public class Result {
	List<Item> itemsList;
	
	/**
	 * Default constructor for a Result object. Initializes by creating an 
	 * ArrayList of Items.
	 */
	public Result() {
		itemsList = new ArrayList<Item>();
	}
	
	/**
	 * Fills itemList from an Elements object by iterating through the list 
	 * of Elements and creating a new Item object for each. Items are either 
	 * ProductItem or DealItem; type defaults to ProductItem if a type cannot 
	 * be found.
	 * 
	 * @param elems	the list of Elements to be converted to items and 
	 * added to itemsList 
	 */
	public void addItemElements(Elements elems){
		for (Element elem : elems){
			Item i;
			if (elem.classNames().contains("product")){
				i = new ProductItem(elem);
			} else if (elem.classNames().contains("deal")){
				i = new DealItem(elem);
			} else {
				i = new ProductItem(elem);
				System.out.println("might be the wrong type of item");
			}
			i.convertElementToItem(elem);
			itemsList.add(i);
		}
	}
	
	/**
	 * Prints the list of items.
	 * Tracks number of items with possible errors and displays an additional
	 * message with number of such items.
	 */
	public void printResult(){
		int errorsAndWarnings = 0;
		for (Item item : itemsList){
			if (item.getHasError()){
				errorsAndWarnings++;
			}
			System.out.println(item.toString());
		}
		if (errorsAndWarnings > 0){
			System.out.println(errorsAndWarnings + " item(s) may have errors");
		}
	}
	
	/**
	 * Gets the size of the itemsList.
	 * 
	 * @return number of items in this Result object
	 */
	public int size(){
		return itemsList.size();
	}
	
}
