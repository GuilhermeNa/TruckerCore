package com.example.truckercore.view.fragments.preparing_ambient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore._utils.expressions.navigateToActivity
import com.example.truckercore.databinding.FragmentPreparingAmbientBinding
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.dialogs.LoadingDialog
import com.example.truckercore.view.ui_error.UiError
import com.example.truckercore.view_model.view_models.preparing_ambient.PreparingAmbientUiState
import com.example.truckercore.view_model.view_models.preparing_ambient.PreparingAmbientViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PreparingAmbientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreparingAmbientFragment : Fragment() {

    private var _binding: FragmentPreparingAmbientBinding? = null
    private val binding get() = _binding!!

    private val dialog = LoadingDialog(requireContext())

    private val viewModel: PreparingAmbientViewModel by viewModel()

    private val flavorService: FlavorService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUiStateManager()
    }

    private fun setUiStateManager() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        PreparingAmbientUiState.LoadingSession -> dialog.show()

                        PreparingAmbientUiState.Success -> {
                            val intent = flavorService.enterSystemIntent(requireContext())
                            navigateToActivity(intent, true)
                        }

                        is PreparingAmbientUiState.UiError -> {
                            val intent = NotificationActivity.newInstance(
                                context = requireContext(),
                                title = UiError.Critical().title,
                                message = UiError.Critical().message
                            )
                            navigateToActivity(intent, true)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreparingAmbientBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog.dismissIfShowing()
        _binding = null
    }

}