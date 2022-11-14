package com.example.pbl_sns_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pbl_sns_project.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.joinbtn2.setOnClickListener {
            var Join = true

            val email = binding.emailtext.text.toString()
            val password1 = binding.pwtext1.text.toString()
            val password2 = binding.pwtext2.text.toString()

            if(email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                Join = false
            }

            if(password1.isEmpty()) {
                Toast.makeText(this, "password를 입력해주세요", Toast.LENGTH_LONG).show()
                Join = false
            }

            if(password2.isEmpty()) {
                Toast.makeText(this, "password를 입력해주세요", Toast.LENGTH_LONG).show()
                Join = false
            }

            if(!password1.equals(password2)) {
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_LONG).show()
                Join = false
            }

            if(password1.length < 8) {
                Toast.makeText(this, "비밀번호를 8자 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
                Join = false
            }

            if(Join) {
                auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}