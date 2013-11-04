import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Test;
import static org.junit.Assert.*;
public class ResultTest {

	@Test
	public void addItemElementsTest(){
		Elements emptyList = new Elements(5);
		Element empty = new Element(Tag.valueOf("a"), "");
		emptyList.add(empty);
		emptyList.add(empty);
		emptyList.add(empty);
		emptyList.add(empty);
		emptyList.add(empty);
		Result result = new Result();
		result.addItemElements(emptyList);
		assertEquals("Result should have five Items", 5, result.size());
	}
}
