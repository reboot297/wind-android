package com.reboot297.wind.android

data class Item(
    /**
     * Title of the item.
     */
    val title: String?,
    /**
     * List of web urls. We expect that sometimes they be more than one.
     */
    val links: List<String>?,
    /**
     * List of media urls. These maybe links to audio/video files
     */
    val media: List<String>?
)

data class Group(
    /**
     * Version of file
     */
    val version: String,
    /**
     * Comma separated tags
     */
    val tags: String,
    /**
     * Array of items.
     */
    val items: List<Item>
)