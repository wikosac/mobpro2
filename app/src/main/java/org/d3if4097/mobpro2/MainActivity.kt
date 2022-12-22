package org.d3if4097.mobpro2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.samples.propertyanimation.R
import com.google.samples.propertyanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        binding.rotateButton.setOnClickListener { rotater() }
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator) {
                view.isEnabled = true
            }
        })
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.ROTATION, -360f, 0f)
        with(animator) {
            duration = 1000
            disableViewDuringAnimation(binding.rotateButton)
            start()
        }
    }
}