package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Address;
import com.openclassrooms.realestatemanager.data.Photo;
import com.openclassrooms.realestatemanager.data.RealEstateAgent;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
                button.setImageResource(R.drawable.ic_baseline_expand_more_24);
            } else {
                TransitionManager.beginDelayedTransition(baseLayout, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                button.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        });
    }

    public static String convertLongToDate(long time){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(time);

        return format.format(date);
    }

    public static Long convertDateToLong(String date) {
        long dateLong = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateLong = dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateLong;
    }
}
