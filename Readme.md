# Video Feed App

## Overview
The **Video Feed App** is an Android application designed to fetch and display a list of videos from an external API. The app follows the MVVM (Model-View-ViewModel) architecture and utilizes a local database to store video data for offline access. The app periodically refreshes video content in the background using WorkManager.

## Architecture & Data Flow
1. **User Interaction**:
    - The user launches the app, which initializes components and schedules background work.
2. **Data Fetching & Storage**:
    - The **Fragment** requests a playlist from the **ViewModel**.
    - The **ViewModel** queries the **Repository** for videos.
    - The **Repository** first retrieves stored videos from the **Database**.
    - If no data is available, the **Repository** fetches videos from the **Network API**, updates the **Database**, and returns fresh data.
3. **UI Updates**:
    - The **ViewModel** updates the **Fragment**, which updates the UI with the retrieved videos.
    - The user can click a video, triggering an **Intent** to open the video in the YouTube app.

State diagram covering the major component and interactions of Video Feed App:
[state_diagram.png](assets/state_diagram.png)

## Key Components
- **VideoFeedApplication**: Initializes background tasks using WorkManager.
- **VideoFeedActivity**: Hosts the fragment and serves as the entry point.
- **VideoFeedFragment**: Fetches and displays the video list.
- **VideoFeedViewModel**: Manages UI-related data and lifecycle awareness.
- **VideosRepository**: Handles data retrieval from the database and network.
- **VideosDatabase**: Stores fetched videos locally for offline viewing.
- **Network API**: Fetches video data from an external service.
- **YouTube Intent**: Opens YouTube when a user selects a video.

## Technologies Used
- **Kotlin / Java** for Android development
- **MVVM Architecture**
- **Room Database** for local storage
- **Retrofit** for API calls
- **WorkManager** for periodic background refresh
- **LiveData & ViewModel** for lifecycle-aware UI updates

## How It Works
1. The app launches and initializes dependencies.
2. The fragment requests the video list from ViewModel.
3. The repository fetches stored videos or queries the API.
4. Data is stored in RoomDB and delivered to the ViewModel.
5. UI updates with the latest video list.
6. Clicking a video launches YouTube via an intent.

## Future Enhancements
- Implement pagination for large video lists.
- Add caching mechanisms to reduce API calls.
- Improve UI with animations and transitions.
- Enable offline playback of downloaded videos.