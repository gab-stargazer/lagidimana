# Lagidimana

## Overview

Lagidimana is an Android application developed with Kotlin that tracks the location of a user at regular 5-minute intervals. The application stores the location data in a local Room database and automatically deletes all records after 10 PM local time.

## Features

- **Jetpack Compose**: For building the UI with a declarative approach.
- **Koin**: Dependency injection framework to manage dependencies.
- **GeoAPI**: To work with geographical data to obtain an address of a specific coordinate.
- **Google Maps**: Integrated map functionalities.
- **Work Manager**: To handle database cleaning.
- **Room DB**: Local database for storing location data.
- **Paging3**: For efficient data loading from the Room database.
- **Coroutines**: To handle asynchronous tasks.
- **Accompanist Permission Manager**: For managing location permissions.

## Screenshots

| No Permission Screen | Map Screen | Location History Screen |
|----------------------|-------------------|----------|
| ![No Permission Screen](https://github.com/gab-stargazer/lagidimana/blob/master/image/no_permission_screen.png) | ![Map Screen](https://github.com/gab-stargazer/lagidimana/blob/master/image/map_screen.png) | ![Location History Screen](https://github.com/gab-stargazer/lagidimana/blob/master/image/location_history_screen.png) |

### All in One Screen for large device
![All In One Screen](https://github.com/gab-stargazer/lagidimana/blob/master/image/map_and_history_screen.png)

## Download

You can download the APK file for the Employee Location Tracker app using the link below:

[Download APK](https://drive.google.com/file/d/1N4vVk0cQzqCDzjYNMJHtcK_IkijcXs2u/view?usp=sharing)

## Prerequisites

- Android Studio Giraffe or later
- Kotlin 1.8.0 or later
- Gradle 8.1.1 or later

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```
### 2. Open the Project in Android Studio
- Open Android Studio.
- Click on File > Open. 

### 3. Navigate to the directory where you cloned the project and select it.
- Set Up API Keys
- To use Google Maps and GeoAPI, you need to add your API keys:
- Create a local.properties file in the root of your project.
- Add the following lines:

```
MAPS_API_KEY=your_google_maps_api_key
```

### 4. Build the Project
- Ensure that you have an Android device or emulator running.
- Click on Build > Make Project or press Ctrl+F9.
- To install and run the app on your device, click on Run > Run 'app' or press Shift+F10.

## How It Works
- The app tracks the user's location every 5 minutes using Work Manager and stores it in a Room database.
- The app uses Coroutine to ensure that all background tasks are handled efficiently.
- The location data is automatically deleted from the database at 10 PM local time.
- The app uses Paging3 to load location data in a paginated manner, improving performance with large datasets.
- Permissions are managed with Accompanist, ensuring a smooth user experience.
