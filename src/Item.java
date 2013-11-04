import java.math.BigDecimal;
import java.text.NumberFormat;
import org.jsoup.nodes.Element;

/**
 * An abstract class to represent a sales item. Has four fields with necessary
 * information about the item, provides a method for a String representation, 
 * and declares the abstract method to convert an HTML element to an item.
 */

/** 
 * @author Xinran Wang
 */

public abstract class Item {
	protected String name;
	protected BigDecimal price;
	protected String shipping;
	protected String vendor;
	protected Boolean hasError;
	
	/**
	 * Class constructor. 
	 * <p>
	 * Sets name, shipping and vendor fields to blank strings and sets price 
	 * to be 0.
	 */
	public Item() {
		name = "";
		price = new BigDecimal(0);
		shipping = "";
		vendor = "";
		hasError = false;
	}
	
	/**
	 * Returns a string representation of the Item.
	 * Each field is labeled and the price is converted to a local currency 
	 * format.
	 * If the hasError field is true, adds a warning message.
	 */
	public String toString(){
		String result = "";
		result = result.concat("Product Name: " + name + "\r\n");
		result = result.concat("Price: " + NumberFormat.getCurrencyInstance().format(price) + "\r\n");
		result = result.concat("Shipping Price: " + shipping + "\r\n");
		result = result.concat("Vendor: " + vendor + "\r\n");
		if (hasError){
			result = result.concat("Warning - this item might have an error!\r\n");
		} 
		return result;
	}
	
	/**
	 * Checks if any of the fields of the item is blank. If so, replaces the 
	 * field with an error or warning message and changes the hasError field.
	 */
	protected void checkItemValidity(){
		if (name.isEmpty()){
			name = "Error - invalid item";
			hasError = true;
		}
		if (vendor.isEmpty()){
			vendor = "Warning - no vendor provided";
			hasError = true;
		}
		if (shipping.isEmpty()){
			shipping = "Warning - no shipping information was provided";
			hasError = true;
		}
	}
	
	/**
	 * Parses an HTML element to extract necessary information for an item.
	 * Replaces old item information with data from the new element.
	 * 
	 * @param elem	new element from which item information is to be extracted
	 */
	public abstract void convertElementToItem(Element elem);
	
	/**
	 * @return product name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @return shipping information
	 */
	public String getShipping() {
		return shipping;
	}

	/**
	 * @return vendor informatoin
	 */
	public String getVendor() {
		return vendor;
	}	
	
	/**
	 * @return if the item might have an error
	 */
	public Boolean getHasError(){
		return hasError;
	}
}
