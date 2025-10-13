package com.example.findu.presentation.ui.search.filter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.presentation.ui.search.adapter.SearchBreedRVAdapter
import com.google.android.material.chip.Chip

class SearchBreedSelector(
    private val binding: FragmentSearchFilterBinding
) {
    private val selectedList = mutableListOf<String>()
    private val maxBreedCount = 10
    private var adapter: SearchBreedRVAdapter? = null
    private var suppressWatcher = false
    private var isDropdownOpen = false

    fun init() = with(binding) {
        rvSearchFilterBreed.layoutManager = LinearLayoutManager(root.context)
        tvSearchFilterBreedCount.text =
            root.context.getString(R.string.search_bottom_sheet_breed_count, 0)
        cgSelectedBreeds.removeAllViews()

        setBreedFieldEnabled(false)

        actvSearchFilterBreed.setOnClickListener {
            if (!actvSearchFilterBreed.isEnabled || adapter == null) return@setOnClickListener
            hideKeyboard(it)
            flFilterCityContainer.isGone = true
            flFilterDistrictContainer.isGone = true

            adapter?.filter?.filter(token())
            toggleDropdown(!flFilterBreedContainer.isVisible)
        }

        actvSearchFilterBreed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (suppressWatcher || !actvSearchFilterBreed.isEnabled || adapter == null) {
                    toggleDropdown(false)
                    return
                }
                val query = token()
                adapter?.filter?.filter(query)
                toggleDropdown(query.isNotEmpty())
            }
        })
    }

    private fun token(): String =
        binding.actvSearchFilterBreed.text?.toString().orEmpty().substringAfterLast(",").trim()

    private fun toggleDropdown(open: Boolean) = with(binding) {
        flFilterBreedContainer.isVisible = open && (adapter?.itemCount ?: 0) > 0
        actvSearchFilterBreed.setBackgroundResource(
            if (open) R.drawable.bg_search_radius_8_up else R.drawable.bg_search_radius_8
        )
        ivBreedArrow.animate().rotation(if (open) 180f else 0f).setDuration(150).start()
        isDropdownOpen = open
    }

    private fun hideKeyboard(view: android.view.View) {
        val imm =
            view.context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setBreeds(breeds: List<String>) = with(binding) {
        if (breeds.isEmpty()) {
            selectedList.clear()
            updateChips()
            setBreedFieldEnabled(false)
            flFilterBreedContainer.isGone = true
            adapter = null
            return@with
        }

        selectedList.retainAll(breeds)
        updateChips()

        adapter = SearchBreedRVAdapter(
            allItems = breeds,
            isSelected = { selectedList.contains(it) },
            onPick = { picked ->
                if (picked in selectedList) removeBreed(picked) else addBreed(picked)
                adapter?.refreshSelections()
                toggleDropdown(true)
            }
        )

        rvSearchFilterBreed.adapter = adapter
        adapter?.filter?.filter(token())
    }

    private fun addBreed(breed: String) {
        if (selectedList.contains(breed)) return
        if (selectedList.size >= maxBreedCount) {
            Toast.makeText(binding.root.context, "최대 10개까지 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            return
        }
        selectedList.add(breed)
        updateChips()
    }

    private fun removeBreed(breed: String) {
        selectedList.remove(breed)
        updateChips()
        adapter?.refreshSelections()
        if (isDropdownOpen) toggleDropdown(true)
    }

    private fun updateChips() = with(binding) {
        tvSearchFilterBreedCount.text =
            root.context.getString(R.string.search_bottom_sheet_breed_count, selectedList.size)
        cgSelectedBreeds.removeAllViews()
        val inflater = LayoutInflater.from(root.context)

        selectedList.forEach { b ->
            val chip =
                inflater.inflate(R.layout.item_search_breed_chip, cgSelectedBreeds, false) as Chip
            chip.text = b
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener { removeBreed(b) }
            cgSelectedBreeds.addView(chip)
        }

        suppressWatcher = true
        actvSearchFilterBreed.setText(selectedList.joinToString(", "))
        actvSearchFilterBreed.setSelection(actvSearchFilterBreed.text?.length ?: 0)
        suppressWatcher = false
    }

    fun setBreedFieldEnabled(enabled: Boolean) = with(binding) {
        actvSearchFilterBreed.isEnabled = enabled
        actvSearchFilterBreed.alpha = if (enabled) 1f else 0.5f
        if (!enabled) toggleDropdown(false)
    }

    fun getSelected(): List<String>? = selectedList.takeIf { it.isNotEmpty() }

    fun reset() = with(binding) {
        selectedList.clear()
        updateChips()
        actvSearchFilterBreed.setText("")
        actvSearchFilterBreed.hint = root.context.getString(R.string.search_filter_breed_hint)
        actvSearchFilterBreed.setBackgroundResource(R.drawable.bg_search_radius_8)
        flFilterBreedContainer.isGone = true
        setBreedFieldEnabled(false)
        adapter?.refreshSelections()
    }
}