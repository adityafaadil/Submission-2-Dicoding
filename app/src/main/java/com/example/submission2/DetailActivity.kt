package com.example.submission2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.submission2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        var EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData()
        val get = intent.getParcelableExtra<User>(EXTRA_USER)
        val uname = get?.username
        val pageAdapter = PageAdapter(this, uname.toString())
        binding.viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetTextI18n")
    private fun userData() {
        val listUser = intent.getParcelableExtra<User>(EXTRA_USER)
        binding.detailName.text = checkNull(listUser?.name)
        binding.detailUsername.text = "@" + checkNull(listUser?.username)
        binding.detailRepository.text = checkNull(listUser?.repository)
        binding.detailCompany.text = checkNull(listUser?.company)
        binding.detailLocation.text = checkNull(listUser?.location)

        Glide.with(this)
            .load(listUser?.avatar)
            .into(binding.detailAvatar)
    }

    private fun checkNull(string: String?): String? {
        if (string == "null"){
            return "-"
        }
        return string
    }
}