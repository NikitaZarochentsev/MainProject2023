package com.example.mainproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.commit
import com.example.mainproject.R
import com.example.mainproject.databinding.ActivityMainBinding
import com.example.mainproject.presentation.ui.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        with(supportFragmentManager) {
            if (findFragmentById(R.id.fragmentContainerViewMain) == null) {
                commit {
                    add(R.id.fragmentContainerViewMain, SignInFragment())
                }
            }
        }
    }
}