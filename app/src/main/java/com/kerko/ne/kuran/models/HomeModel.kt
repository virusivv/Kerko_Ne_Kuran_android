package Models

import java.io.Serializable

class HomeModel(
    val count: Int,
    val category: String,
    val id: Int
) :
    Serializable {
}

