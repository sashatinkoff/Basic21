package com.isidroid.b21.clean.presentation.fragments

import android.annotation.SuppressLint
import androidx.navigation.fragment.navArgs
import com.isidroid.b21.clean.presentation.Book
import kotlinx.android.synthetic.main.fragment.*
import java.util.*
import kotlin.random.Random

class BookListFragment : BaseFragment2() {
    override fun onButtonClick() {
        val book = Book(
            name = "Some name ${UUID.randomUUID().toString().take(4)}",
            author = "Sasha",
            year = Random.nextInt(1990, 2021)
        )

       val action = BookListFragmentDirections.actionBookListFragmentToDetailsActivity(
            book = book
        )

        navController.navigate(action)
    }
}