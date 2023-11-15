package com.example.roombasicimpl.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombasicimpl.databinding.FragmentCallHistoryBinding
import com.example.roombasicimpl.presentation.util.adapters.CallHistoryAdapter
import com.example.roombasicimpl.presentation.view_models.CallHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CallHistoryFragment : Fragment() {

    private val TAG = "CallHistoryFragment"
    private val viewModel by viewModels<CallHistoryViewModel>()
    private val callsAdapter = CallHistoryAdapter()
    private lateinit var binding: FragmentCallHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCallHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getCalls()
                viewModel.state.collect {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.v(TAG, it.callList.toString())
                        callsAdapter.setItems(it.callList)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.callHistoryListRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = callsAdapter
    }
}