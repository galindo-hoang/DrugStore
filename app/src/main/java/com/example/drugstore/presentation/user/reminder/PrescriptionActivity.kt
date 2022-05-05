package com.example.drugstore.presentation.user.reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.drugstore.databinding.ActivityPrescriptionBinding
import com.example.drugstore.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrescriptionActivity : BaseActivity() {
    private var _binding: ActivityPrescriptionBinding? = null
    private val binding get() = _binding!!
    var fromActivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra(HOME, false)) {
            fromActivity = HOME
        }

        if (intent.getBooleanExtra(REMINDER_FRAGMENT, false)) {
            replaceFragment(ReminderFragment())
        } else {
            replaceFragment(NewPrescriptionFragment())
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentLayout.id, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        const val REMINDER_FRAGMENT = "RemindFragment"
        const val HOME = "HOME"
    }
}
