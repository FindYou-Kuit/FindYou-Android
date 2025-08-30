package com.example.findu.presentation.ui.report.missing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.findu.R
import com.example.findu.databinding.FragmentNewMissingReportBinding
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.ui.home.composeview.HomeScreen
import com.example.findu.presentation.ui.home.viewmodel.HomeUiEffect
import com.example.findu.presentation.ui.home.viewmodel.HomeUiEvent

class NewMissingReportFragment : Fragment() {

    private var _binding: FragmentNewMissingReportBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewMissingReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.missingReportComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

            }
        }
    }

}