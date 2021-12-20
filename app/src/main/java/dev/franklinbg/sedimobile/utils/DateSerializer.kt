package dev.franklinbg.sedimobile.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateSerializer : JsonDeserializer<Date>, JsonSerializer<Date> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(json.asString)!!

    override fun serialize(
        src: Date,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = JsonPrimitive(SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(src))
}