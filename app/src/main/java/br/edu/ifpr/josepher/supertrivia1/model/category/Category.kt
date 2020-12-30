package br.edu.ifpr.josepher.supertrivia1.model.category

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("name") var name: String,
) {
    var id: Long? = null
}