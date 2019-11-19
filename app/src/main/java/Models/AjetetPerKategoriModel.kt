package Models

import java.io.Serializable

class AjetetPerKategoriModel(val id: Int, val tagu: String, val surja_id: Int, val ajeti_id: Int, val surja: String, val ajeti: String, val nr_rendor: Int,
                             val ajeti_shkurt: String) : Serializable  {

}
