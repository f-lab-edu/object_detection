package com.example.objectdetection.data

data class PhotoResponseItem(
    val results: List<Photo>,
    val total: Int,
    val total_pages: Int
)

data class Photo(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any?>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val urls: Urls,
    val user: User,
    val width: Int
)

data class Links(
    val download: String,
    val html: String,
    val self: String
)

data class LinksX(
    val html: String,
    val likes: String,
    val photos: String,
    val self: String
)

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class User(
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: LinksX,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImage,
    val twitter_username: String,
    val username: String
)
