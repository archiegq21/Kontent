package com.quibbly.kontent.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quibbly.common.search.DashboardStore

class DashboardViewModel : ViewModel() {

    val dashboardStore = DashboardStore(viewModelScope)

}