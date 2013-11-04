import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * The main entry point fort the TextScraper program.
 * 
 * <p> 
 * Can be used from the command line as:
 * <p>
 * <code> java -jar TextScraper.jar &ltkeyword&gt</code>
 * or
 * <code> java -jar TextScraper.jar &ltkeyword&gt &ltpage_number&gt</code>
 * <p>
 * <ul>
 * <li>&ltkeyword&gt - the search keyword
 * <li>&ltpage_number&gt - optional input, the page number of the results
 * </ul>
 * <p>
 * If only the search keyword is provided, the program returns the total number
 * of search results. If the page number is also provided, the program returns
 * a result object and then prints results on the screen.
 */


/**
 * @author Xinran Wang
 */
public class TextScraper {
	/**
	 * This main program creates an appropriate search url, creates a Document 
	 * object containing the search results, and creates either an int for the 
	 * number of results or a Result object for all the results. The output is
	 * then printed on the screen.
	 * 
	 * @param args - array of command line arguments that specify the search 
	 * keyword and optionally a results page number
	 * @throws IOException
	 */
	public static void main(String[] args) {
		if (args.length <= 0 || args[0].equals("")){
			System.out.println("Error - please enter a valid keyword.");
			System.out.println("usage: java -jar TextScraper.jar <keyword> or ");
			System.out.println("usage: java -jar TextScraper.jar <keyword> <page number>");
			return;
		} else if (args.length > 2){
			System.out.println("Error - too many inputs.");
			System.out.println("usage: java -jar TextScraper.jar <keyword> or ");
			System.out.println("usage: java -jar TextScraper.jar <keyword> <page number>");
			return;
		} else if (args.length == 2 && args[1].equals("")){
			System.out.println("Error - please enter a valid page number");
			return;
		}
		
		try{
			if (args.length == 1){
				Document itemsPage = loadFromUrl("http://www.shopping.com/", 
						args[0], "1");
				int numResults = getNumberOfResults(itemsPage); 
				System.out.println(numResults);
			} else {
				Document itemsPage = loadFromUrl("http://www.shopping.com/", 
						args[0], args[1]);
				Result result = getResultDetails(itemsPage);
				if (result.size() < 1){
					System.out.println("There were no matches for " 
				+ args[0] + " on search page " + args[1] + ".");
					System.out.println("Please check your spelling, keyword, or page number and try again.");
				} else {
					result.printResult();
				}
			}
		} catch (IOException e){
			System.out.println("Caught IOException attempting to reach the webpage with inputs: " + args);
			System.err.println("Error message: " + e.getMessage());
		}		
	}
	
	/**
	 * Returns a Document object from a URL generated from the URI argument, 
	 * search keyword, and results pageNumber. A [GET] request is performed on 
	 * the generated URL to get the results page.
	 * <p>
	 * Throws an IOException if there is an error with the [GET] request.
	 * 
	 * @param baseURI 		an absolute URI with the address of the website
	 * @param keyword		name of the item that is being searched
	 * @param pageNumber	the results page number
	 * @return				the HTML document of the requested results page
	 * @throws IOException	on error with the [GET] request 
	 */
	private static Document loadFromUrl(String baseURI, String keyword, 
			String pageNumber) throws IOException{
		String url = baseURI + keyword.replace(" ", "-") 
				+ "/products~PG-" + pageNumber 
				+ "?KW=" + keyword.replace(" ", "+");
		return Jsoup.connect(url).get();
	}
	
	/**
	 * Returns the total number of search results associated with a keyword 
	 * by parsing the doc argument passed in.
	 * 
	 * @param doc 	document to be parsed for results
	 * @return		total number of search results
	 */
	private static int getNumberOfResults(Document doc){
		String numItemsString = doc.select("#sortFiltersBox").first()
				.getElementsByAttributeValueStarting("name", "NumItemsReturned").first()
				.attr("name");
		return Integer.parseInt(numItemsString.replaceAll("[^\\d.]", ""));
	}
	
	/**
	 * Returns a Result object containing all the items from the doc argument. 
	 * The result object is represented by a List of Items.
	 * 
	 * @param doc	document containing a set of search results
	 * @return		the result object containing the items from the document
	 */
	private static Result getResultDetails(Document doc){
		Elements itemElements = 
				doc.getElementsByAttributeValueStarting("id", "quickLookItem");
		Result result = new Result();
		result.addItemElements(itemElements);
		return result;
	}
}
