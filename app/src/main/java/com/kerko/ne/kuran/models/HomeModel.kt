package Models

import java.io.Serializable

/**
 * Created by Ibrhaim Vasija on 26/04/2020
 **/
class HomeModel(
    val count: Int,
    val category: String,
    val id: Int
) :
    Serializable {
}

