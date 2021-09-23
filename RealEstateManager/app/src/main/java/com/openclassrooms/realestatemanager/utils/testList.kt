package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.PointOfInterest
import com.openclassrooms.realestatemanager.data.RealEstateAgent

object TestList {

    private val address0 =
        Address("118", "Rue Can Artevelde", "Bruxelles", "Belgique", "1000")
    private val address1 =
        Address("17", "Rue Evezard", "Étampes", "France", "91150")
    private val address2 =
        Address("32", "Rue Mercelis", "Ixelles", "Belgique", "1050")
    private val address3 =
        Address("96", "Rue Gatti de Gamond", "Uccle", "Belgique", "1180")
    private val address4 =
        Address("24", "Avenue des Tropiques", "Forest", "Belgique", "1190")
    private val address5 =
        Address("21", "Rue du Cerf", "Binche", "Belgique", "7130")

    private val poi0 =
        PointOfInterest(school = true, localCommerce = false, publicTransport = true, park = false)
    private val poi1 =
        PointOfInterest(school = false, localCommerce = true, publicTransport = false, park = true)
    private val poi2 =
        PointOfInterest(school = false, localCommerce = false, publicTransport = false, park = true)
    private val poi3 =
        PointOfInterest(school = true, localCommerce = false, publicTransport = false, park = false)
    private val poi4 =
        PointOfInterest(school = false, localCommerce = true, publicTransport = true, park = false)
    private val poi5 =
        PointOfInterest(school = true, localCommerce = true, publicTransport = true, park = true)

    private val estate0 =
        Estate(
            "Appartement", "700000", "Fake Description", "600", "8", "1", "2", false,
            1630156645559, RealEstateAgent("Adelajda Rutkowska", "0561049730"),
            poi0, address0, 1
        )
    private val estate1 =
        Estate(
            "Maison", "800000", "Fake_Description", "800", "9", "2", "3", true,
            1629466664808, RealEstateAgent("Sandra Eberhardt", "0548872315"),
            poi1, address1, 2
        )
    private val estate2 =
        Estate(
            "Villa", "707000", "Fake Description", "600", "8", "1", "2", false,
            1629898736454, RealEstateAgent("Rebecca Z. McCoy", "0416869852"),
            poi2, address2, 3
        )
    private val estate3 =
        Estate(
            "Manoir", "1000000", "Manoir description", "1000", "12", "3", "3", false,
            1630491742641, RealEstateAgent("Adelajda Rutkowska", "0561049730"),
            poi3, address3, 4
        )
    private val estate4 =
        Estate(
            "Appartement", "500000", "Nouveau appartement", "120", "4", "1", "2", true,
            1631096613818, RealEstateAgent("Sandra Eberhardt", "0548872315"),
            poi4, address4, 5
        )
    private val estate5 =
        Estate(
            "Maison", "320000", "Nouvelle maison", "450", "10", "2", "4", false,
            16311701541002, RealEstateAgent("Rebecca Z. McCoy", "0416869852"),
            poi5, address5, 6
        )

    val getEstate0: Estate
        get() = estate0

    val getEstate1: Estate
        get() = estate1

    val getEstate2: Estate
        get() = estate2

    val getEstate3: Estate
        get() = estate3

    val getEstate4: Estate
        get() = estate4

    val getEstate5: Estate
        get() = estate5
}



