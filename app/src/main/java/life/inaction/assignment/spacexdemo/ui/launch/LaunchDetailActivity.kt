package life.inaction.assignment.spacexdemo.ui.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import life.inaction.assignment.spacexdemo.R
import life.inaction.assignment.spacexdemo.databinding.ActivityLaunchDetailBinding
import life.inaction.assignment.spacexdemo.extension.openUrl
import life.inaction.assignment.spacexdemo.model.LaunchModel
import life.inaction.assignment.spacexdemo.ui.common.LoadingDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchDetailActivity : AppCompatActivity(), LaunchDetailViewModel.ViewModelNavigator {

    companion object {
        const val LAUNCH_MODEL_KEY = "launch model"
    }

    private val mVm: LaunchDetailViewModel by viewModel()
    private lateinit var mBinding: ActivityLaunchDetailBinding
    private val mLaunchModel: LaunchModel by lazy {
        intent.getSerializableExtra(LAUNCH_MODEL_KEY) as LaunchModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_launch_detail)

        mVm.setData(mLaunchModel.rocket.id)
        mVm.setNavigator(this)

        mBinding.launch = mLaunchModel
        mBinding.vm = mVm
        mBinding.lifecycleOwner = this

        observe()
    }

    private fun observe() {
        mVm.isLoading.observe(this, Observer {
            if (it) {
                LoadingDialogFragment().show(supportFragmentManager, LoadingDialogFragment.TAG)
            } else {
                supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG)?.let { fragment ->
                    (fragment as LoadingDialogFragment).dismiss()
                }
            }
        })
        mVm.error.observe(this, Observer {
            when (it) {
                LaunchDetailViewModel.ViewModelError.LOAD_FAILED -> {
                    AlertDialog.Builder(this)
                        .setMessage(R.string.fetch_rocket_fail)
                        .setPositiveButton(R.string.option_try_again) { _, _ ->
                            mVm.load()
                        }
                        .setNegativeButton(R.string.option_cancel, null)
                        .create()
                        .show()
                }
            }
        })
    }

    override fun goToWikipedia(url: String) {
        openUrl(url)
    }
}
