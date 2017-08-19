package com.runescape.wave;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ItemPriceTest {
    @Test
    public void itemPriceTest() {
        new ItemPrice();
    }
	@Test
	public void getItemPriceTest() throws IOException {
        URL link = new URL("file:///D:/RunescapeWebsite/wave/src/test/testdata/itemPriceTest.json");

		BigDecimal price = ItemPrice.getItemPrice(link);

		assertEquals(500, price.intValue());
	}

	@Test
    public void getItemPriceTestFileNotFound() throws IOException {
        URL link = new URL("file:///D:/RunescapeWebsite/wave/src/test/testdata/itemPriceTestNotHere.json");

        BigDecimal price = ItemPrice.getItemPrice(link);

        assertEquals(0, price.intValue());
    }
}
