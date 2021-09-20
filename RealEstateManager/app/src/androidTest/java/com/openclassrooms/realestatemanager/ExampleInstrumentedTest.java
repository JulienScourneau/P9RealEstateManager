package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.contentprovider.RealEstateManagerContentProviderKt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest  {
    private ContentResolver contentResolver;

    @Before
    public void setUp(){

        contentResolver = ApplicationProvider.getApplicationContext().getContentResolver();
    }

    @Test
    public void getItemWithContentProvider() {
        long id = 1;
        Cursor cursor = contentResolver.query(ContentUris.withAppendedId(RealEstateManagerContentProviderKt.getUri(),id), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        cursor.close();
    }
}
