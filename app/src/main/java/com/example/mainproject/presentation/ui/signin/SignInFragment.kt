package com.example.mainproject.presentation.ui.signin

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentSignInBinding
import com.example.mainproject.presentation.ui.catalog.CatalogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signInUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                SignInUiState.Loading -> {
                    binding.progressButtonSignIn.isLoading = true
                    binding.layoutLoginSignIn.error = null
                    binding.layoutPasswordSignIn.error = null
                }
                SignInUiState.LoginError -> {
                    binding.progressButtonSignIn.isLoading = false
                    binding.layoutLoginSignIn.error = getString(R.string.sign_in_input_error)
                }
                SignInUiState.PasswordError -> {
                    binding.progressButtonSignIn.isLoading = false
                    binding.layoutPasswordSignIn.error = getString(R.string.sign_in_input_error)
                }
                SignInUiState.Error -> {
                    binding.progressButtonSignIn.isLoading = false
                    Toast.makeText(view.context, R.string.sign_in_error, Toast.LENGTH_SHORT).show()
                }
                SignInUiState.Success -> {
                    lifecycle.coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                replace(R.id.fragmentContainerViewMain, CatalogFragment())
                            }
                        }
                    }
                }
            }
        }

        binding.progressButtonSignIn.setOnClickListener {
            navigateToCatalog()
        }

        binding.textPasswordSignIn.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                navigateToCatalog()
                true
            } else {
                false
            }
        }
    }

    private fun navigateToCatalog() {
        viewModel.signIn(
            binding.textLoginSignIn.text.toString(),
            binding.textPasswordSignIn.text.toString()
        )
    }
}