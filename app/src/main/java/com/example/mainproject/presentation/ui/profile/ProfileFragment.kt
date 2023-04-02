package com.example.mainproject.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentProfileBinding
import com.example.mainproject.presentation.ui.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarProfile.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonOrdersProfile.setOnClickListener {
            Toast.makeText(view.context, binding.buttonOrdersProfile.text, Toast.LENGTH_SHORT)
                .show()
        }

        binding.buttonSettingsProfile.setOnClickListener {
            Toast.makeText(view.context, binding.buttonSettingsProfile.text, Toast.LENGTH_SHORT)
                .show()
        }

        binding.buttonSignOutProfile.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainerViewMain, SignInFragment())
            }
        }

        viewModel.getProfile()

        viewModel.profile.observe(this as LifecycleOwner) { profile ->
            binding.imageViewProfile.load(profile.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            binding.textViewTitleProfile.text = "${profile.name} ${profile.surname}"
            binding.textViewSubtitleProfile.text = profile.occupation
        }

        binding.textViewVersionProfile.text = viewModel.getVersionApplication()
    }
}