package com.jayys.stashmap.feature.profile.viewmodel

import com.jayys.stashmap.base.BaseViewModel
import com.jayys.stashmap.core.common.local.LocalManager
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferenceStorage: SharedPreferenceStorage
): BaseViewModel() {

    val selectedLanguage = LocalManager.stashLanguage
}