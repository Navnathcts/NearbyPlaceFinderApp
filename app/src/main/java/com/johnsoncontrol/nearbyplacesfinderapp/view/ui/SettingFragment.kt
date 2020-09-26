package com.johnsoncontrol.nearbyplacesfinderapp.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.johnsoncontrol.nearbyplacesfinderapp.R
import com.johnsoncontrol.nearbyplacesfinderapp.utils.Constants
import com.johnsoncontrol.nearbyplacesfinderapp.utils.Constants.KEY_RADIUS_VALUE
import com.johnsoncontrol.nearbyplacesfinderapp.utils.Constants.KEY_SHOW_BY_MILES
import com.johnsoncontrol.nearbyplacesfinderapp.utils.SharedPrefUtility
import com.johnsoncontrol.nearbyplacesfinderapp.utils.showSelected
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rgDistanceDisplaySetting?.setOnCheckedChangeListener { radioGroup: RadioGroup, checkedRadioId: Int ->
            when (checkedRadioId) {
                R.id.rbByKm -> SharedPrefUtility.saveBooleanValue(
                    requireActivity(),
                    KEY_SHOW_BY_MILES, false
                )
                R.id.rbByMiles -> SharedPrefUtility.saveBooleanValue(
                    requireActivity(),
                    KEY_SHOW_BY_MILES,
                    true
                )
            }
        }

        distanceRadioGroup?.setOnCheckedChangeListener { radioGroup: RadioGroup, checkedRadioId: Int ->
            when (checkedRadioId) {
                R.id.rbRadius5Km -> SharedPrefUtility.saveStringValue(
                    requireActivity(),
                    KEY_RADIUS_VALUE,
                    Constants.RADIUS_5K
                )
                R.id.rbRadius10Km -> SharedPrefUtility.saveStringValue(
                    requireActivity(),
                    KEY_RADIUS_VALUE, Constants.RADIUS_10K
                )
                R.id.rbRadius15Km -> SharedPrefUtility.saveStringValue(
                    requireActivity(),
                    KEY_RADIUS_VALUE, Constants.RADIUS_15K
                )

            }
        }
        SharedPrefUtility.getStringValue(requireActivity(), KEY_RADIUS_VALUE).let {
            when {
                it.equals(Constants.RADIUS_10K) -> rbRadius10Km?.showSelected()
                it.equals(Constants.RADIUS_15K) -> rbRadius15Km?.showSelected()
                else -> rbRadius5Km?.showSelected()
            }
        }

        SharedPrefUtility.getBooleanValue(requireActivity(), KEY_SHOW_BY_MILES).let {
            when {
                it -> rbByMiles?.showSelected()
                else -> rbByKm?.showSelected()

            }
        }

    }
}
