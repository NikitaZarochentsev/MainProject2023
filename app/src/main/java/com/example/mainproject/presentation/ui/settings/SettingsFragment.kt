package com.example.mainproject.presentation.ui.settings

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentSettingsBinding
import com.example.mainproject.domain.models.Profile
import com.example.mainproject.presentation.ui.settings.occupations.OccupationsBottomFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()
    private val photoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
            if (result != null) {
                val imageStream = requireContext().contentResolver.openInputStream(result)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                binding.imageSettings.load(selectedImage) {
                    transformations(CircleCropTransformation())
                }
                viewModel.setImageUri(selectedImage)
            }
        }

    companion object {
        private const val IMAGE_URL_ARGUMENT = "IMAGE_URL_ARGUMENT"
        private const val NAME_ARGUMENT = "NAME_ARGUMENT"
        private const val SURNAME_ARGUMENT = "SURNAME_ARGUMENT"
        private const val OCCUPATION_ARGUMENT = "OCCUPATION_ARGUMENT"

        fun newInstance(
            imageUrl: String,
            name: String,
            surname: String,
            occupation: String,
        ): SettingsFragment {
            val arguments = Bundle()
            arguments.putString(IMAGE_URL_ARGUMENT, imageUrl)
            arguments.putString(NAME_ARGUMENT, name)
            arguments.putString(SURNAME_ARGUMENT, surname)
            arguments.putString(OCCUPATION_ARGUMENT, occupation)
            val fragment = SettingsFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarSettings.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val imageUrl = requireArguments().getString(IMAGE_URL_ARGUMENT)
        if (imageUrl != null) {
            viewModel.getAvatar(imageUrl)
        }

        binding.imageSettings.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val name = requireArguments().getString(NAME_ARGUMENT)
        if (name != null) {
            binding.textNameSettings.setText(name)
        }

        val surname = requireArguments().getString(SURNAME_ARGUMENT)
        if (surname != null) {
            binding.textSurnameSettings.setText(surname)
        }

        binding.textSurnameSettings.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                true
            } else {
                false
            }
        }

        val occupation = requireArguments().getString(OCCUPATION_ARGUMENT)
        if (occupation != null) {
            binding.textOccupationSettings.setText(occupation)
        }

        binding.textOccupationSettings.setOnClickListener {
            val occupationsBottomFragment = OccupationsBottomFragment(
                listOf(
                    "Разработчик",
                    "Тестировщик",
                    "Менеджер",
                    "Другое"
                )
            )
            occupationsBottomFragment.show(parentFragmentManager, null)
        }

        parentFragmentManager.setFragmentResultListener(
            OccupationsBottomFragment.REQUEST_KEY,
            this
        ) { _, result ->
            binding.textOccupationSettings.setText(result.getString(OccupationsBottomFragment.RESPONSE_KEY))
        }

        binding.progressButtonSettings.setOnClickListener {
            viewModel.changeProfile(
                Profile(
                    binding.textNameSettings.text.toString(),
                    binding.textSurnameSettings.text.toString(),
                    binding.textOccupationSettings.text.toString(),
                    ""
                )
            )
        }

        viewModel.settingsUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is SettingsUiState.Loading -> {
                    binding.progressButtonSettings.isLoading = true
                    binding.layoutNameSettings.error = null
                    binding.layoutSurameSettings.error = null
                }
                is SettingsUiState.Success -> {
                    binding.progressButtonSettings.isLoading = false
                    Toast.makeText(requireContext(), "Готово", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                is SettingsUiState.Error -> {
                    binding.progressButtonSettings.isLoading = false
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                }
                is SettingsUiState.EmptyName -> {
                    binding.progressButtonSettings.isLoading = false
                    binding.layoutNameSettings.error = getString(R.string.settings_error_empty)
                }
                is SettingsUiState.EmptySurname -> {
                    binding.progressButtonSettings.isLoading = false
                    binding.layoutSurameSettings.error = getString(R.string.settings_error_empty)
                }
            }
        }

        viewModel.avatar.observe(this as LifecycleOwner) { image ->
            binding.imageSettings.load(image) {
                transformations(CircleCropTransformation())
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.progressButtonSettings) { progressButtonView, insets ->
            val defaultMarginBottom = (16 * Resources.getSystem().displayMetrics.density).toInt()
            if (progressButtonView.marginBottom == defaultMarginBottom) {
                val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
                val params = progressButtonView.layoutParams as ConstraintLayout.LayoutParams
                params.setMargins(
                    progressButtonView.marginLeft,
                    progressButtonView.marginTop,
                    progressButtonView.marginRight,
                    progressButtonView.marginBottom + navigationInsets.bottom
                )
                progressButtonView.layoutParams = params
            }
            insets
        }
    }
}