package org.d3if4097.mobpro2

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.data.MahasiswaDb
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    companion object {
        private val MAHASISWA_DUMMY = Mahasiswa(
            0, "6706180001", "Tika Aulia Utami"
        )
    }

    @Before
    fun setUp() {
// Lakukan penghapusan database setiap kali test akan dijalankan.
        InstrumentationRegistry.getInstrumentation().targetContext
            .deleteDatabase(MahasiswaDb.DATABASE_NAME)
    }

    @Test
    fun testInsert() {
// Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(
            MainActivity::class.java
        )
// Lakukan aksi menambah data baru
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.nimEditText)).perform(
            typeText(MAHASISWA_DUMMY.nim))
        onView(withId(R.id.namaEditText)).perform(
            typeText(MAHASISWA_DUMMY.nama))
        onView(withText(R.string.simpan)).perform(click())
// Cek apakah hasil sesuai yang diharapkan
        onView(withText(MAHASISWA_DUMMY.nim)).check(matches(isDisplayed()))
        onView(withText(MAHASISWA_DUMMY.nama)).check(matches(isDisplayed()))
// Tes selesai, tutup activity nya
        activityScenario.close()
    }

    @Test
    fun testActionMode() {
// Masukkan beberapa mahasiswa sebagai data awal
        runBlocking(Dispatchers.IO) {
            val dao = MahasiswaDb.getInstance(getApplicationContext()).dao
            dao.insertData(MAHASISWA_DUMMY)
            dao.insertData(MAHASISWA_DUMMY)
            dao.insertData(MAHASISWA_DUMMY)
        }
// Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
// Lakukan long click terhadap data pertama di RecyclerView,
// lalu cek apakah action mode nya muncul / menu delete tampil
        onView(withId(R.id.recyclerView)).atItem(0, longClick())
        onView(withId(R.id.menu_delete)).check(matches(isDisplayed()))
// Lakukan klik terhadap data ke-3 dan ke-2 pada RecyclerView,
// lalu cek apakah action mode menampilkan jumlah data terpilih
        onView(withId(R.id.recyclerView)).atItem(2, click())
        onView(withId(R.id.recyclerView)).atItem(1, click())
        onView(withText("3")).check(matches(isDisplayed()))
// Lakukan klik terhadap menu hapus, lalu konfirmasi hapus
// Cek apakah data mahasiswanya sudah tidak ada lagi
        onView(withId(R.id.menu_delete)).perform(click())
        onView(withText(R.string.hapus)).perform(click())
        onView(withText(MAHASISWA_DUMMY.nim)).check(doesNotExist())
        onView(withText(MAHASISWA_DUMMY.nama)).check(doesNotExist())
// Tes selesai, tutup activity nya
        activityScenario.close()
    }

    private fun ViewInteraction.atItem(pos: Int, action: ViewAction) {
        perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                pos, action
            )
        )
    }
}