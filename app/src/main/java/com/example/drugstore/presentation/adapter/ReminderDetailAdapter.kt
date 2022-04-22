package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.PrescriptionDetail
import com.example.drugstore.databinding.ItemReminderDetailBinding

class ReminderDetailAdapter(
    var onItemClick: ((PrescriptionDetail) -> Unit)? = null
) : RecyclerView.Adapter<ReminderDetailAdapter.ReminderDetailViewHolder>() {
    class ReminderDetailViewHolder(item: ItemReminderDetailBinding) :
        RecyclerView.ViewHolder(item.root) {
        val binding = item
    }

    private var list: List<PrescriptionDetail> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<PrescriptionDetail>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderDetailViewHolder {
        return ReminderDetailViewHolder(
            ItemReminderDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReminderDetailViewHolder, position: Int) {
        val model = list[position]
        holder.binding.apply {
            textViewDrugName.text = model.product?.ProName ?: ""
            textViewDrugQuantity.text = model.quantity.toString()
            root.setOnClickListener {
                onItemClick?.invoke(model)
            }
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