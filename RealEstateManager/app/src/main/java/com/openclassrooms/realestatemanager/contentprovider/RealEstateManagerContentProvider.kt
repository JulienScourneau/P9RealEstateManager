package com.openclassrooms.realestatemanager.contentprovider

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.EstateDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent


val ESTATE_TABLE: String = Estate::class.java.simpleName
private const val AUTHORITY = "com.openclassrooms.realestatemanager.contentprovider"
val uri: Uri = Uri.parse("content://${AUTHORITY}/${ESTATE_TABLE}")

class RealEstateManagerContentProvider : ContentProvider() {
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, ESTATE_TABLE, 1)
        addURI(AUTHORITY, "$ESTATE_TABLE/#", 2)
    }


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ContentProviderEntryPoint {
        fun estateDao(): EstateDao
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val appContext = context?.applicationContext ?: throw IllegalStateException()

        if (context != null) {
            val estateId = ContentUris.parseId(uri)
            val estateDao: EstateDao = getEstateDao(appContext)

            val cursor: Cursor? = if (uriMatcher.match(uri) == 2) {
                estateDao.getEstateByIdCursor(estateId)
            } else {
                estateDao.getAllEstateCursor()
            }
            cursor?.setNotificationUri(appContext.contentResolver, uri)
            return cursor

        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }

    }

    override fun getType(p0: Uri): String? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    private fun getEstateDao(appContext: Context): EstateDao {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            ContentProviderEntryPoint::class.java
        )
        return hiltEntryPoint.estateDao()
    }
}