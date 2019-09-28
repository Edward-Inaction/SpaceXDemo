package life.inaction.assignment.spacexdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.inaction.assignment.spacexdemo.model.LaunchModel
import life.inaction.assignment.spacexdemo.ui.base.BaseViewModel
import life.inaction.assignment.spacexdemo.usercase.FetchLaunchesCase

class MainViewModel(private val mFetchLaunchesCase: FetchLaunchesCase) : BaseViewModel<MainViewModel.ViewModelError>() {
    private lateinit var mNavigator: ViewModelNavigator

    private var mRawLaunches: List<LaunchModel> = emptyList()

    private val mLaunches = MutableLiveData<List<LaunchModel>>()
    val launches: LiveData<List<LaunchModel>> = mLaunches

    private val mIsRefreshing = MutableLiveData<Boolean>(false)
    val isRefreshing:LiveData<Boolean> = mIsRefreshing

    private val mSortOption = MutableLiveData<SortOption>(
        SortOption.YEAR_ASC
    )
    val sortOption: LiveData<SortOption> = mSortOption

    private val mFilterOption = MutableLiveData<FilterOption>(
        FilterOption.ALL
    )
    val filterOption: LiveData<FilterOption> = mFilterOption

    fun setNavigator(navigator: ViewModelNavigator) {
        mNavigator = navigator

        if (mLaunches.value == null) {
            refresh()
        }
    }

    fun refresh() {
        if (isRefreshing.value == true) {
            return
        }
        mIsRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mFetchLaunchesCase.run()
                mRawLaunches = result
                formData()
            } catch (e: Exception) {
                error.postValue(ViewModelError.REFRESH_FAILED)
            } finally {
                mIsRefreshing.postValue(false)
            }
        }
    }

    private fun formData() {
        val filteredResult = when (mFilterOption.value) {
            FilterOption.ALL -> mRawLaunches
            FilterOption.SUCCESS -> mRawLaunches.filter { it.success == true }
            FilterOption.FAILURE -> mRawLaunches.filter { it.success == false }
            FilterOption.UPCOMING -> mRawLaunches.filter { it.upcoming }
            else -> mRawLaunches
        }

        val sortedResult = filteredResult.sortedBy {
            when (mSortOption.value) {
                SortOption.YEAR_ASC -> it.launchYear.toInt()
                SortOption.YEAR_DESC -> -it.launchYear.toInt()
                SortOption.NAME -> it.missionName.toLowerCase().first().toInt()
                else -> it.launchYear.toInt()
            }
        }

        mLaunches.postValue(sortedResult)
    }

    fun sort(sortOption: SortOption) {
        mSortOption.value = sortOption
        formData()
    }

    fun filter(filterOption: FilterOption) {
        mFilterOption.value = filterOption
        formData()
    }

    enum class SortOption {
        YEAR_ASC,
        YEAR_DESC,
        NAME,
    }

    enum class FilterOption {
        ALL,
        SUCCESS,
        FAILURE,
        UPCOMING,
    }

    interface ViewModelNavigator {
    }

    enum class ViewModelError {
        REFRESH_FAILED
    }
}
