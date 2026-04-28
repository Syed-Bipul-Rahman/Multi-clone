package com.dong.multirun

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dong.multirun.MultiRunApplication
import com.dong.multirun.fragment.InstalledAppFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CloneAppActivity : AppCompatActivity() {

    private val TAG = "CloneAppActivity"

    companion object {
        const val EXTRA_PKG  = "extra_pkg"
        const val EXTRA_NAME = "extra_name"

        private val TAB_TITLES = listOf("User Apps", "System Apps")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clone_app)

        val toolbar   = findViewById<Toolbar>(R.id.toolbar)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        viewPager.adapter = ClonePagerAdapter(this)


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        checkCloneEngine()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    fun onCloneInstallComplete(successCount: Int, failCount: Int) {
        Log.i(TAG, "Clone install complete — success=$successCount, fail=$failCount")
    }

    private fun checkCloneEngine() {
        val cloneClassLoader = MultiRunApplication.getCloneClassLoader()
        if (cloneClassLoader == null) {
            Log.e(TAG, "Clone ClassLoader is null — CloneEngine will not work!")
            Toast.makeText(
                this,
                "Clone engine failed to initialise. Please restart the app.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Log.i(TAG, "Clone ClassLoader ready: ${cloneClassLoader.javaClass.simpleName}")
        }
    }

    // -------------------------------------------------------------------------
    // Pager adapter
    // -------------------------------------------------------------------------

    private inner class ClonePagerAdapter(
        activity: FragmentActivity
    ) : FragmentStateAdapter(activity) {  // ✅ FragmentStateAdapter, not FragmentPagerAdapter

        override fun getItemCount(): Int = TAB_TITLES.size

        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> InstalledAppFragment.newInstance(showSystem = false)
                1 -> InstalledAppFragment.newInstance(showSystem = true)
                else -> throw IllegalArgumentException("Unknown tab position: $position")
            }
    }
}