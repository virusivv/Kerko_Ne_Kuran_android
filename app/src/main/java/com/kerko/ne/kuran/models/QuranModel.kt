package Models

import java.io.Serializable

/**
 * Created by Ibrhaim Vasija on 26/04/2020
 **/
class QuranModel(val id: Int, val surah: String, val ayah: String, val surahId: Int, val ayahId: Int, val language: String) :
    Serializable {
}

