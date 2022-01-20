package dev.franklinbg.sedimobile.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class SqlDateSerializer : JsonSerializer<Date>, JsonDeserializer<Date> {
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    override fun serialize(
        date: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(simpleDateFormat.format(date))


    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date = Date(simpleDateFormat.parse(json!!.asString).time)
}