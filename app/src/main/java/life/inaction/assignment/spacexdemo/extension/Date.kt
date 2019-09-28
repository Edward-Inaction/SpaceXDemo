package life.inaction.assignment.spacexdemo.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")

fun Date.toDateString(): String {
    return DATE_FORMAT.format(this)
}
