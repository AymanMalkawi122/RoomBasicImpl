package com.example.roombasicimpl.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombasicimpl.R
import com.example.roombasicimpl.common.Constants
import com.example.roombasicimpl.common.SnackBbarMaker
import com.example.roombasicimpl.databinding.FragmentContactFormBinding
import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.presentation.view_models.ContactFormViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactFormFragment : Fragment() {

    private val TAG = "ContactFormFragment"
    private val viewModel by viewModels<ContactFormViewModel>()
    private lateinit var binding: FragmentContactFormBinding
    private var contactID: Int? = null
    private var formMode:String = "create"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactFormBinding.inflate(layoutInflater, container, false)
        contactID = arguments?.getInt(Constants.ArgumentNames.contactID)
        formMode = if(contactID == null) "create" else "update"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener { saveOnClick() }
        binding.cancelButton.setOnClickListener{ endFragment() }
        populateForm()
        hideBottomNav()
    }

    override fun onDestroy() {
        super.onDestroy()
        showBottomNav()
    }

    private fun saveOnClick() {
        val contact = collectFormContact()

        viewLifecycleOwner.lifecycleScope.launch {
            decideTransaction(contact)
            viewModel.state.collect { state ->
                if(state.contactTransactionSuccessful == true) {
                    activity?.let {
                        SnackBbarMaker.makeSnackBar(
                            it.findViewById(android.R.id.content),
                            "Success"
                        )
                    }
                    endFragment()
                    return@collect
                }
                else if(state.contactTransactionSuccessful == false){
                    activity?.let {
                        SnackBbarMaker.makeSnackBar(
                            it.findViewById(android.R.id.content),
                            "Something went wrong"
                        )
                    }
                }
            }
        }

    }

    private fun endFragment(){
        val navController = findNavController()
        navController.popBackStack()
    }

    private fun collectFormContact(): Contact {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()

        return Contact(firstName, lastName, phoneNumber)
    }

    private fun hideBottomNav() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.apply { this.visibility = View.GONE }
    }

    private fun showBottomNav() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.apply { this.visibility = View.VISIBLE }
    }

    private suspend fun decideTransaction(contact: Contact){
        if(formMode == "create")
            viewModel.addContact(contact)
        else viewModel.updateContact(contact)
        Log.v(TAG,"FORM MODE $formMode")
    }

    private fun populateForm(){
        if (formMode == "create")
            return

        viewLifecycleOwner.lifecycleScope.launch {
            val id = if(contactID == null) -1 else contactID!!
            viewModel.getContactById(id)

            viewModel.state.collect { state ->
                if(state.contactFetchSuccessful == true) {
                    Log.v(TAG, "${state.contactInitialDetails}")
                    binding.firstName.setText(state.contactInitialDetails?.firstName)
                    binding.lastName.setText(state.contactInitialDetails?.lastName)
                    binding.phoneNumber.setText(state.contactInitialDetails?.phoneNumber)
                }

                else if(state.contactFetchSuccessful == false){
                    activity?.let {
                        SnackBbarMaker.makeSnackBar(
                            it.findViewById(android.R.id.content),
                            "Something went wrong"
                        )
                    }
                }
            }
        }
    }

    companion object {
        val TAG = "BottomSheetDialogFragment"
    }
}

