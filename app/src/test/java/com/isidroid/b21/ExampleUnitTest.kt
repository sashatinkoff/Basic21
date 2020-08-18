package com.isidroid.b21

import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val data = mutableSetOf<Int>()
        var counter = 0
        while (true) {
            val item = Random.nextLong(0, 1000000000000).toInt()
            if (!data.add(item)) {
                println("Stop for $item, counter=$counter")
                break
            }
            counter++
        }

        counter = 0
        while (counter < 1000000) {
            val uid = UUID.randomUUID()
            val item = uid.hashCode()
            if (!data.add(item)) {
                println("Stop for $item $uid, counter=$counter")
                break
            }
            counter++
        }


        assert(true)
    }
}