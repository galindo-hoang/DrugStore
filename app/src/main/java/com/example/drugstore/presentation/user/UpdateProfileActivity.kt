package com.example.drugstore.presentation.user

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityUpdateProfileBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.utils.DatePickerDialogFactory
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UpdateProfileActivity : BaseActivity() {
    private lateinit var genderList: List<String>
    private lateinit var calendar: DatePickerDialog
    private lateinit var loadImageFromGallery: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityUpdateProfileBinding
    private val dataUser = hashMapOf<String, Any>()

    @Inject lateinit var profileVM: ProfileVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendar = DatePickerDialogFactory.create(this) {
            binding.tvBirth.text =
                SimpleDateFormat(
                    "dd-MM-yyyy",
                    Locale.getDefault()
                ).format(DatePickerDialogFactory.getDate())
            DatePickerDialogFactory.setPreviousDate(DatePickerDialogFactory.getDate())
            dataUser[Constants.BIRTH_DATE] = DatePickerDialogFactory.getDate()
        }
        genderList = resources.getStringArray(R.array.gender).toList()


        ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
        setupLoadDataFromOtherActivity()
        onBindComponents()

        profileVM.getCurrentUser().observe(this) {
            if (it != null) {
                binding.etName.setText(it.UserName)
                binding.tvName.text = it.UserName
                binding.tvBirth.text = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it.Birthday)
                DatePickerDialogFactory.setPreviousDate(it.Birthday)
                if (it.PhoneNumber.isNotEmpty()) {
                    binding.etPhoneNumber.setText(it.PhoneNumber)
                    binding.etPhoneNumber.keyListener = null
                }
                binding.spinner.setSelection(it.Gender)
                Glide.with(binding.root)
                    .load(it.UserImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.ivCircle)
            }
        }
    }

    private fun onBindComponents() {
        binding.btnEditImage.setOnClickListener {
            if (Constants.checkPermissionRead(this)) {
                loadImage()
            } else {
                showDialogPermission()
            }
        }

        binding.tvBirth.setOnClickListener {
            calendar.show()
        }

        binding.btnUpdate.setOnClickListener {
            showProgressDialog(resources.getString(R.string.please_wait))
            if (binding.etName.text.isEmpty()) {
                Toast.makeText(this, "Please fill name", Toast.LENGTH_SHORT).show()
            } else dataUser[Constants.USER_NAME] = binding.etName.text.toString()

            if (binding.etPhoneNumber.text.length in 9..12 || binding.etPhoneNumber.text.isEmpty()) {
                dataUser[Constants.PHONE_NUMBER] = binding.etPhoneNumber.text.toString()
            } else Toast.makeText(this, "phone number invalid", Toast.LENGTH_SHORT).show()

            dataUser[Constants.GENDER] = when (binding.spinner.selectedItem.toString()) {
                "Others" -> 0
                "Female" -> 1
                "Male" -> 2
                else -> 0
            }
            profileVM.updateUser(dataUser,this)
        }
    }


    private fun showDialogPermission() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    private fun loadImage() {
        loadImageFromGallery.launch(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
    }


    private fun setupLoadDataFromOtherActivity() {
        loadImageFromGallery =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        try {
                            dataUser[Constants.USER_URL_IMAGE] = data.data.toString()
                            Glide
                                .with(this)
                                .load(data.data)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(binding.ivCircle)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(
                                this,
                                "Failed to load image from camera",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
    }
}