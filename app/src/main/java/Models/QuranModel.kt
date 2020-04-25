package Models

import java.io.Serializable

class QuranModel(val id: Int, val surah: String, val ayah: String, val surahId: Int, val ayahId: Int) :
    Serializable {
}

