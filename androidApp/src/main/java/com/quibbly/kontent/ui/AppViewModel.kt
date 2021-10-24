package com.quibbly.kontent.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quibbly.common.search.DashboardStore

class AppViewModel : ViewModel() {

    val dashboardStore = DashboardStore(viewModelScope)

}