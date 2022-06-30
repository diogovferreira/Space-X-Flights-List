package com.mindera.rocketscience.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mindera.rocketscience.databinding.FilterPopupBinding
import com.mindera.rocketscience.ui.filter.item.FilterItem
import com.mindera.rocketscience.ui.list.ListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilterDialogFragment : DialogFragment() {

    private lateinit var binding: FilterPopupBinding

    private val viewModel: ListViewModel by viewModels({ requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilters()
    }

    private fun setupFilters() {
        with(binding) {
            applyFiltersButton.setOnClickListener {
                viewModel.selectItem(
                    FilterItem(
                        switchAsc.isChecked,
                        switchDesc.isChecked,
                        switchLaunchSuccess.isChecked,
                        switchLaunchFail.isChecked,
                        rangeSlider.values
                    )
                )
                dialog?.dismiss()
            }

            switchAsc.setOnClickListener {
                if (switchAsc.isChecked) {
                    switchDesc.isChecked = false
                }
            }
            switchDesc.setOnClickListener {
                if (switchDesc.isChecked) {
                    switchAsc.isChecked = false
                }
            }

            switchLaunchFail.setOnClickListener {
                if (switchLaunchFail.isChecked) {
                    switchLaunchSuccess.isChecked = false
                }
            }

            switchLaunchSuccess.setOnClickListener {
                if (switchLaunchSuccess.isChecked) {
                    switchLaunchFail.isChecked = false
                }
            }

            rangeSlider.apply {
                valueFrom = viewModel.minValue
                valueTo = viewModel.maxValue
                values = listOf(viewModel.minValue, viewModel.maxValue)
                stepSize = 1F
            }
        }
    }

}