package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.models.Product
import com.example.drugstore.databinding.ItemNewPrescriptionDetailBinding
import com.example.drugstore.databinding.ItemProductBinding
import java.text.DecimalFormat

class NewPrescriptionAdapter(
    var onIncreaseClick: ((PrescriptionDetailDto) -> Unit)? = null,
    var onDecreaseClick: ((PrescriptionDetailDto) -> Unit)? = null,
    var onDeleteClick: ((PrescriptionDetailDto) -> Unit)? = null
) : RecyclerView.Adapter<NewPrescriptionAdapter.NewPrescriptionViewHolder>() {
    class NewPrescriptionViewHolder(item: ItemNewPrescriptionDetailBinding) :
        RecyclerView.ViewHolder(item.root) {
        val binding = item
    }


    private var list: List<PrescriptionDetailDto> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<PrescriptionDetailDto>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewPrescriptionViewHolder {
        return NewPrescriptionViewHolder(
            ItemNewPrescriptionDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewPrescriptionViewHolder, position: Int) {
        val model = list[position]
        holder.binding.apply {
            textViewDrugName.text = model.product?.ProName ?: ""
            textViewDrugQuantity.text = model.quantity.toString()
            btnIncrease.setOnClickListener { onIncreaseClick?.invoke(model) }
            btnDecrease.setOnClickListener { onDecreaseClick?.invoke(model) }
            btnDelete.setOnClickListener { onDeleteClick?.invoke(model) }
        }

        model.product?.run {
            Glide
                .with(holder.binding.root)
                .load(ProImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.binding.imageViewDrug)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}