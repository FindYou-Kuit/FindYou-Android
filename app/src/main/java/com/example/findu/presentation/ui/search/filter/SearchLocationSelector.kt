package com.example.findu.presentation.ui.search.filter

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.presentation.ui.search.adapter.SearchFilterLocationRVAdapter
import com.example.findu.presentation.ui.search.model.LocationData

class SearchLocationSelector(
    private val binding: FragmentSearchFilterBinding,
) {
    private var selectedCity: String? = null
    private var selectedDistrict: String? = null
    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private var districtAdapter: SearchFilterLocationRVAdapter? = null

    fun init() = with(binding) {
        val cities = listOf("전체") + LocationData.locationMap.keys.toList()

        cityAdapter = SearchFilterLocationRVAdapter(cities, selectedCity) { newCity ->
            selectedCity = newCity
            actvSearchFilterCity.setText(newCity)

            if (newCity == "전체") {
                selectedDistrict = null
                actvSearchFilterDistrict.isEnabled = false
                actvSearchFilterDistrict.setText("")
                setDistrictList(listOf("전체"))
            } else {
                actvSearchFilterDistrict.isEnabled = true
                selectedDistrict = null
                actvSearchFilterDistrict.setText("")
                val districts = (LocationData.locationMap[newCity] ?: emptyList())
                setDistrictList(districts)
            }
        }

        rvSearchFilterCity.layoutManager = LinearLayoutManager(root.context)
        rvSearchFilterCity.adapter = cityAdapter

        setDistrictList(listOf("전체"))
        actvSearchFilterDistrict.isEnabled = false

        actvSearchFilterCity.setOnClickListener {
            toggleRecyclerView(flFilterCityContainer, actvSearchFilterCity, ivCityArrow)
        }
        actvSearchFilterDistrict.setOnClickListener {
            if (actvSearchFilterDistrict.isEnabled) {
                toggleRecyclerView(
                    flFilterDistrictContainer,
                    actvSearchFilterDistrict,
                    ivDistrictArrow
                )
            }
        }
    }

    private fun setDistrictList(items: List<String>) = with(binding) {
        districtAdapter = SearchFilterLocationRVAdapter(items, selectedDistrict) { newDistrict ->
            selectedDistrict = newDistrict
            actvSearchFilterDistrict.setText(newDistrict)
        }
        rvSearchFilterDistrict.layoutManager = LinearLayoutManager(root.context)
        rvSearchFilterDistrict.adapter = districtAdapter
    }

    private fun toggleRecyclerView(container: View, trigger: View, arrow: ImageView) =
        with(binding) {
            val open = container.isGone

            val start = trigger.paddingStart
            val top = trigger.paddingTop
            val end = trigger.paddingEnd
            val bottom = trigger.paddingBottom

            flFilterCityContainer.visibility = View.GONE
            flFilterDistrictContainer.visibility = View.GONE
            actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)
            actvSearchFilterDistrict.setBackgroundResource(R.drawable.bg_search_radius_8)
            ivCityArrow.rotation = 0f
            ivDistrictArrow.rotation = 0f

            if (open) {
                container.visibility = View.VISIBLE
                trigger.setBackgroundResource(R.drawable.bg_search_radius_8_up)
                arrow.rotation = 180f
            }

            trigger.setPadding(start, top, end, bottom)
        }

    fun getSelected(): String? {
        val city = selectedCity?.takeUnless { it.isBlank() || it == "전체" }
        val district = selectedDistrict?.takeUnless { it.isBlank() || it == "전체" }
        return when {
            city == null -> null
            district == null -> city
            else -> "$city $district"
        }
    }

    fun reset() = with(binding) {
        selectedCity = null
        selectedDistrict = null
        actvSearchFilterCity.setText("")
        actvSearchFilterDistrict.setText("")
        actvSearchFilterDistrict.isEnabled = false
        flFilterCityContainer.visibility = View.GONE
        flFilterDistrictContainer.visibility = View.GONE
        actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)
        actvSearchFilterDistrict.setBackgroundResource(R.drawable.bg_search_radius_8)
        ivCityArrow.rotation = 0f
        ivDistrictArrow.rotation = 0f
        setDistrictList(listOf("전체"))
    }
}