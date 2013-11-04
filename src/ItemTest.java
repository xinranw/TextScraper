import java.math.BigDecimal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {

	@Test
	public void DealItemErrorTest(){
		String html = "";
		Document document = Jsoup.parse(html);
		Element element = document.getElementsByClass("deal").first();
		Item item = new DealItem(element);
		assertTrue("Name should contain an error", item.getName().contains("Error"));
		assertEquals("Price should be 0", new BigDecimal(0), item.getPrice());
		assertTrue("Vendor information should contain a warning",
				item.getVendor().contains("Warning"));
		assertTrue("Shipping information should contain a warning",
				item.getShipping().contains("Warning"));
	}
	
	@Test
	public void ProductItemConstructorTest(){
		Item i = new ProductItem("productItem", "10", "Free Shipping", "Amazon");
		assertFalse("Name should not be empty", i.getName().equals(""));
		assertEquals("Price should be ten", new BigDecimal(10), i.getPrice());
	}

	@Test
	public void DealItemConstructorTest(){
		Item i = new DealItem("dealItem", "10", "Free Shipping", "Amazon");
		assertFalse("Name should not be empty", i.getName().equals(""));
		assertEquals("Name should be dealItem", "dealItem", i.getName());
	}
}