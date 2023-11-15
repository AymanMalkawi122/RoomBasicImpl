package com.example.roombasicimpl.presentation.util.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roombasicimpl.databinding.ComponentCallItemBinding
import com.example.roombasicimpl.domain.model.Call


open class CallHistoryAdapter: RecyclerView.Adapter<CallHistoryAdapter.CallViewHolder>() {
    private val TAG = "RecentCallsAdapter"

    protected var callsList: ArrayList<Call> = ArrayList()

    inner class CallViewHolder(val binding: ComponentCallItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        return CallViewHolder(
            ComponentCallItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = callsList.size

    fun setItems(newList: List<Call>) {
        val diffCallback = CallsDiffCallback(callsList, newList)
        val result = DiffUtil.calculateDiff(diffCallback)
        callsList.clear()
        callsList.addAll(newList)
        result.dispatchUpdatesTo(this)
    }


    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val currentCall = callsList[position]
        holder.binding.apply {
            contactTag.text = currentCall.contactId.toString()
            date.text = currentCall.dateFormatted
        }
    }

    private class CallsDiffCallback(
        private val oldList: List<Call>,
        private val newList: List<Call>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

}