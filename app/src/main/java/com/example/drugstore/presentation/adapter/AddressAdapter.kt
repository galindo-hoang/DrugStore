package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.data.models.Address
import com.example.drugstore.databinding.ItemAddressBinding

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    private var list: List<Address> = listOf()
    var selectedPosition: Int = -1
    var onClickItemListener: ((Address, Int) -> Unit)? = null
    var onClickRadioListener: ((Address) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(array: List<Address>) {
        this.list = array
        notifyDataSetChanged()
    }

    class AddressViewHolder(view: ItemAddressBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemAddressBinding.inflate(
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
        holder.binding.rb.isChecked =
            (model.check && (selectedPosition == position || selectedPosition == -1))
        holder.binding.rb.setOnCheckedChangeListener { _, b ->
            if (b) {
                if (selectedPosition != -1) list[selectedPosition].check = false
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                list[holder.adapterPosition].check = true
                notifyItemChanged(holder.adapterPosition)
                onClickRadioListener?.invoke(list[holder.adapterPosition])

            }
        }
        holder.binding.root.setOnClickListener {
            onClickItemListener?.invoke(model, position)
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