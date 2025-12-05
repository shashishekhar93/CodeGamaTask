package com.tech.myapplication.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.myapplication.data.model.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>().apply { value = emptyList() }
    val messages: LiveData<List<Message>> = _messages

    private val _temporaryImageUri = MutableLiveData<Uri?>()
    val temporaryImageUri: LiveData<Uri?> = _temporaryImageUri

    private val preloadedResponse = listOf(
        "That's interesting!",
        "Tell me more.",
        "Got it.",
        "👍",
        "Haha, classic.",
        "I'm not sure, let me check."
    )

    private fun addMessage(message: Message) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
        currentMessages.add(message)
        _messages.value = currentMessages
    }

    fun sendTextMessage(content: String) {
        if (content.isBlank()) return
        val message = Message(content = content, isSent = true)
        addMessage(message)
        // i have added a code to make it more realistic by adding a delay.
        /*viewModelScope.launch {
            delay(2000)
            receiveMessage()
        }*/
    }

    fun receiveMessage(content: String? = null) {
        val replyText = content ?: preloadedResponse.random()
        val message = Message(content = replyText, isSent = false)
        addMessage(message)
    }

    fun sendImageMessage(imageUrl: String, caption: String?) {
        val message = Message(imageUrl = imageUrl, content = caption, isSent = true)
        addMessage(message)
        viewModelScope.launch {
            delay(2000)
            //receiveMessage("Cool picture!")
            receiveImageMessage(imageUrl)
        }
    }

    fun receiveImageMessage(imageUrl: String, caption: String? = null){
        val message = Message(imageUrl = imageUrl, content = caption, isSent = false)
        addMessage(message)
    }

    fun temporaryImage(uri: Uri?) {
        _temporaryImageUri.value = uri
    }
}
