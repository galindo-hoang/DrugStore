package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.models.Prescription
import com.example.drugstore.data.models.PrescriptionDetail
import com.example.drugstore.databinding.ItemAddressBinding
import com.example.drugstore.databinding.ItemPrescriptionBinding
import com.example.drugstore.databinding.ItemProfileAddressBinding

class PrescriptionAdapter constructor(
    var onEditClick: ((Prescription) -> Unit)? = null,
    var onDeleteClick: ((Prescription) -> Unit)? = null,
) : RecyclerView.Adapter<PrescriptionAdapter.AddressViewHolder>() {
    class AddressViewHolder(view: ItemPrescriptionBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }

    private var list: List<Prescription> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(array: List<Prescription>) {
        this.list = array
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemPrescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val model = list[position]
        holder.binding.tvName.text = model.name
        holder.binding.tvDate.text = "${model.startDate} - ${model.endDate}"

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(model)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick?.invoke(model)
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