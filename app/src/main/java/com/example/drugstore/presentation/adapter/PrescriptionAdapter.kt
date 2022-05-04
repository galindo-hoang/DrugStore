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
import java.text.SimpleDateFormat

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
        val format = SimpleDateFormat("dd-MM-yyyy")

        holder.binding.tvDate.text =
            "${format.format(model.startDate)} - ${format.format(model.endDate)}"

        var hour = "${model.time!!["hours"]}"
        if (model.time["hours"]!! < 10) {
            hour = "0${model.time["hours"]}"
        }

        var minute = "${model.time["minutes"]}"
        if (model.time["minutes"]!! < 10) {
            minute = "0${model.time["minutes"]}"
        }

        holder.binding.tvTime.text =
            "${hour}:${minute}"

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