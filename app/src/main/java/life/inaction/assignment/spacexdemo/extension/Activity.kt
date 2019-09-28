package life.inaction.assignment.spacexdemo.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Activity.goToActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.goToActivityForResult(code: Int, bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivityForResult(intent, code)
}
