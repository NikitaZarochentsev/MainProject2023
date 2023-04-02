package com.example.mainproject.presentation.ui.signin

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentSignInBinding
import com.example.mainproject.presentation.ui.catalog.CatalogFragment
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signInUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                SignInState.Loading -> {
                    binding.progressButtonSignIn.isLoading = true
                    binding.layoutLoginSignIn.error = null
                    binding.layoutPasswordSignIn.error = null
                }
                SignInState.LoginError -> {
                    binding.progressButtonSignIn.isLoading = false
                    binding.layoutLoginSignIn.error = "Поле заполнено неверно"
                }
                SignInState.PasswordError -> {
                    binding.progressButtonSignIn.isLoading = false
                    binding.layoutPasswordSignIn.error = "Поле заполнено неверно"
                }
                SignInState.Error -> {
                    binding.progressButtonSignIn.isLoading = false
                    Toast.makeText(view.context, "Непредвиденная ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.token.observe(this as LifecycleOwner) {
            lifecycle.coroutineScope.launch {
                delay(3000)
                withContext(Dispatchers.Main) {
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerViewMain, CatalogFragment())
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