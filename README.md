🚀 SpaceX Launches Android App
Show Image
Show Image
Show Image
Show Image
A modern Android application that displays SpaceX launches using GraphQL API. Built with Clean Architecture, MVI pattern, and Jetpack Compose for a scalable and maintainable codebase.
📹 Demo Video
<!-- OPTION 1: Upload video to GitHub -->
https://github.com/user-attachments/assets/your-video-id.mp4
<!-- OPTION 2: Add video thumbnail with link to YouTube/external hosting -->
Show Image
<!-- OPTION 3: Use GIF (recommended for README) -->
Show Image

📱 Screenshots
<p align="center">
  <img src="screenshots/launches_light.png" width="250" alt="Launches Light"/>
  <img src="screenshots/launches_dark.png" width="250" alt="Launches Dark"/>
  <img src="screenshots/detail_light.png" width="250" alt="Detail Light"/>
</p>
<p align="center">
  <img src="screenshots/detail_dark.png" width="250" alt="Detail Dark"/>
  <img src="screenshots/loading_state.png" width="250" alt="Loading"/>
  <img src="screenshots/error_state.png" width="250" alt="Error"/>
</p>

✨ Features

📋 Browse SpaceX Launches - View all past and upcoming SpaceX launches
🔍 Launch Details - Detailed information about each mission
🌓 Dark Mode Support - Seamless light and dark theme switching
🎨 Material Design 3 - Modern UI with Material You components
🚀 Real-time Data - Fetches latest launch data from SpaceX GraphQL API
📶 Offline Support - Apollo normalized cache for offline access
🔄 Pull to Refresh - Easy data refresh with swipe gesture
⚡ Fast & Smooth - Optimized performance with Jetpack Compose
🎯 Type-Safe Navigation - Safe Args for navigation between screens


🏗️ Architecture
This project follows Clean Architecture principles with clear separation of concerns:
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                   │
│  ┌────────────────────────────────────────────────────┐ │
│  │ UI (Jetpack Compose)                               │ │
│  │ - Screens, Components, Theme                       │ │
│  └────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────┐ │
│  │ ViewModel (MVI Pattern)                            │ │
│  │ - State, Intent, Effect                            │ │
│  └────────────────────────────────────────────────────┘ │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│                     Domain Layer                        │
│  ┌────────────────────────────────────────────────────┐ │
│  │ Use Cases                                          │ │
│  │ - GetLaunchesUseCase                               │ │
│  │ - GetLaunchDetailUseCase                           │ │
│  └────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────┐ │
│  │ Domain Models                                      │ │
│  │ - Launch, LaunchDetail                             │ │
│  └────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────┐ │
│  │ Repository Interface                               │ │
│  └────────────────────────────────────────────────────┘ │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│                      Data Layer                         │
│  ┌────────────────────────────────────────────────────┐ │
│  │ Repository Implementation                          │ │
│  └────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────┐ │
│  │ Data Sources                                       │ │
│  │ - Remote (Apollo GraphQL)                          │ │
│  └────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────┐ │
│  │ Mappers                                            │ │
│  │ - GraphQL DTOs → Domain Models                     │ │
│  └────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────┘
📐 Architecture Layers
1. Presentation Layer

UI Components: Reusable Jetpack Compose components
Screens: Main screens (Launches List, Launch Detail)
ViewModels: MVI pattern with State, Intent, and Effect
Theme: Material Design 3 theming system

2. Domain Layer

Use Cases: Business logic encapsulation
Models: Core business entities
Repository Interfaces: Contracts for data access

3. Data Layer

Repository Implementation: Concrete data access implementation
Data Sources: Apollo GraphQL client for API communication
Mappers: Transform GraphQL responses to domain models


🎨 Design Patterns
MVI (Model-View-Intent)
kotlin// State - Represents the UI state
data class LaunchesListState(
    val launches: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Intent - User actions
sealed interface LaunchesListIntent {
    object LoadLaunches : LaunchesListIntent
    data class OnLaunchClick(val launchId: String) : LaunchesListIntent
}

// Effect - One-time events
sealed interface LaunchesListEffect {
    data class NavigateToDetail(val launchId: String) : LaunchesListEffect
}
Repository Pattern
kotlininterface LaunchRepository {
    suspend fun getLaunches(): Result<List<Launch>>
    suspend fun getLaunchDetail(id: String): Result<LaunchDetail>
}
Use Case Pattern
kotlinclass GetLaunchesUseCase @Inject constructor(
    private val repository: LaunchRepository
) {
    suspend operator fun invoke(): Result<List<Launch>>
}

🛠️ Tech Stack
Core

Kotlin - 100% Kotlin
Jetpack Compose - Modern declarative UI
Material Design 3 - Latest Material Design components
Coroutines - Asynchronous programming
Flow - Reactive streams

Architecture Components

ViewModel - UI state management
Navigation Compose - Type-safe navigation
Lifecycle - Lifecycle-aware components

Dependency Injection

Dagger Hilt - Dependency injection framework

Network & Data

Apollo GraphQL - GraphQL client (v4.0.0)
OkHttp - HTTP client
Apollo Normalized Cache - Data caching

Image Loading

Coil - Image loading library for Compose

Code Quality

Kotlin DSL - Gradle build configuration
Version Catalog - Centralized dependency management


📦 Project Structure
com.example.launches/
├── data/
│   ├── mapper/
│   │   └── LaunchMapper.kt
│   ├── remote/
│   │   ├── apollo/
│   │   │   └── ApolloClientProvider.kt
│   │   └── datasource/
│   │       ├── LaunchRemoteDataSource.kt
│   │       └── LaunchRemoteDataSourceImpl.kt
│   └── repository/
│       └── LaunchRepositoryImpl.kt
├── di/
│   ├── DataSourceModule.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
├── domain/
│   ├── model/
│   │   ├── Launch.kt
│   │   └── LaunchDetail.kt
│   ├── repository/
│   │   └── LaunchRepository.kt
│   └── usecase/
│       ├── GetLaunchesUseCase.kt
│       └── GetLaunchDetailUseCase.kt
├── navigation/
│   ├── NavGraph.kt
│   └── Screen.kt
├── presentation/
│   ├── detail/
│   │   ├── LaunchDetailScreen.kt
│   │   └── LaunchDetailViewModel.kt
│   └── list/
│       ├── LaunchesListScreen.kt
│       └── LaunchesListViewModel.kt
└── ui/
    ├── components/
    │   ├── DetailTopBar.kt
    │   ├── EmptyStateView.kt
    │   ├── ErrorView.kt
    │   ├── InfoSection.kt
    │   ├── LaunchesTopBar.kt
    │   ├── LaunchIcon.kt
    │   ├── LaunchListItem.kt
    │   ├── LoadingIndicator.kt
    │   └── MissionPatch.kt
    └── theme/
        ├── Color.kt
        └── Theme.kt

🚀 Getting Started
Prerequisites

Android Studio: Hedgehog (2023.1.1) or newer
JDK: Version 11 or higher
Android SDK: API 24 (Android 7.0) or higher
Gradle: 8.1+

Installation

Clone the repository

bash   git clone https://github.com/yourusername/spacex-launches-app.git
   cd spacex-launches-app

Open in Android Studio

Open Android Studio
Select "Open an Existing Project"
Navigate to the cloned repository
Wait for Gradle sync to complete


Build the project

bash   ./gradlew build

Run the app

Connect an Android device or start an emulator
Click the "Run" button in Android Studio
Or use the command line:



bash     ./gradlew installDebug

🔧 Configuration
GraphQL Schema
The app uses SpaceX GraphQL API. Schema files are located at:

Schema: app/src/main/graphql/schema.graphqls
Queries: app/src/main/graphql/*.graphql

API Endpoint
kotlin// data/remote/apollo/ApolloClientProvider.kt
serverUrl = "https://apollo-fullstack-tutorial.herokuapp.com/graphql"
Build Variants
kotlinbuildTypes {
    debug {
        applicationIdSuffix = ".debug"
        debuggable = true
    }
    release {
        isMinifyEnabled = true
        proguardFiles(...)
    }
}

🧪 Testing
Unit Tests
bash./gradlew test
Instrumentation Tests
bash./gradlew connectedAndroidTest
Code Coverage
bash./gradlew jacocoTestReport

📊 GraphQL Queries
Get Launches
graphqlquery GetLaunches {
  launches {
    hasMore
    cursor
    launches {
      id
      site
      mission {
        name
        missionPatch(size: LARGE)
      }
      rocket {
        id
        name
        type
      }
    }
  }
}
Get Launch Detail
graphqlquery GetLaunchDetail($id: ID!) {
  launch(id: $id) {
    id
    site
    mission {
      name
      missionPatch(size: LARGE)
    }
    rocket {
      id
      name
      type
    }
    isBooked
  }
}

🎯 Key Features Implementation
Image Loading with Coil
kotlinAsyncImage(
    model = missionPatch,
    contentDescription = "Mission patch",
    contentScale = ContentScale.Crop
)
Error Handling
kotlinResult.fold(
    onSuccess = { data -> /* Handle success */ },
    onFailure = { error -> /* Handle error */ }
)
State Management
kotlinval state by viewModel.state.collectAsStateWithLifecycle()

📱 Minimum Requirements

Minimum SDK: API 24 (Android 7.0)
Target SDK: API 36 (Android 14)
Compile SDK: API 36


🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository
Create a feature branch (git checkout -b feature/amazing-feature)
Commit your changes (git commit -m 'Add amazing feature')
Push to the branch (git push origin feature/amazing-feature)
Open a Pull Request

Coding Standards

Follow Kotlin Coding Conventions
Use meaningful commit messages
Write unit tests for new features
Update documentation as needed


📝 License
This project is licensed under the MIT License - see the LICENSE file for details.
MIT License

Copyright (c) 2024 [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...

👨‍💻 Author
Your Name

GitHub: @yourusername
LinkedIn: Your Name
Email: your.email@example.com


🙏 Acknowledgments

SpaceX GraphQL API
Apollo GraphQL
Android Developers
Material Design


📚 Resources

Clean Architecture Guide
MVI Pattern
Jetpack Compose
Apollo Android Documentation


📈 Roadmap

 Add unit tests coverage (80%+)
 Implement search functionality
 Add favorite launches feature
 Implement push notifications for upcoming launches
 Add launch countdown timer
 Integrate with more SpaceX APIs
 Add launch video playback
 Implement analytics


🐛 Known Issues

Mission patch images may not load for some older launches
Network timeout on slow connections (working on optimization)


💬 Support
If you have any questions or need help, please:

Open an Issue
Start a Discussion
Contact via email: your.email@example.com


<p align="center">
  Made with ❤️ and ☕
</p>
<p align="center">
  <img src="https://img.shields.io/github/stars/yourusername/spacex-launches-app?style=social" alt="Stars"/>
  <img src="https://img.shields.io/github/forks/yourusername/spacex-launches-app?style=social" alt="Forks"/>
  <img src="https://img.shields.io/github/watchers/yourusername/spacex-launches-app?style=social" alt="Watchers"/>
</p>
