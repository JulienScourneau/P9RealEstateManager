package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

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
        assertEquals(812,dollar);
    }

    @Test
    public void convertEuroToDollarIsCorrect() throws Exception {
        int euro = 1000;
        euro = Utils.convertEuroToDollar(euro);
        assertEquals(1232,euro);
    }

    @Test
    public void getTodayDateIsCorrect() {
        Date actualDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = df.format(actualDate);

        String todayDate = Utils.getTodayDate();
        assertEquals(formattedDate,todayDate);
    }
}