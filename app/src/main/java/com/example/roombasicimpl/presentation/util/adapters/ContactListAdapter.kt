package com.example.roombasicimpl.presentation.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roombasicimpl.databinding.ComponentContactItemBinding
import com.example.roombasicimpl.domain.model.Contact


class ContactListAdapter(
    val navigateToContactDetailFragment: (contactID:Int)->Unit,
    val navigateToContactFormFragment: (contactID:Int)->Unit,
    val addCall: (contact:Contact)->Unit,
) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {
    private val TAG = "ContactListAdapter"

    var contactsList: ArrayList<Contact> = ArrayList()
    var contactsListFiltered: ArrayList<Contact> = ArrayList()
    private var displayedItem: ContactViewHolder? = null

    inner class ContactViewHolder(val binding: ComponentContactItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ComponentContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = contactsList.size

    fun setItems(newList: List<Contact>) {
        val diffCallback = DiffCallback(contactsList, newList)
        val result = DiffUtil.calculateDiff(diffCallback)
        contactsList.clear()
        contactsList.addAll(newList)
        contactsListFiltered = contactsList
        result.dispatchUpdatesTo(this)
    }

    protected fun setFilteredItems(newFilteredList: List<Contact>) {
        val diffCallback = DiffCallback(contactsListFiltered, newFilteredList)
        val result = DiffUtil.calculateDiff(diffCallback)
        contactsListFiltered.clear()
        contactsListFiltered.addAll(newFilteredList)
        result.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contactsListFiltered[position]
        holder.binding.apply {
            fullName.text = currentContact.fullName
            phoneNumber.text = currentContact.phoneNumber
            fullName.setOnClickListener{toggleContactIconsVisibility(holder)}
            contactDetailButton.setOnClickListener{navigateToContactDetailFragment(currentContact.id)}
            contactEditButton.setOnClickListener{navigateToContactFormFragment(currentContact.id)}
            contactCallButton.setOnClickListener{addCall(currentContact)}
        }
    }

    private fun toggleContactIconsVisibility(holder: ContactViewHolder){
        holder.binding.apply {
            if (displayedItem == holder) {
                contactIconContainer.visibility = View.GONE
                displayedItem = null
            } else {
                displayedItem?.binding?.contactIconContainer?.visibility = View.GONE
                displayedItem = holder
                contactIconContainer.visibility = View.VISIBLE
            }
        }
    }


    private class DiffCallback(
        private val oldList: List<Contact>,
        private val newList: List<Contact>,
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