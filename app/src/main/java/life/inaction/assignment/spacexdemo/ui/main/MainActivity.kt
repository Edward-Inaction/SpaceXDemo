package life.inaction.assignment.spacexdemo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.os.bundleOf
import androidx.core.widget.PopupWindowCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import life.inaction.assignment.spacexdemo.R
import life.inaction.assignment.spacexdemo.extension.goToActivity
import life.inaction.assignment.spacexdemo.extension.toast
import life.inaction.assignment.spacexdemo.model.LaunchModel
import life.inaction.assignment.spacexdemo.ui.common.LoadingDialogFragment
import life.inaction.assignment.spacexdemo.ui.common.OptionPopupWindow
import life.inaction.assignment.spacexdemo.ui.launch.LaunchDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
    MainViewModel.ViewModelNavigator {

    private val mVm: MainViewModel by viewModel()
    private val mLaunchAdapter =
        LaunchAdapter(this::onLaunchClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mVm.setNavigator(this)

        setupView()
        observe()
    }

    private fun setupView() {
        recyclerView.adapter = mLaunchAdapter
        recyclerView.addItemDecoration(LaunchItemDecoration(mLaunchAdapter))

        refreshLayout.setOnRefreshListener {
            mVm.refresh()
        }

        sortButton.setOnClickListener {
            val sortPopupWindow =
                OptionPopupWindow(
                    this,
                    sortButton.width,
                    listOf(getString(R.string.sort_by_year_asc), getString(R.string.sort_by_year_desc), getString(R.string.sort_by_name))
                ) {
                    when (it) {
                        0 -> mVm.sort(MainViewModel.SortOption.YEAR_ASC)
                        1 -> mVm.sort(MainViewModel.SortOption.YEAR_DESC)
                        2 -> mVm.sort(MainViewModel.SortOption.NAME)
                    }
                }

            PopupWindowCompat.showAsDropDown(
                sortPopupWindow,
                sortButton,
                0,
                0,
                Gravity.BOTTOM
            )
        }

        filterButton.setOnClickListener {
            val filterPopupWindow =
                OptionPopupWindow(
                    this,
                    filterButton.width,
                    listOf(getString(R.string.filter_all), getString(R.string.filter_success), getString(R.string.filter_failure), getString(R.string.filter_upcoming))
                ) {
                    when (it) {
                        0 -> mVm.filter(MainViewModel.FilterOption.ALL)
                        1 -> mVm.filter(MainViewModel.FilterOption.SUCCESS)
                        2 -> mVm.filter(MainViewModel.FilterOption.FAILURE)
                        3 -> mVm.filter(MainViewModel.FilterOption.UPCOMING)
                    }
                }

            PopupWindowCompat.showAsDropDown(
                filterPopupWindow,
                filterButton,
                0,
                0,
                Gravity.BOTTOM
            )
        }
    }

    private fun observe() {
        mVm.error.observe(this, Observer {
            when (it) {
                MainViewModel.ViewModelError.REFRESH_FAILED -> {
                    toast(R.string.error_refresh)
                }
                else -> {
                    toast(R.string.error_general)
                }
            }
        })
        mVm.isRefreshing.observe(this, Observer {
            refreshLayout.isRefreshing = it
        })
        mVm.launches.observe(this, Observer {
            mLaunchAdapter.updateLaunches(it, mVm.sortOption.value!!)
        })
        mVm.sortOption.observe(this, Observer {
            val textRes = when (it) {
                MainViewModel.SortOption.YEAR_ASC -> R.string.sort_by_year_asc
                MainViewModel.SortOption.YEAR_DESC -> R.string.sort_by_year_desc
                MainViewModel.SortOption.NAME -> R.string.sort_by_name
            }

            sortButton.text = getString(textRes)
        })
        mVm.filterOption.observe(this, Observer {
            val textRes = when (it) {
                MainViewModel.FilterOption.ALL -> R.string.filter_all
                MainViewModel.FilterOption.SUCCESS -> R.string.filter_success
                MainViewModel.FilterOption.FAILURE -> R.string.filter_failure
                MainViewModel.FilterOption.UPCOMING -> R.string.filter_upcoming
            }

            filterButton.text = getString(textRes)
        })
    }

    private fun onLaunchClick(launchModel: LaunchModel) {
        goToActivity<LaunchDetailActivity>(bundleOf(
            LaunchDetailActivity.LAUNCH_MODEL_KEY to launchModel
        ))
    }
}
