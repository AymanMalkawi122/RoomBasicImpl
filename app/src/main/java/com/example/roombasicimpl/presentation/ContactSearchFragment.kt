package com.example.roombasicimpl.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.roombasicimpl.databinding.FragmentContactSearchBinding
import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.presentation.util.adapters.FilteredContactListAdapter
import com.example.roombasicimpl.presentation.view_models.ContactsSearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactSearchFragment : Fragment() {

    private val TAG = "ContactSearchFragment"
    private val viewModel by viewModels<ContactsSearchViewModel>()
    private val contactsAdapter: FilteredContactListAdapter = FilteredContactListAdapter(
        { contactID: Int -> navigateToContactDetailFragment(contactID) },
        { contactID: Int -> navigateToContactFormFragment(contactID) },
        { contact: Contact -> addCall(contact) }
    )
    private lateinit var binding: FragmentContactSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchContacts()
        binding.searchBar.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contactsAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        hideBottomNav()
    }


    override fun onDestroy() {
        super.onDestroy()
        showBottomNav()
    }

    private fun hideBottomNav() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.apply { this.visibility = View.GONE }
    }

    private fun showBottomNav() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.apply { this.visibility = View.VISIBLE }
    }

    private fun fetchContacts() {
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

    private fun addCall(contact: Contact) {
        val call = Call(contact.id)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    private fun navigateToContactDetailFragment(contactID: Int) {
        val navController = findNavController()
        val bundle = Bundle()
        bundle.putInt(Constants.ArgumentNames.contactID, contactID)
        navController.navigate(R.id.contactDetailFragment, bundle)
    }

    private fun navigateToContactFormFragment(contactID: Int = -1) {
        val navController = findNavController()
        val bundle = Bundle()
        bundle.putInt(Constants.ArgumentNames.contactID, contactID)
        navController.navigate(R.id.contactFormFragment, bundle)
    }

    companion object {
        val TAG = "ContactSearchFragment"
    }

}
