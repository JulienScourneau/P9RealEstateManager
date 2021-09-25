package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.contentprovider.RealEstateManagerContentProviderKt;
import com.openclassrooms.realestatemanager.data.Address;
import com.openclassrooms.realestatemanager.utils.TestList;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private ContentResolver contentResolver;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        contentResolver = ApplicationProvider.getApplicationContext().getContentResolver();
    }

    @Test
    public void getSingleEstateWithContentProvider() {
        long id = 1;
        Cursor cursor = contentResolver.query(ContentUris.withAppendedId(RealEstateManagerContentProviderKt.getUri(), id), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        cursor.close();
    }

    @Test
    public void getEstateListWithContentProvider() {
        Cursor cursor = contentResolver.query(RealEstateManagerContentProviderKt.getUri(), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(),is(6));
        cursor.close();
    }

    @Test
    public void isInternetAvailable() {
        boolean internetAvailable = Utils.isInternetAvailable(context);
        assertTrue(internetAvailable);
    }

    @Test
    public void getLocationFromAddressIsCorrect() {
        Address address = TestList.INSTANCE.getGetEstate0().getAddress();
        String addressString = Utils.formatAddress(address);
        LatLng location = Utils.getLocationFromAddress(addressString, context);
        assertEquals(new LatLng(50.8465716, 4.3438595), location);
    }
}
