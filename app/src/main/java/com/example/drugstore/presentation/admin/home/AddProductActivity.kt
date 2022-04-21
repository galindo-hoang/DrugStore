package com.example.drugstore.presentation.admin.home

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.Product
import com.example.drugstore.databinding.ActivityAddProductBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.CategoryVM
import com.example.drugstore.presentation.home.ProductVM
import com.example.drugstore.presentation.user.StorageVM
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject
@AndroidEntryPoint
class AddProductActivity : BaseActivity() {
    private lateinit var loadImageFromGallery: ActivityResultLauncher<Intent>
    private lateinit var binding:ActivityAddProductBinding
    private var product:Product = Product()
    private var update = false
    private var dataProduct: HashMap<String,Any> = hashMapOf()

    @Inject lateinit var categoryVM: CategoryVM
    @Inject lateinit var productVM: ProductVM
    @Inject lateinit var storageVM: StorageVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)

        if(intent.hasExtra(Constants.OBJECT_PRODUCT)){
            update = true
            binding.btnAdd.text = "Update"
            product = intent.getParcelableExtra(Constants.OBJECT_PRODUCT)!!
            setUpLoadExtra()

        }

        categoryVM.getAllCategories().observe(this){
            if(it != null){
                val nameCategory: MutableList<String> = mutableListOf()
                for(i in it) nameCategory.add(i.CatName)
                val categoryAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, nameCategory)
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sCategory.adapter = categoryAdapter
                if(update) binding.sCategory.setSelection(product.CatID)
                binding.sCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                        if(update) dataProduct[Constants.CAT_ID] = it[pos].CatID
                        else product.CatID = it[pos].CatID
                    }
                }
            }
        }



        setupLoadDataFromOtherActivity()
        setUpEvent()

        setContentView(binding.root)
    }

    private fun setUpEvent() {

        binding.ivProduct.setOnClickListener {
            if(Constants.checkPermissionRead(this)){
                loadImage()
            }else{
                showDialogPermission()
            }
        }

        binding.btnAdd.setOnClickListener {
            showProgressDialog("please wait a minute ...")
            val name = binding.etName.text.toString()
            val quantity = binding.etQuantity.text.toString()
            val price = binding.etPrice.text.toString()
            if(name.isEmpty() || quantity.isEmpty() || price.isEmpty()){
                Toast.makeText(this,"Please fill blank",Toast.LENGTH_SHORT).show()
            }else{
                if(update){
                    dataProduct[Constants.PRODUCT_NAME] = name
                    dataProduct[Constants.PRODUCT_PRICE] = price.toInt()
                    dataProduct[Constants.PRODUCT_QUANTITY] = quantity.toInt()
                    dataProduct[Constants.DESCRIPTION] = binding.etDes.text.toString()
                    productVM.updateProduct(dataProduct, product.ProID.toString()).observe(this){
                        hideProgressDialog()
                        if(it == false) Toast.makeText(this,"Cant update product",Toast.LENGTH_SHORT).show()
                        else {
                            finish()
                        }
                    }
                }else{
                    product.ProName = name
                    product.Price = price.toInt()
                    product.Quantity = quantity.toInt()
                    product.Description =  binding.etDes.text.toString()
                    productVM.addProduct(product).observe(this){
                        hideProgressDialog()
                        if(it == true){
                            finish()
                        }else Toast.makeText(this,"Cant add product",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setUpLoadExtra() {
        binding.etName.setText(product.ProName)
        binding.etPrice.setText(product.Price.toString())
        binding.etQuantity.setText(product.Quantity.toString())
        binding.etDes.setText(product.Description)
        Glide.with(binding.root)
            .load(product.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(binding.ivProduct)
    }

    private fun showDialogPermission(){
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
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
                        if(update) dataProduct[Constants.USER_URL_IMAGE] = data.data.toString()
                        else product.ProImage = data.data.toString()
                        Glide
                            .with(this)
                            .load(data.data)
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(binding.ivProduct)
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