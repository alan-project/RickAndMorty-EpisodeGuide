package net.alanproject.data.common

import net.alanproject.domain.model.DomainQuoteModel
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.*

fun parseQuotesJson(json: String): List<DomainQuoteModel> {
    Timber.d("parseQuotesJson In")
//    Timber.d("json: $json")
    val jsonArray = JSONArray(json)
    var jsonList = emptyList<JSONObject>()
    for (index in 0 until jsonArray.length()) {
        val jsonObject: JSONObject = jsonArray.getJSONObject(index)
        jsonObject.let {
            jsonList = jsonList + it
        }
    }

//    Timber.d("jsonList: $jsonList")
    val response =  jsonList.map {
        DomainQuoteModel(it.getString("quote"), it.getString("name"))
    }.shuffled(random = Random())

//    Timber.d("response: $response")
    return response
}