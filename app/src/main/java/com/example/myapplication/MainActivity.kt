package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                     R.id.settings -> {
                        Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                        true
                     }
                        else -> false
                }
            }
            binding.searchBar.setNavigationOnClickListener{
                Toast.makeText(this,"Меню", Toast.LENGTH_SHORT).show()
            }


            binding.bottomNavigation.setOnItemSelectedListener {

                when (it.itemId) {
                    R.id.favorites -> {
                        Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.watch_later -> {
                        Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.selections -> {
                        Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }
}
