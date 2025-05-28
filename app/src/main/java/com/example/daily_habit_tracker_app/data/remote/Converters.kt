package com.example.daily_habit_tracker_app.data.remote

import androidx.room.TypeConverter
import com.example.daily_habit_tracker_app.domain.model.HabitFrequency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromFrequency(value: String): HabitFrequency = HabitFrequency.valueOf(value)

    @TypeConverter
    fun toFrequency(freq: HabitFrequency): String = freq.name

    @TypeConverter
    fun fromCompletionHistory(value: String): Map<LocalDate, Boolean> {
        val type = object : TypeToken<Map<LocalDate, Boolean>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toCompletionHistory(map: Map<LocalDate, Boolean>): String {
        return gson.toJson(map)
    }
}
