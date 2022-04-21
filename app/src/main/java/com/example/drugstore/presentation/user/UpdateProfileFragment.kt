package com.example.drugstore.presentation.user

import android.app.*
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.provider.*
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.activity.result.*
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentUpdateProfileBinding
import com.example.drugstore.presentation.order.AddPlaceActivity
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {
    private lateinit var genderList: List<String>
    private lateinit var loadAddress: ActivityResultLauncher<Intent>

    private val cal = Calendar.getInstance()
    private lateinit var calendar: DatePickerDialog
    private lateinit var loadImageFromGallery: ActivityResultLauncher<Intent>
    private lateinit var binding: FragmentUpdateProfileBinding
    private val dataUser = hashMapOf<String,Any>()

    @Inject lateinit var profileVM: ProfileVM
    @Inject lateinit var storageVM: StorageVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                binding.tvBirth.text = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(cal.time)
                dataUser[Constants.BIRTH_DATE] = cal.time
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH))
        genderList = resources.getStringArray(R.array.gender).toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        setupLoadDataFromOtherActivity()

        context?.let {
            ArrayAdapter.createFromResource(it,R.array.gender,android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = adapter
            }
        }
        onBindComponents()
        profileVM.getUserByID().observe(viewLifecycleOwner){
            if (it != null) {
                binding.etName.setText(it.UserName)
                binding.tvName.text = it.UserName
                binding.tvBirth.text = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it.Birthday)
                binding.tvAddress.text = it.Address


                if(it.PhoneNumber.isNotEmpty()){
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
        return binding.root
    }

    private fun onBindComponents(){
        binding.ivCircle.setOnClickListener {
            if(context?.let { it1 -> Constants.checkPermissionRead(it1) } == true){
                loadImage()
            }else{
                showDialogPermission()
            }
        }
        binding.tvAddress.setOnClickListener {
            val intent = Intent(context,AddPlaceActivity::class.java)
            intent.putExtra(Constants.ADDRESS,true)
            loadAddress.launch(intent)
        }

        binding.tvBirth.setOnClickListener {
            calendar.show()
        }

        binding.btnUpdate.setOnClickListener {
            if(binding.etName.text.isEmpty()){
                Toast.makeText(context,"Please fill name",Toast.LENGTH_SHORT).show()
            }else dataUser[Constants.USER_NAME] = binding.etName.text.toString()

            if(binding.etPhoneNumber.text.length in 9..12){
                dataUser[Constants.PHONE_NUMBER] = binding.etPhoneNumber.text.toString()
            }else Toast.makeText(context,"phone number invalid",Toast.LENGTH_SHORT).show()

            dataUser[Constants.GENDER] = when(binding.spinner.selectedItem.toString()){
                "Others" -> 0
                "Female" -> 1
                "Male" -> 2
                else -> 0
            }

            if(dataUser.containsKey(Constants.USER_URL_IMAGE)){
                storageVM.uploadImageToStorage("profile",dataUser[Constants.USER_URL_IMAGE].toString())
                    .observe(viewLifecycleOwner) {
                        if (it != null) {
                            dataUser[Constants.USER_URL_IMAGE] = it
                            context?.let { it1 -> profileVM.updateUser(dataUser, it1) }
                        } else {
                            Toast.makeText(
                                context,
                                "Cant storage image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }else{
                context?.let { it1 -> profileVM.updateUser(dataUser, it1) }
            }
        }
    }


    private fun showDialogPermission(){
        AlertDialog.Builder(context)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
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
        loadImageFromGallery.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
    }


    private fun setupLoadDataFromOtherActivity() {
        loadImageFromGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                            context,
                            "Failed to load image from camera",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        loadAddress = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getStringExtra(Constants.ADDRESS)?.let { dataUser[Constants.ADDRESS] = it }
                binding.tvAddress.text = dataUser[Constants.ADDRESS].toString()
            }
        }
    }
}