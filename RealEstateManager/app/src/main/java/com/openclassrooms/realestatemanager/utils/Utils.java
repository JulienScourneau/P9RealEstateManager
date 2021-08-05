package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Spinner;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.openclassrooms.realestatemanager.data.Address;
import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.data.Photo;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

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

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
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
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    public static int getIndex(Spinner spinner, String category) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(category)) {
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

    public static ArrayList<Photo> uriToPhoto(ArrayList<Uri> uriList) {
        ArrayList<Photo> photoList = new ArrayList<>();

        for (int i = 0; i < uriList.size(); i++) {
            photoList.add(new Photo( uriList.get(i).toString(),0,0));
            Log.d("uriToPhoto", "photoList Reference: " + photoList.get(0).getPhotoReference());
        }

        return photoList;
    }


}
