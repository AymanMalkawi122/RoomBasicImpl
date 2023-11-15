package com.example.roombasicimpl.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombasicimpl.R
import com.example.roombasicimpl.common.Constants
import com.example.roombasicimpl.common.SnackBbarMaker
import com.example.roombasicimpl.databinding.FragmentContactListBinding
import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.presentation.util.adapters.ContactListAdapter
import com.example.roombasicimpl.presentation.view_models.ContactsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactListFragment : Fragment() {

    private val TAG = "ContactListFragment"
    private val viewModel by viewModels<ContactsListViewModel>()
    private val contactsAdapter: ContactListAdapter = ContactListAdapter(
        {contactID:Int->navigateToContactDetailFragment(contactID)},
        {contactID:Int->navigateToContactFormFragment(contactID)},
        {contact:Contact->addCall(contact)}
    )
    private lateinit var binding: FragmentContactListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getAllContacts()
                viewModel.state.collect {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.v(TAG, it.contactList.toString())
                        contactsAdapter.setItems(it.contactList)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.contactsListRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = contactsAdapter
    }

    private fun addCall(contact: Contact){
        val call = Call(contact.id)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getAllContacts()
                viewModel.addCall(call)

                activity?.let {
                    SnackBbarMaker.makeSnackBar(
                        it.findViewById(android.R.id.content),
                        "Call Made"
                    )
                }
            }
        }
    }

    private fun navigateToContactDetailFragment(contactID:Int){
        val navController = findNavController()
        val bundle = Bundle()
        bundle.putInt(Constants.ArgumentNames.contactID, contactID)
        navController.navigate(R.id.contactDetailFragment, bundle)
    }

    private fun navigateToContactFormFragment(contactID:Int = -1){
        val navController = findNavController()
        val bundle = Bundle()
        bundle.putInt(Constants.ArgumentNames.contactID, contactID)
        navController.navigate(R.id.contactFormFragment, bundle)
    }
}