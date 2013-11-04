import java.math.BigDecimal;
import org.jsoup.nodes.Element;

/**
 * A subclass of Item. Implements the convertElementToItem method with 
 * parsing information specific to items with the deal css-class.
 */

/**
 * @author Xinran Wang
 * @see Item
 */
public class DealItem extends Item {
	/**
	 * Class constructor specifying a provided element.
	 * 
	 * @param elem	HTML element containing information about the DealItem.
	 */
	public DealItem(Element elem){
		super();
		convertElementToItem(elem);
	}
	
	/**
	 * Class constructor specifying specific fields' information.
	 * 
	 * @param name		the name of the item
	 * @param price		price of the item
	 * @param shipping	shipping information of the item
	 * @param vendor	vendor that is selling the item
	 */
	public DealItem(String name, String price, String shipping, String vendor){
		super();
		this.name = name;
		this.price = new BigDecimal(price);
		this.shipping = shipping;
		this.vendor = vendor;
	}
	
	@Override
	public void convertElementToItem(Element elem) {
		try{
			this.name = elem.select(".productName").first().getElementsByAttributeValueStarting("id", "nameQA").first().attr("title");
			this.price = new BigDecimal(elem.select("input[name=itemPrice]").first().attr("value"));
			this.vendor = elem.select("span.merchantName").html();
			Element shippingDiv = elem.select("div.taxShippingArea span").first();
			if (shippingDiv.classNames().contains("calc")){
				// Shipping cost
				this.shipping = shippingDiv.html().replaceAll("[^0-9$.]", ""); 
			} else if (shippingDiv.classNames().contains("noCalc")){
				// "Free Shipping"
				this.shipping = shippingDiv.select("span.freeShip").first().html();  
			} else if (shippingDiv.classNames().contains("missCalc")){
				// "No Shipping Info"
				this.shipping = shippingDiv.html(); 
			}
		} catch (NullPointerException e){
		} catch (Exception e){
			System.out.println("Unexpected error when converting element to item.");
			System.out.println("See error message: " + e.getMessage());
		}
		this.checkItemValidity();
	}
	
	
}
