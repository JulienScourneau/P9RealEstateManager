package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class TestList {

    private static final List<Integer> photos = Collections.emptyList();
    private static final Address address = new Address(100,"Fake Street","Additional Address","Fake Country","000000");

    private static final List<Estate> estateList = Arrays.asList(
            new Estate(photos, "Fake_category", "000 000 €", "Fake_Description", 600, 8, 1, 2, address)
    );

    public static ArrayList<Estate> getTestList() {
        return new ArrayList<>(estateList);
    }
}
