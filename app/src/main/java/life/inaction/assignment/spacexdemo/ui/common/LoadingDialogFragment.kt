package life.inaction.assignment.spacexdemo.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import life.inaction.assignment.spacexdemo.R

class LoadingDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "LoadingDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.let {
            isCancelable = false
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
