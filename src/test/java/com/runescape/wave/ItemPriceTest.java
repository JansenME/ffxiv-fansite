package com.runescape.wave;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ItemPriceTest {
    private static final Logger logger = LoggerFactory.getLogger(ItemPriceTest.class);

    @Test
    public void itemPriceTest() {
        logger.info("Performing itemPriceTest");
        new ItemPrice();
    }
	@Test
	public void getItemPriceTest() throws IOException {
        logger.info("Performing getItemPriceTest");
        URL link = new URL("file:///D:/RunescapeWebsite/wave/src/test/testdata/itemPriceTest.json");

		BigDecimal price = ItemPrice.getItemPrice(link, 0L);

		assertEquals(500, price.intValue());
	}

	@Test
    public void getItemPriceTestFileNotFound() throws IOException {
        logger.info("Performing getItemPriceTestFileNotFound");
        URL link = new URL("file:///D:/RunescapeWebsite/wave/src/test/testdata/itemPriceTestNotHere.json");

        BigDecimal price = ItemPrice.getItemPrice(link, 0L);

        assertEquals(0, price.intValue());
    }
}
