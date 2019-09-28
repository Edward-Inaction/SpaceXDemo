package life.inaction.assignment.spacexdemo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_launch.view.*
import life.inaction.assignment.spacexdemo.R
import life.inaction.assignment.spacexdemo.extension.toDateString
import life.inaction.assignment.spacexdemo.model.LaunchModel

class LaunchAdapter(val onClick: (LaunchModel) -> Unit) : RecyclerView.Adapter<LaunchAdapter.LaunchViewHolder>() {

    private var mLaunches: List<LaunchModel> = emptyList()
    private var mSortOption: MainViewModel.SortOption = MainViewModel.SortOption.YEAR_ASC
    private var mHeaders: List<String> = emptyList()

    fun updateLaunches(newLaunches: List<LaunchModel>, sortOption: MainViewModel.SortOption) {
        mLaunches = newLaunches
        mSortOption = sortOption

        generateHeaders()

        notifyDataSetChanged()
    }

    fun getHeaderText(position: Int): String {
        return mHeaders[position]
    }

    private fun generateHeaders() {
        var lastHeaderName = ""
        mHeaders = mLaunches.map {
            val headerName = when (mSortOption) {
                MainViewModel.SortOption.YEAR_ASC, MainViewModel.SortOption.YEAR_DESC -> it.launchYear
                MainViewModel.SortOption.NAME -> it.missionName.toUpperCase().first().toString()
            }
            if (headerName != lastHeaderName) {
                lastHeaderName = headerName
                lastHeaderName
            } else {
                ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_launch, parent, false)

        return LaunchViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val launch = mLaunches[position]
        holder.update(launch)
        holder.itemView.setOnClickListener {
            onClick(launch)
        }
    }

    override fun getItemCount(): Int {
        return mLaunches.size
    }

    class LaunchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun update(launchModel: LaunchModel) {
            with(containerView) {
                nameText.text = launchModel.missionName
                locationText.text = launchModel.launchSite.name

                when (launchModel.success) {
                    true -> {
                        successText.visibility = View.VISIBLE
                        successText.setTextAppearance(R.style.TextAppearance_AppCompat_Small_Success)
                        successText.text = context.getString(R.string.launch_success)
                    }
                    false -> {
                        successText.visibility = View.VISIBLE
                        successText.setTextAppearance(R.style.TextAppearance_AppCompat_Small_Failure)
                        successText.text = context.getString(R.string.launch_failure)
                    }
                    null -> {
                        if (launchModel.upcoming) {
                            successText.visibility = View.VISIBLE
                            successText.setTextAppearance(R.style.TextAppearance_AppCompat_Small_Upcoming)
                            successText.text = context.getString(R.string.launch_upcoming)
                        } else {
                            successText.visibility = View.INVISIBLE
                        }
                    }
                }

                dateText.text = launchModel.launchDate.toDateString()
            }
        }
    }
}
