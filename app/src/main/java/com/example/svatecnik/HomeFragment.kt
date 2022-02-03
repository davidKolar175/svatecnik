package com.example.svatecnik

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var currentName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.svatekResponse.observe(this) { response ->
            if (response == null) {
                currentName = ""
                today_name_text.text = "Nepodařilo se nalézt dané jméno!"
            }
            else {
                currentName = response.name
                today_name_text.text = formatDisplayedName(response.name, response.date)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        change_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dpd = DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->
                    val sdf = SimpleDateFormat("ddMM")
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val dateString = sdf.format(calendar.time)
                    today_name_text.text = "Načítání..."
                    viewModel.getSvatekByDate(dateString)
                    name_input.text.clear()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show()
        }

        name_input.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                today_name_text.text = "Načítání..."
                viewModel.getSvatekByName(v.text.toString())
            }
            false
        }

        star_button.setOnClickListener {
            if (currentName != "")
                viewModel.addNewFavouriteName(currentName)
        }
    }

    private fun formatDisplayedName(name: String, date: String): String {
        val calendar = Calendar.getInstance()
        val month = "${date[2]}${date[3]}".toInt()
        val day = "${date[0]}${date[1]}".toInt()
        calendar.set(calendar.get(Calendar.YEAR), month - 1, day)
        val sdf = SimpleDateFormat("d. MMMM ")
        return "${sdf.format(calendar.time)}\nslaví svátek\n$name";
    }
}