package com.mindera.rocketscience.ui.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.R
import com.mindera.rocketscience.data.api.ApiResponse
import com.mindera.rocketscience.databinding.ListFragmentBinding
import com.mindera.rocketscience.extensions.convertToLaunchCardModel
import com.mindera.rocketscience.extensions.replaceTitle
import com.mindera.rocketscience.ui.filter.FilterDialogFragment
import com.mindera.rocketscience.ui.filter.item.FilterItem
import com.mindera.rocketscience.ui.list.adapter.LaunchesAdapter
import com.mindera.rocketscience.ui.list.adapter.item.LaunchCardItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.list_fragment), LaunchesAdapter.Actions {


    private lateinit var binding: ListFragmentBinding

    private lateinit var dialog: DialogFragment
    private lateinit var launchesList: List<LaunchCardItem>
    private lateinit var filteredList: List<LaunchCardItem>
    private lateinit var yearsList: MutableList<Int>

    private val mainViewModel: ListViewModel by viewModels()

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.fetchCompanyInfoResponse()
        mainViewModel.fetchLaunchesResponse()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        listeners()
    }

    private fun setObservers() {
        mainViewModel.launchesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.ApiResult.Error -> {
                    it.message?.let { message -> showError(message) }
                }
                is ApiResponse.ApiResult.Success -> {
                    if (it.data != null) {
                        val listOfItems = it.data.convertToLaunchCardModel()
                        loadRecyclerView(listOfItems)
                        launchesList = listOfItems
                        setDateRangeSliderLimit(listOfItems)
                    }
                }
            }
        }

        mainViewModel.companyInfoResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.ApiResult.Error -> {
                    it.message?.let { message -> showError(message) }
                }
                is ApiResponse.ApiResult.Success -> {
                    if (it.data != null) {
                        val companyInfo = getString(R.string.company_info).replaceTitle(it.data)
                        binding.companyInfo.text = companyInfo
                    }
                }
            }
        }

        mainViewModel.selectedItem.observe(viewLifecycleOwner) { filterItem ->
            setFilter(filterItem)
        }
    }

    private fun showError(errorMessage: String) {
        binding.errorMessage.isVisible = true
        binding.launchRecyclerView.isVisible = false
        binding.errorMessage.text = errorMessage
    }

    private fun setFilter(filterItem: FilterItem) {
        filteredList = emptyList()

        if (filterItem.asc && filterItem.successLaunches) {
            filteredList = launchesList.sortedBy { it.launchYear }.filter { it.launchSuccess }
                .filter { it.launchYear >= filterItem.dateRange[0] && it.launchYear <= filterItem.dateRange[1] }
        } else if (filterItem.asc && filterItem.failedLaunches) {
            filteredList = launchesList.sortedBy { it.launchYear }.filter { !it.launchSuccess }
                .filter { it.launchYear >= filterItem.dateRange[0] && it.launchYear <= filterItem.dateRange[1] }
        } else if (filterItem.desc && filterItem.successLaunches) {
            filteredList =
                launchesList.sortedByDescending { it.launchYear }.filter { it.launchSuccess }
                    .filter { it.launchYear >= filterItem.dateRange[0] && it.launchYear <= filterItem.dateRange[1] }
        } else if (filterItem.desc && filterItem.failedLaunches) {
            filteredList =
                launchesList.sortedByDescending { it.launchYear }.filter { !it.launchSuccess }
                    .filter { it.launchYear >= filterItem.dateRange[0] && it.launchYear <= filterItem.dateRange[1] }
        } else if (filterItem.asc) {
            filteredList = launchesList.sortedBy { it.launchYear }
        } else if (filterItem.desc) {
            filteredList = launchesList.sortedByDescending { it.launchYear }
        } else if (filterItem.failedLaunches) {
            filteredList = launchesList.filter { !it.launchSuccess }
        } else if (filterItem.successLaunches) {
            filteredList = launchesList.filter { it.launchSuccess }
        } else {
            filteredList =
                launchesList.filter { it.launchYear >= filterItem.dateRange[0] && it.launchYear <= filterItem.dateRange[1] }
        }

        loadRecyclerView(filteredList)
    }

    private fun setDateRangeSliderLimit(list: List<LaunchCardItem>) {
        yearsList = mutableListOf()
        list.forEach { item ->
            yearsList.add(item.launchYear)
        }
        mainViewModel.minValue = yearsList.distinct()[0].toFloat()
        mainViewModel.maxValue = yearsList.distinct()[yearsList.distinct().size - 1].toFloat()
    }


    private fun loadRecyclerView(launches: List<LaunchCardItem>) {
        binding.launchRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.launchRecyclerView.adapter = context?.let { LaunchesAdapter(launches, it, this) }!!
    }


    private fun listeners() {
        binding.filterButton.setOnClickListener {
            dialog = FilterDialogFragment()
            dialog.show(this.childFragmentManager, FilterDialogFragment::class.java.simpleName)
        }
    }


    override fun onYoutubeClicked(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onWikipediaClicked(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}