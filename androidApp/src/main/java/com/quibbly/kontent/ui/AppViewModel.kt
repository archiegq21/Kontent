package com.quibbly.kontent.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quibbly.common.search.DashboardStateStore

class AppViewModel : ViewModel() {

    val dashboardStore = DashboardStateStore(viewModelScope)

}