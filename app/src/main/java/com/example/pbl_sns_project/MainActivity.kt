package com.example.pbl_sns_project

//import android.content.Intent
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.pbl_sns_project.databinding.ActivityMainBinding
import com.example.snsproject.alluser.FollowFragment
import com.example.snsproject.home.HomeFragment
import com.example.snsproject.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val homeFragment = HomeFragment()
        val followFragment = FollowFragment()
        val myPageFragment = MyPageFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(homeFragment)
        Snackbar.make(bottomNavigationView,"${auth.currentUser!!.email}", Snackbar.LENGTH_LONG).show()
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.allUser-> replaceFragment(followFragment)
                R.id.myPage -> {
                    var bundle = Bundle()
                    var uid = FirebaseAuth.getInstance().currentUser?.uid
                    bundle.putString("destinationUid", uid)
                    myPageFragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,myPageFragment).commit()
                }
            }
            true
        }


        binding.logout.setOnClickListener {
            auth.signOut()

            val intent1 = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent1)
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, permission))
                }.show()
            }
        }
        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, permission))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }
    }
}