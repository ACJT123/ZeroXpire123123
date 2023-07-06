package my.edu.tarc.zeroxpire

import android.net.Uri
import androidx.room.TypeConverter

class TypeConverters {
    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun stringToUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}