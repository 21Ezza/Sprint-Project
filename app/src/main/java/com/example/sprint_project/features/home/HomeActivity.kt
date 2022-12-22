package com.example.sprint_project.features.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sprint_project.R
import com.example.sprint_project.data.model.AdapterItem
import com.example.sprint_project.data.model.ItemModel
import com.example.sprint_project.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), HomeContract {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapterNotif: AdapterItem

    private val listTransactionTitle = listOf(
        ItemModel("Keluar", "Top Up E-Wallet", "Gopay - 08123123123", "Rp. 150.000"),
        ItemModel("Masuk", "Transaksi Masuk", "BRI - 3453 3434 3435", "Rp. 20.000"),
        ItemModel("Keluar", "Pembelian", "Telkomsel - 08123123123", "Rp. 1.500.000"),
        ItemModel("Keluar", "Pembelian", "BNI - 0896734212", "Rp. 150.000"),
        ItemModel("Masuk", "Gaji Infosys", "Mandiri - 78755112893", "Rp. 4.500.000"),
        ItemModel("Keluar", "Pembelian", "ShopeePay - 0897534877", "Rp. 780.000"),
        ItemModel("Masuk", "Top Up Steam", "OVO - 087590212903", "Rp. 659.000"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userProfile.setUpperText("Abdullah Ibn Hassan")
        binding.userProfile.setLowerText("666 Poin")
        binding.userProfile.setTextColor(R.color.black, R.color.blue)
        binding.userProfile.setTextSize(16f, 12f)
        binding.userProfile.setIcon(R.drawable.ic_notif)

        adapterNotif = AdapterItem(listTransactionTitle.toMutableList())

        val layoutManager = LinearLayoutManager(this)
        binding.rvTransaction.adapter = adapterNotif
        binding.rvTransaction.layoutManager = layoutManager

    }
}