package com.tech.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tech.myapplication.databinding.FragmentChatBinding
import com.tech.myapplication.presentation.adapter.ChatAdapter
import com.tech.myapplication.presentation.viewmodel.ChatViewModel

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                chatViewModel.temporaryImage(it)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatViewModel = (activity as MainActivity).chatViewModel
        chatAdapter = (activity as MainActivity).chatAdapter
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonSend.setOnClickListener {
            val stagedImageUri = chatViewModel.temporaryImageUri.value
            val messageText = binding.editTextMessage.text.toString()

            if (stagedImageUri != null) {
                chatViewModel.sendImageMessage(stagedImageUri.toString(), messageText)
            } else {
                chatViewModel.sendTextMessage(messageText)
            }
            // Clear inputs after sending
            binding.editTextMessage.text.clear()
            chatViewModel.temporaryImage(null)
        }

        binding.buttonReceive.setOnClickListener {
            if (chatViewModel.temporaryImageUri.value != null) {
                chatViewModel.receiveImageMessage(chatViewModel.temporaryImageUri.value.toString())
            } else {
                chatViewModel.receiveMessage()
            }

        }

        binding.fabAttachImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonRemovePreview.setOnClickListener {
            chatViewModel.temporaryImage(null)
        }
    }

    private fun observeViewModel() {
        chatViewModel.messages.observe(viewLifecycleOwner) { messages ->
            chatAdapter.differ.submitList(messages) {
                binding.recyclerViewChat.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }

        chatViewModel.temporaryImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.previewCard.visibility = View.VISIBLE
                Glide.with(this).load(uri).into(binding.previewImage)
            } else {
                binding.previewCard.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
