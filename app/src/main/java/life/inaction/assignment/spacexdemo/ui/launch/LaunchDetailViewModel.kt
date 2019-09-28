package life.inaction.assignment.spacexdemo.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.inaction.assignment.spacexdemo.model.RocketModel
import life.inaction.assignment.spacexdemo.ui.base.BaseViewModel
import life.inaction.assignment.spacexdemo.usercase.FetchRocketCase

class LaunchDetailViewModel(private val mFetchRocketCase: FetchRocketCase) : BaseViewModel<LaunchDetailViewModel.ViewModelError>() {
    private lateinit var mNavigator: ViewModelNavigator

    private lateinit var mRocketId: String

    private val mRocket = MutableLiveData<RocketModel>()
    val rocket: LiveData<RocketModel> = mRocket

    private val mIsLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = mIsLoading

    fun setNavigator(navigator: ViewModelNavigator) {
        mNavigator = navigator

        if (mRocket.value == null) {
            load()
        }
    }

    fun setData(rocketId: String) {
        mRocketId = rocketId
    }

    fun load() {
        if (isLoading.value == true) {
            return
        }
        mIsLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mFetchRocketCase.run(mRocketId)
                mRocket.postValue(result)
            } catch (e: Exception) {
                error.postValue(ViewModelError.LOAD_FAILED)
            } finally {
                mIsLoading.postValue(false)
            }
        }
    }

    fun goToWikipedia() {
        mRocket.value?.let {
            mNavigator.goToWikipedia(it.wikipedia)
        }
    }

    interface ViewModelNavigator {
        fun goToWikipedia(url: String)
    }

    enum class ViewModelError {
        LOAD_FAILED
    }
}
