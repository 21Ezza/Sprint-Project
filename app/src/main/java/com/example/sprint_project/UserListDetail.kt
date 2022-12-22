package com.example.sprint_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sprint_project.databinding.ActivityUserListDetailBinding
import com.example.sprint_project.databinding.UserListViewBinding

class UserListDetail : AppCompatActivity() {
    private lateinit var binding: ActivityUserListDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserListDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userAvatar = intent.getStringExtra("userAvatar")
        val userName = intent.getStringExtra("userName")
        val userEmail = intent.getStringExtra("userEmail")

        Glide.with(this).load(userAvatar).into(binding.userImage)
        binding.userName.text = userName
        binding.userEmail.text = userEmail
    }
}