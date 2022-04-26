package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.models.PrescriptionDetail
import com.example.drugstore.databinding.ItemAddressBinding
import com.example.drugstore.databinding.ItemProfileAddressBinding

class AddressProfileAdapter constructor(
    var onEditClick: ((Address) -> Unit)? = null,
) : RecyclerView.Adapter<AddressProfileAdapter.AddressViewHolder>() {
    class AddressViewHolder(view: ItemProfileAddressBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }

    private var list: List<Address> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(array: List<Address>) {
        this.list = array
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemProfileAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val model = list[position]
        holder.binding.tvAddress.text = model.address
        holder.binding.tvPhoneNumber.text = model.phoneNumber
        holder.binding.tvTitle.text = model.title

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(model)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }

}