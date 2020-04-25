package Models

import java.io.Serializable

class AyahsForCategoriesModel(val id: Int, val category: String, val surah_id: Int, val ayah_id: Int, val surah: String, var ayah: String, val orderNo: Int,
                              val shortAyah: String, var ayah_ids_text: String) : Serializable  {

}
