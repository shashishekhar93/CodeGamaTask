
# CodeGamaTask News App

This is a sample news application for Android, built using modern Android development practices. It fetches news from a remote API, displays it to the user, and supports offline caching, a user profile section, and a chat feature.

## Features

- **MVVM Architecture**: Follows the official recommended architecture pattern.
- **Clean Architecture**: Separates concerns into Domain, Data, and Presentation layers.
- **Kotlin Coroutines & Flow**: For asynchronous operations.
- **Hilt**: For dependency injection.
- **Retrofit & OkHttp**: For networking.
- **Room Database**: For offline caching of news articles.
- **Glide**: For image loading.
- **Android Jetpack**:
    - **ViewModel**: To store and manage UI-related data.
    - **LiveData**: To build data objects that notify views of any database changes.
    - **Navigation Component**: To handle in-app navigation.
    - **ViewBinding**: To easily write code that interacts with views.
- **News Feed**:
    - Displays top headlines from a remote source.
    - Infinite scrolling with pagination.
    - Pull-to-refresh to fetch the latest articles.
- **Search**:
    - Real-time search for news articles.
    - Search results support pagination.
- **Offline Caching**:
    - Fetched articles are automatically saved to a local database.
    - Cached articles are displayed when the device is offline.
- **User Profile**:
    - Capture a profile image using the Camera or select from the Gallery.
    - Runtime permission handling for Camera and Location.
    - Fetches and displays the user's current location.
- **Chat**:
    - A functional chat interface.
    - Send/receive text and image messages.
    - Stage images for preview before sending.
    - Auto-reply simulation for a more interactive feel.

## API Endpoints

The application fetches data from the [News API](https://newsapi.org/). The following endpoints are used:

- `GET /v2/top-headlines`: To get breaking news headlines.

## Setup Instructions

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    ```
2.  **Open in Android Studio:**
    - Open Android Studio and select `Open an Existing Project`.
    - Navigate to the cloned repository folder.
3.  **Add API Key and Base URL:**
    - Create a file named `local.properties` in the root directory of the project.
    - Add your News API key and the base URL to this file as follows:
      ```properties
      MY_KEY="YOUR_NEWS_API_KEY_HERE"
      BASE_URL="https://newsapi.org/"
      ```
4.  **Sync and Build:**
    - Sync the Gradle files and build the project.
    - Run the application on an emulator or a physical device.

## Unit Test Results

Unit tests have not been implemented for this project yet.

## Video Demo

[Link to Video Demo - *Please upload a screen recording and add the link here*]


https://github.com/user-attachments/assets/4fd8342f-ee1d-43f2-9d08-70925ca1c879




