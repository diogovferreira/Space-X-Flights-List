package com.mindera.rocketscience.ui.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.LaunchInfoCardBinding
import com.mindera.rocketscience.ui.list.adapter.item.LaunchCardItem

class LaunchesAdapter(
    private var launchesDataSet: List<LaunchCardItem>,
    private val context: Context,
    private val actions: Actions
) : RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        val itemBinding =
            LaunchInfoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchesViewHolder(itemBinding, actions)
    }

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val launchItem: LaunchCardItem = launchesDataSet[position]
        Glide.get(context)
        holder.bind(launchItem, context)
    }

    override fun getItemCount(): Int {
        return launchesDataSet.size
    }

    class LaunchesViewHolder(
        private val binding: LaunchInfoCardBinding,
        private val actions: Actions
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(launch: LaunchCardItem, context: Context) {
            with(binding) {
                missionName.text = launch.missionName
                missionDate.text = launch.dateTime
                rocketName.text = launch.rocketName
                if (Integer.valueOf(launch.days) < 0) {
                    labelDays.setText(R.string.label_days_since)
                    numberOfDays.text = launch.days.replace("-", "")
                } else {
                    labelDays.setText(R.string.label_days_from)
                    numberOfDays.text = launch.days
                }

                Glide.with(context)
                    .load(launch.image)
                    .into(missionImage)

                if (launch.launchSuccess) {
                    launchIcon.setImageResource(R.drawable.ic_success)
                } else {
                    launchIcon.setImageResource(R.drawable.ic_failure)
                }


                youtubeImage.setOnClickListener {
                    if (!launch.youtubeLink.isNullOrEmpty())
                        actions.onYoutubeClicked(launch.youtubeLink)
                }
                wikipediaImage.setOnClickListener {
                    if (!launch.wikipediaLink.isNullOrEmpty())
                        actions.onWikipediaClicked(launch.wikipediaLink)
                }
            }
        }
    }

    interface Actions {
        fun onYoutubeClicked(url: String)
        fun onWikipediaClicked(url: String)
    }
}