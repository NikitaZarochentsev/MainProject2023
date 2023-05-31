package com.example.mainproject.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentProfileBinding
import com.example.mainproject.presentation.ui.orders.OrdersFragment
import com.example.mainproject.presentation.ui.settings.SettingsFragment
import com.example.mainproject.presentation.ui.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.getProfile()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVersionApplication()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainerViewMain, OrdersFragment())
                addToBackStack(null)
            }
        }

        binding.buttonSignOutProfile.setOnClickListener {
            val signOutDialog = SignOutDialog {
                viewModel.signOut()
            }
            signOutDialog.show(parentFragmentManager, null)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.textViewVersionProfile) { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBarInsets.bottom)
            insets
        }

        viewModel.profileUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is ProfileUiState.Default -> {
                    binding.imageViewProfile.load(R.drawable.ic_logo) {
                        transformations(CircleCropTransformation())
                    }
                    binding.textViewTitleProfile.text = getString(
                        R.string.text_name_profile,
                        state.profile.name,
                        state.profile.surname
                    )
                    binding.textViewSubtitleProfile.text = state.profile.occupation

                    binding.buttonSettingsProfile.setOnClickListener {
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(
                                R.id.fragmentContainerViewMain,
                                SettingsFragment.newInstance(
                                    state.profile.avatarId,
                                    state.profile.name,
                                    state.profile.surname,
                                    state.profile.occupation
                                )
                            )
                            addToBackStack(null)
                        }
                    }
                }
                is ProfileUiState.Out -> {
                    parentFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerViewMain, SignInFragment())
                    }
                }
            }
        }

        viewModel.avatar.observe(this as LifecycleOwner) { avatar ->
            binding.imageViewProfile.load(avatar) {
                placeholder(R.drawable.ic_logo)
                error(R.drawable.ic_logo)
                transformations(CircleCropTransformation())
            }
        }

        viewModel.versionApp.observe(this as LifecycleOwner) { version ->
            binding.textViewVersionProfile.text =
                getString(R.string.text_application_version, version.first, version.second)
        }
    }
}