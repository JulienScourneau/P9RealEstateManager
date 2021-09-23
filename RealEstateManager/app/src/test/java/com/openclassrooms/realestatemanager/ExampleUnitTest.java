package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.data.Address;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void convertDollarToEuroIsCorrect() throws Exception {
        int dollar = 1000;
        dollar = Utils.convertDollarToEuro(dollar);
        assertEquals(812, dollar);
    }

    @Test
    public void convertEuroToDollarIsCorrect() throws Exception {
        int euro = 1000;
        euro = Utils.convertEuroToDollar(euro);
        assertEquals(1232, euro);
    }

    @Test
    public void getTodayDateIsCorrect() {
        Date actualDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = df.format(actualDate);
        String todayDate = Utils.getTodayDate();
        assertEquals(formattedDate, todayDate);
    }

    @Test
    public void formattedPriceIsCorrect() {
        String formattedPrice = Utils.formatPrice("100");
        assertEquals("100 €", formattedPrice);
    }

    @Test
    public void formattedAddressIsCorrect() {
        Address address = new Address("11", "Fake street", "Fake city", "Fake country", "7777");
        String formattedAddress = Utils.formatAddress(address);
        String expectedAddress = ("11" +
                System.lineSeparator() +
                "Fake street" +
                System.lineSeparator() +
                "Fake city" +
                System.lineSeparator() +
                "7777" +
                System.lineSeparator() +
                "Fake country");
        assertEquals(expectedAddress, formattedAddress);
    }
}