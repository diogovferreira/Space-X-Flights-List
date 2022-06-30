package com.mindera.rocketscience.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.data.api.ApiResponse
import com.mindera.rocketscience.domain.model.CompanyInfoModel
import com.mindera.rocketscience.domain.model.MissionModel
import com.mindera.rocketscience.domain.usecase.FetchCompanyInfoUseCase
import com.mindera.rocketscience.domain.usecase.FetchLaunchesUseCase
import com.mindera.rocketscience.ui.filter.item.FilterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListViewModel
@Inject
constructor(
    private val fetchLaunchesUseCase: FetchLaunchesUseCase,
    private val fetchCompanyInfoUseCase: FetchCompanyInfoUseCase
) :
    ViewModel() {

    val launchesResponse = MutableLiveData<ApiResponse.ApiResult<List<MissionModel>>>()
    val companyInfoResponse = MutableLiveData<ApiResponse.ApiResult<CompanyInfoModel>>()

    private val mutableSelectedItem = MutableLiveData<FilterItem>()
    val selectedItem: LiveData<FilterItem> get() = mutableSelectedItem

    var maxValue = 0F;
    var minValue = 0F;

    fun selectItem(item: FilterItem) {
        mutableSelectedItem.value = item
    }

    fun fetchLaunchesResponse() {
        viewModelScope.launch {
            val result = fetchLaunchesUseCase()
            launchesResponse.postValue(result!!)
        }
    }

    fun fetchCompanyInfoResponse() {
        viewModelScope.launch {
            val result = fetchCompanyInfoUseCase()
            companyInfoResponse.postValue(result!!)
        }
    }

}


