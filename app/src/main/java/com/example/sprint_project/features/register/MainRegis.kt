package com.example.sprint_project.features.register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.sprint_project.MainActivity
import com.example.sprint_project.R
import com.example.sprint_project.data.api.CredentialApi
import com.example.sprint_project.data.api.UserApi
import com.example.sprint_project.databinding.ActivityMainRegisBinding

class MainRegis : AppCompatActivity(), RegisView {
    lateinit var binding: ActivityMainRegisBinding
    private val presenter = RegisPresenter(CredentialApi(), UserApi())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onAttach(this)

        binding.btnSelanjutnya.setOnClickListener {
            presenter.validatePassword(
                binding.iKataSandi.editText?.text.toString(),
                binding.tvKonfirmasi.editText?.text.toString()
            )
            presenter.validateEmailAndPassword(
                binding.idPengguna.editText?.text.toString(),
                binding.tvKonfirmasi.editText?.text.toString()
            )


        }

        binding.idPengguna.editText?.doOnTextChanged() {text, start, before, count ->
            presenter.validateEmail(
                binding.idPengguna.editText?.text.toString(),
            )
        }

        binding.tvKonfirmasi.editText?.doOnTextChanged() {text, start, before, count ->
            presenter.validatePassword(
                binding.iKataSandi.editText?.text.toString(),
                binding.tvKonfirmasi.editText?.text.toString()
            )
        }
    }

    override fun onLoading() {
        binding.progressIndicator.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressIndicator.isVisible = false
    }

    override fun onError(code: Int, message: String) {
        when(code) {
            1 -> binding.iKataSandi.error = message
            1 -> binding.tvKonfirmasi.error = message
            2 -> binding.idPengguna.error = message
            else -> {
                AlertDialog.Builder(this)
                    .setMessage("code: $code, $message")
                    .setPositiveButton("Ok", this::dialogClickListener)
                    .setNegativeButton("Cancel", this::dialogClickListener)
                    .create()
                    .show()
            }
        }
    }

    private fun dialogClickListener(dialogInterface: DialogInterface, button: Int) {
        when (button) {
            DialogInterface.BUTTON_NEGATIVE -> {}
            DialogInterface.BUTTON_POSITIVE -> {}
            DialogInterface.BUTTON_NEUTRAL -> {}
        }
    }


    override fun onSuccessLogin(userName: String, password: String) {
        presenter.register(userName,password)

    }

    override fun onSuccessRegis() {
        Toast.makeText(this, "Success Register", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onErrorSuccess() {
        binding.iKataSandi.isErrorEnabled = false
        binding.tvKonfirmasi.isErrorEnabled = false
        binding.idPengguna.isErrorEnabled = false
        binding.btnSelanjutnya.isEnabled = true
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}