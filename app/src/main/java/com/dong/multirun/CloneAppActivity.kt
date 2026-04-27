package com.dong.multirun

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dong.multirun.fragment.InstalledAppFragment
import com.google.android.material.tabs.TabLayout

class CloneAppActivity : AppCompatActivity() {

    private val TAG = "CloneAppActivity"

    companion object {
        const val EXTRA_PKG  = "extra_pkg"
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clone_app)

        val toolbar   = findViewById<Toolbar>(R.id.toolbar)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        viewPager.adapter = ClonePagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        // FIX: Verify the clone ClassLoader is ready before allowing installs.
        // If it's null the user should know immediately rather than silently failing.
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    /**
     * Called by InstalledAppFragment after clone installs complete.
     * FIX: replaces the old installApp() which just called setResult+finish
     *      without ever invoking the clone engine.
     */
    fun onCloneInstallComplete(successCount: Int, failCount: Int) {
        Log.i(TAG, "Clone install complete — success=$successCount, fail=$failCount")
        // Refresh home screen or notify HomeActivity if needed
        // e.g. setResult(RESULT_OK) if started with startActivityForResult
    }

    // -------------------------------------------------------------------------
    // Pager adapter
    // -------------------------------------------------------------------------

    private inner class ClonePagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val tabs = listOf(
            "User Apps"   to InstalledAppFragment.newInstance(showSystem = false),
            "System Apps" to InstalledAppFragment.newInstance(showSystem = true)
        )

        override fun getItem(position: Int): Fragment = tabs[position].second
        override fun getCount(): Int                  = tabs.size
        override fun getPageTitle(position: Int): CharSequence = tabs[position].first
    }
}