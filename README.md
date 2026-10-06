# 🎵 Music Vibe

[![Android SDK](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android.com)
[![Target SDK](https://img.shields.io/badge/TargetSDK-34-blue.svg)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-orange.svg)](LICENSE)

**Music Vibe** is an advanced, modern Android music streaming and offline playback application built with a sleek Glassy design system, dynamic palette-based theming, high-quality audio streaming, and powerful local playlist management.

---

## ✨ Features Overview

### 🎶 1. Online Music Streaming & Discovery
- **Vast Music Catalog**: Stream millions of tracks, albums, playlists, and top artist tracks online.
- **Smart Recommendations & Quick Picks**: Dynamic homepage sections featuring *Quick Picks*, *Recommended Albums*, *Top Artists*, and *Music Videos*.
- **Mood & Genre Filtering**: One-tap interactive chips to filter music by mood or region:
  - 🔥 Trending
  - ⚡ Energise
  - 😊 Feel Good
  - 🧘 Relax
  - 🎙️ Podcasts
  - 🇮🇳 Hindi / Bhojpuri / Haryanvi
- **Instant Search**: Real-time debounced music search with instant results for tracks, artists, and albums.

---

### 🎧 2. Adaptive Audio Quality Controls
- **Smart Auto Quality Mode**: Automatically measures network bandwidth (Wi-Fi, Ethernet, or Cellular speed) and picks the best bitrate for buffer-free playback.
- **Manual Quality Customization**:
  - 🌟 **320 kbps**: Very High Quality (Studio fidelity & rich audio)
  - 🎵 **160 kbps**: High Quality (Optimal speed-to-quality ratio)
  - 📻 **96 kbps**: Standard Quality (Fast buffering)
  - ⚡ **48 kbps**: Data Saver (Minimizes cellular data consumption)
  - 📉 **12 kbps**: Ultra Low Quality (Extreme bandwidth saving)

---

### 📱 3. Modern Glassy UI & Dynamic Visual Experience
- **Dynamic Palette Theming**: The player background dynamically transitions its color gradient based on the dominant artwork color extracted from the currently playing album cover.
- **Expandable BottomSheet Player**: Full-screen player dialog with smooth gestures, slider controls, and full track metadata.
- **Persistent Mini Player**: Quick play/pause, title scrolling, and mini progress bar accessible across all screens.
- **Custom Squiggly Progress Bar**: Unique animated squiggly progress bar on full player.
- **Shimmer Skeletons**: Beautiful skeleton placeholders while content loads asynchronously.

---

### 🔄 4. Foreground Background Service & Playback Controls
- **Uninterrupted Background Playback**: Powered by a robust Android `ForegroundService` with `WakeLock` support to ensure uninterrupted playback when the screen is off or the app is in the background.
- **Media Notification & Lock Screen Controls**: Full media notification controls using `MediaSessionCompat` (Play, Pause, Skip, Previous, Track Info, Album Art).
- **Bluetooth & Hardware Button Support**: Works seamlessly with Bluetooth headsets and system media buttons.
- **Playback Modes**: Support for Repeat All, Repeat One (`1`), and Shuffle modes.
- **Favorites System**: One-tap favoriting system to save tracks into your liked list.

---

### 📥 5. Offline Music Player & Downloads
- **In-App Music Downloader**: Download tracks directly to your local storage with progress tracking.
- **Local Storage Scanner (MediaStore)**: Automatically indexes and plays local MP3/audio files on your device.
- **Offline Controls**: Options to **Play All**, **Shuffle**, delete tracks, and view local storage track counts.

---

### 📂 6. Custom Multi-Playlist Manager
- **Custom Playlists**: Create, rename, and delete multiple custom playlists.
- **Track Organization**: Easily add or remove tracks across custom playlists.
- **Persistent Local Database**: Built-in `MultiPlaylistManager` backed by JSON serialization and `SharedPreferences`.

---

### 🔄 7. Direct GitHub Auto-Updates
- **In-App Update Checker**: Directly queries GitHub Releases (`Aakashdevelopers/music_vibe`) for app updates.
- **Changelog Preview**: Displays full update release notes and version information in a modern BottomSheet dialog.
- **In-App Direct Downloader**: Downloads the update `.apk` file directly in the app with real-time percentage and MB download progress indicators.
- **One-Tap Installer**: Launches the native Android package installer automatically upon download completion.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Java / Android SDK
- **Minimum SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 34 (Android 14)
- **UI Framework**: Material Design 3, ConstraintLayout, CoordinatorLayout, ViewPager2, RecyclerView, BottomSheetBehavior, Facebook Shimmer
- **Image Processing & Palette**: Picasso, Picasso Transformations, AndroidX Palette API, CircleImageView
- **Networking**: OkHttp 3, Gson, Apache HTTP Legacy
- **Media Engine**: Android `MediaPlayer`, `MediaSessionCompat`, `NotificationCompat.MediaStyle`, Custom `MusicService`
- **Backend / Storage**: Firebase Database (BoM 33.9.0), SharedPreferences, Android MediaStore API

---

## 🚀 How to Build & Run

### Requirements
- **Android Studio** (Ladybug or newer recommended)
- **JDK 11**
- **Android SDK 34**

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Aakashdevelopers/music_vibe.git
   cd music_vibe
   ```
2. **Open in Android Studio**:
   Open Android Studio and select **Open** -> Navigate to the cloned `music_vibe` directory.
3. **Gradle Sync**:
   Allow Gradle to download dependencies and sync the project.
4. **Run Application**:
   Connect an Android device or launch an emulator (API 24+) and click **Run** (`Shift + F10`).

---

## 👤 Author & Credits

Developed with ❤️ by **[Aakash Developers](https://github.com/Aakashdevelopers)**.
