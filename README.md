# Inventum Opus - README

## Description
This mobile application, “Inventum Opus”, which means finding work in Latin, is an app that can be used to find and apply for jobs. After creating your résumé in the app, you can apply for a job with a simple click of a button. Based on the user résumé, the app will also try to recommend job positions as well. Users can also track the status of the jobs that they applied to.

## Tools / Software
### System Requirements
**Windows:**
- Minimum:
    - OS: 64-bit Microsoft Windows 8
    - RAM: 8 GB
    - CPU: x86_64 CPU architecture; 2nd generation Intel Core or newer, or AMD CPU with support for Windows Hypervisor Framework
    - Disk Space: 8 GB (IDE and Android SDK and Emulator)
    - Screen Resolution: 1280 x 800

- Recommended:
    - OS: Latest 64-bit version of Windows
    - RAM: 16 GB or more
    - CPU: Latest Intel Core processor
    - Disk Space: Solid state drive with 16 GB or more
    - Screen Resolution: 1920 x 1080

**Mac:**
- Minimum:
    - OS: macOS 10.14 (Mojave)
    - RAM: 8 GB
    - CPU: Apple M1 chip, or 2nd generation Intel Core or newer with support for Hypervisor Framework
    - Disk Space: 8 GB (IDE and Android SDK and Emulator)
    - Screen Resolution: 1280 x 800

- Recommended:
    - OS: Latest version of macOS
    - RAM: 16 GB or more
    - CPU: Latest Apple Silicon chip
    - Disk Space: Solid state drive with 16 GB or more
    - Screen Resolution: 1920 x 1080

**Linux:**
- Minimum:
    - OS: Any 64-bit Linux distribution that supports Gnome, KDE, or Unity DE; GNU C Library (glibc) 2.31 or later
    - RAM: 8 GB
    - CPU: x86_64 CPU architecture; 2nd generation Intel Core or newer, or AMD processor with support for AMD Virtualization (AMD-V) and SSSE3
    - Disk Space: 8 GB (IDE and Android SDK and Emulator)
    - Screen Resolution: 1280 x 800

- Recommended:
    - OS: Latest 64-bit version of Linux
    - RAM: 16 GB or more
    - CPU: Latest Intel Core processor
    - Disk Space: Solid state drive with 16 GB or more
    - Screen Resolution: 1920 x 1080

### File Structure
The application uses Model-View-ViewModel (MVVM) architecture model. This model displays data and forward user interactions to the `ViewModel`, which acts as a bridge between the Model and the View.

```
com.example.inventumopus/
├── api/
│   ├── ApiInterface.kt
│   └── RetrofitClient.kt
├── datamodels/
│   ├── Bookmark.kt
│   ├── Experience.kt
│   ├── Job.kt
│   ├── Job Application.kt
│   ├── JobIDs.kt
│   ├── Qualification.kt
│   └── User.kt
├── ui/
│   ├── components/
│   │   ├── dropdownMenu.kt
│   │   ├── informationModal.kt
│   │   ├── listingCard.kt
│   │   └── loading.kt
│   ├── screens/
│   │   ├── BookmarksScreen.kt
│   │   ├── ExperienceScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── JobApplicationsScreen.kt
│   │   ├── JobDetailsScreen.kt
│   │   ├── ProfileScreen.kt
│   │   ├── QualificationsScreen.kt
│   │   ├── SearchScreen.kt
│   │   ├── SignInScreen.kt
│   │   └── SignUpScreen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── HomeViewModel.kt
└── MainActivity.kt
```

### Dependencies
The following are the dependencies that I used for developing this app.

- ``"com.squareup.retrofit2:retrofit:2.9.0"``
- ``"com.squareup.retrofit2:converter-gson:2.9.0"``
- ``"androidx.recyclerview:recyclerview:1.3.2"``
- ``"com.google.android.material:material:1.12.0"``
- ``"com.squareup.picasso:picasso:2.8"``
- ``"androidx.appcompat:appcompat:1.6.1"``
- ``"androidx.activity:activity:1.7.0"``
- ``"androidx.navigation:navigation-compose:2.7.7"``
- ``"io.coil-kt:coil-compose:2.7.0"``
- ``"androidx.compose.ui:ui-text-google-fonts:1.6.8"``


## Getting Started
- First you will have to download and install Android Studio with the default settings.
- After you finish installing, click ``Open...``, and select the project folder.
- Once the projects finishes loading, you can press the ``Run`` button at the top of the window.
- Finally, the application will run after a few seconds.