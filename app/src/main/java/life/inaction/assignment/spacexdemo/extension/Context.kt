package life.inaction.assignment.spacexdemo.extension

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import life.inaction.assignment.spacexdemo.R


fun Context.toast(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
fun Context.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        toast(R.string.error_no_browser)
    }
}

