package com.example.fakestoreapp.utils

open class Event<out T>(private val content: T) {


    private var isContentHandled = false

    fun getContentIfNotHandled(): T? {
        return if (isContentHandled) null else {
            isContentHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
