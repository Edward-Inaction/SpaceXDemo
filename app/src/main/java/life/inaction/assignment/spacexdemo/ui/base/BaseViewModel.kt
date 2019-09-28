package life.inaction.assignment.spacexdemo.ui.base

import androidx.lifecycle.ViewModel
import life.inaction.assignment.spacexdemo.ui.helper.SingleLiveEvent

abstract class BaseViewModel<Error> : ViewModel() {
    val error = SingleLiveEvent<Error>()
}
