package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.databinding.ActivityMainBinding
import com.isidroid.b21.utils.base.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.view.*
import timber.log.Timber


class MainActivity : BindActivity<ActivityMainBinding>(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        val results1 = mutableListOf<Item>()
        val results2 = mutableListOf<Item>()

        val text = assets.open("winners2.txt").bufferedReader().use { it.readText() }
        val lines = text.lines()
        lines.forEach { line ->
            val numbers = line.split(" ").map { it.toInt() }
            val first = numbers.take(4)
            val last = numbers.takeLast(4)

            first.forEach { execute(it, results1) }
            last.forEach { execute(it, results2) }
        }

        Timber.e("lines=${lines.size}")
        Timber.i("first ${results1.sortedByDescending { it.counter }.map { it.toString(lines.size) }}")
        Timber.i("last ${results2.sortedByDescending { it.counter }.map { it.toString(lines.size) }}")
    }

    private fun execute(number: Int, data: MutableList<Item>) {
        if (!data.any { it.number == number }) data.add(Item(number))

        val item = data.first { it.number == number }
        item.counter++
    }


    data class Item(val number: Int) {
        var counter: Int = 0
        fun probability(total: Int): Float = 100 * (counter.toFloat() / total.toFloat())

        fun toString(total: Int) = "$number=$counter(${probability(total)})"
    }
}