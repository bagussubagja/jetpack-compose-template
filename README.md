
# Jetpack Compose Template Clean Architecture
This is a template project that demonstrates the implementation of the Clean Architecture pattern using Jetpack Compose for the UI layer. The project follows the principles of separation of concerns, testability, and maintainability.
Architecture Overview
The project is structured according to the Clean Architecture principles, which consists of the following layers:

- Presentation Layer: This layer contains the UI components and the ViewModel classes that handle the UI state and interact with the Domain layer. The Jetpack Compose UI components are responsible for rendering the user interface and handling user interactions.
- Domain Layer: This layer contains the business logic of the application, including use cases and domain models. It is independent of the presentation and data layers, making it easier to test and maintain the core functionality.
- Data Layer: This layer is responsible for managing the data sources, such as APIs, databases, or local storage. It provides an abstraction over the data sources and exposes a repository interface to the Domain layer.

Project Structure
The project structure follows the Clean Architecture principles and is organized as follows:

```
.
├── app					# Contains main android app code
├── data/ 				# Data level directory
│   ├── dao 			# Performing operation on the local database.
│   ├── entity 			# Entity data
│   ├── local 			# Local data source (Room, SharedPreferences, etc.)
│   ├── model 			# Model data
│   ├── remote 			# Remote data source (Retrofit, APIs)
│   └── repository 		# Repository implementations
├── di 					# Dependency Injection module (Hilt)
├── domain/ 			# Domain level directory
│   ├── item 			# Domain models
│   ├── repository 		# Repository interfaces
│   └── usecase 		# Use cases
├── navigation 			# Handling App Navigation
├── ui 					# UI Screens and their viewmodels and states
└── utils 				# Helper classes that are shared 
```

### Libraries and Technologies
The project utilizes the following libraries and technologies:

- Jetpack Compose: Modern UI toolkit for building native Android apps.
- Kotlin: The primary programming language for Android development.
- Coroutines: Asynchronous programming with coroutines for handling concurrency.
- Hilt: Dependency Injection framework for Android.
- Room: SQLite object-mapping library for local data persistence.
- Retrofit: Type-safe HTTP client for making API calls.
- OkHttp: Efficient HTTP client for networking.
- Gson: JSON parsing and serialization library.
- Chucker: HTTP inspector tool for monitoring network traffic.

#### Getting Started 
To get started with the project, follow these steps:

```
1. Clone the repository:
https://gitlab.com/bagussubagja/jetpack-compose-codebase.git
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.
```

#### Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please create a new issue or submit a pull request.
When contributing, please follow the existing code style and conventions, and provide clear and detailed descriptions of your changes.

Author - Bagus Subagja (Last Modified: 26 April 2024)

#### License

This project is licensed under the MIT License.