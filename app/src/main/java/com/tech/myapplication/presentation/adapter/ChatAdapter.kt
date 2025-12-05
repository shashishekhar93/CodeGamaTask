package com.tech.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tech.myapplication.data.model.Message
import com.tech.myapplication.databinding.ItemChatReceivedBinding
import com.tech.myapplication.databinding.ItemChatSentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    private val differCallback = object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemViewType(position: Int): Int {
        val message = differ.currentList[position]
        return if (message.isSent) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding =
                ItemChatSentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageViewHolder(binding)
        } else {
            val binding = ItemChatReceivedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = differ.currentList[position]
        when (holder) {
            is SentMessageViewHolder -> holder.bind(message)
            is ReceivedMessageViewHolder -> holder.bind(message)
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    inner class SentMessageViewHolder(private val binding: ItemChatSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            if (message.imageUrl != null) {
                binding.sentImageViewMessage.visibility = View.VISIBLE
                binding.textViewMessage.visibility = View.GONE
                Glide.with(binding.sentImageViewMessage.context)
                    .load(message.imageUrl)
                    .into(binding.sentImageViewMessage)
            } else {
                binding.sentImageViewMessage.visibility = View.GONE
                binding.textViewMessage.visibility = View.VISIBLE
                binding.textViewMessage.text = message.content
            }
            binding.textViewTimestamp.text = formatTimestamp(message.timestamp)
        }
    }

    inner class ReceivedMessageViewHolder(private val binding: ItemChatReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            if (message.imageUrl != null) {
                binding.receivedImageViewMessage.visibility = View.VISIBLE
                binding.textViewMessage.visibility = View.GONE
                Glide.with(binding.receivedImageViewMessage.context)
                    .load(message.imageUrl)
                    .into(binding.receivedImageViewMessage)
            } else {
                binding.receivedImageViewMessage.visibility = View.GONE
                binding.textViewMessage.visibility = View.VISIBLE
                binding.textViewMessage.text = message.content
            }
            binding.textViewTimestamp.text = formatTimestamp(message.timestamp)
        }
    }
}
