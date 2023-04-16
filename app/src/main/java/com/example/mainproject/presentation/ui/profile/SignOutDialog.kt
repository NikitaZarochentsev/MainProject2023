package com.example.mainproject.presentation.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mainproject.R

class SignOutDialog(private val onButtonClicked: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val alertDialog = builder
            .setTitle(R.string.sign_out_dialog_title)
            .setPositiveButton(R.string.sign_out_dialog_positive) { _, _ -> onButtonClicked.invoke() }
            .setNegativeButton(R.string.sign_out_dialog_negative) { dialog, _ -> dialog.cancel() }
            .create()

        alertDialog.show()

        return alertDialog
    }
}