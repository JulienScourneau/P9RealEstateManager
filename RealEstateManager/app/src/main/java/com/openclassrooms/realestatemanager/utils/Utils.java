package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Address;
import com.openclassrooms.realestatemanager.data.Photo;
import com.openclassrooms.realestatemanager.data.RealEstateAgent;
import com.openclassrooms.realestatemanager.data.Search;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euro) {
        return (int) Math.round(euro / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String getTodayDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static int getCategoryIndex(Spinner spinner, String category) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(category)) {
                index = i;
            }
        }
        return index;
    }

    public static int getContactIndex(Spinner spinner, RealEstateAgent contact) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(contact)) {
                index = i;
            }
        }
        return index;
    }

    public static String formatPrice(String string) {
        Long i = Long.valueOf(string);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("EUR"));
        return format.format(i);
    }

    public static String formatAddress(Address address) {
        String location;

        location = (address.getNumber() +
                System.lineSeparator() +
                address.getStreet() +
                System.lineSeparator() +
                address.getCity() +
                System.lineSeparator() +
                address.getPostalCode() +
                System.lineSeparator() +
                address.getCountry()
        );
        return location;
    }

    public static Photo convertUriToPhoto(Uri uri) {
        return new Photo(uri.toString(), 0, 0);
    }

    public static void expandAndCollapseView(ImageView button, ConstraintLayout hiddenView, LinearLayout baseLayout) {
        button.setOnClickListener(view -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(baseLayout, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                button.setImageResource(R.drawable.ic_baseline_expand_more_36);
            } else {
                TransitionManager.beginDelayedTransition(baseLayout, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                button.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        });
    }

    public static String convertLongToDate(long time) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = new Date(time);

        return dateFormat.format(date);
    }

    public static LatLng getLocationFromAddress(String strAddress, Context context) {
        Geocoder coder = new Geocoder(context);
        List<android.location.Address> address;
        LatLng latLng = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address.size() == 0) {
                return null;
            }
            android.location.Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return latLng;
    }

    private static String getConditions(boolean conditions) {
        String query = "";
        if (conditions) {
            query += " AND";
        } else {
            query += " WHERE";
        }
        return query;
    }

    public static SimpleSQLiteQuery createRawQueryString(Search search) {
        String queryString = "";
        List<Object> args = new ArrayList<>();
        boolean containsConditions = false;

        queryString += "SELECT DISTINCT estate_table.* FROM estate_table";

        //photoAvailable
        if (search.getPhotoAvailable() != null)
            if (search.getPhotoAvailable()) {
                queryString += " INNER JOIN estate_photo ON estate_table.id = estate_photo.estateId";
                Log.d("photoAvailable", "photo: " + search.getPhotoAvailable());
            }

        //Price
        if (search.getMinPrice() != null) {
            queryString += getConditions(false);
            queryString += " price >= ?";
            args.add(search.getMinPrice());
            containsConditions = true;
        }
        if (search.getMaxPrice() != null) {
            queryString += getConditions(containsConditions);
            queryString += " price <= ?";
            args.add(search.getMaxPrice());
            containsConditions = true;
        }

        //Category
        if (search.getCategory() != null)
            if (!search.getCategory().isEmpty()) {
                queryString += getConditions(containsConditions);
                queryString += " category LIKE ?";
                args.add(search.getCategory());
                containsConditions = true;
            }

        //Area
        if (search.getMinArea() != null) {
            queryString += getConditions(containsConditions);
            queryString += " area >= ?";
            args.add(search.getMinArea());
            containsConditions = true;
        }
        if (search.getMaxArea() != null) {
            queryString += getConditions(containsConditions);
            queryString += " area <= ?";
            args.add(search.getMaxArea());
            containsConditions = true;
        }

        //bedroom
        if (search.getMinBedroom() != null) {
            queryString += getConditions(containsConditions);
            queryString += " bedroom >= ?";
            args.add(search.getMinBedroom());
            containsConditions = true;
        }
        if (search.getMaxBedroom() != null) {
            queryString += getConditions(containsConditions);
            queryString += " bedroom <= ?";
            args.add(search.getMaxBedroom());
            containsConditions = true;
        }

        //bathroom
        if (search.getMinBathroom() != null) {
            queryString += getConditions(containsConditions);
            queryString += " bathroom >= ?";
            args.add(search.getMinBathroom());
            containsConditions = true;
        }
        if (search.getMaxBathroom() != null) {
            queryString += getConditions(containsConditions);
            queryString += " bathroom <= ?";
            args.add(search.getMaxBathroom());
            containsConditions = true;
        }

        //city
        if (search.getCity() != null)
            if (!search.getCity().isEmpty()) {
                queryString += getConditions(containsConditions);
                queryString += " city LIKE '%' || ? || '%'";
                args.add(search.getCity());
                containsConditions = true;
            }

        //postalCode
        if (search.getPostalCode() != null)
            if (!search.getPostalCode().isEmpty()) {
                queryString += getConditions(containsConditions);
                queryString += " postalCode LIKE '%' || ? || '%'";
                args.add(search.getPostalCode());
                containsConditions = true;
            }

        //school
        if (search.getSchool() != null) {
            queryString += getConditions(containsConditions);
            queryString += " school = ?";
            args.add(search.getSchool());
            containsConditions = true;
        }

        //localCommerce
        if (search.getLocalCommerce() != null) {
            queryString += getConditions(containsConditions);
            queryString += " localCommerce = ?";
            args.add(search.getLocalCommerce());
            containsConditions = true;
        }

        //publicTransport
        if (search.getPublicTransport() != null) {
            queryString += getConditions(containsConditions);
            queryString += " publicTransport = ?";
            args.add(search.getPublicTransport());
            containsConditions = true;
        }

        //park
        if (search.getPark() != null) {
            queryString += getConditions(containsConditions);
            queryString += " park = ?";
            args.add(search.getPark());
            containsConditions = true;
        }

        //date
        if (search.getDate() != null) {
            queryString += getConditions(containsConditions);
            queryString += " date >= ?";
            args.add(search.getDate());
        }

        Log.d("searchEstate", "queryString finish: " + queryString);
        return new SimpleSQLiteQuery(queryString, args.toArray());
    }
}
