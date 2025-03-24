# Attendance Tracker

Attendance Tracker is an Android application designed to help users manage and track their attendance for various subjects. The app provides features to add, update, and delete subjects, view attendance details, and manage user profiles.

## Features

- **Subject Management**:
  - Add new subjects with attendance percentage and class days.
  - Update attendance for existing subjects.
  - Delete subjects from the list.

- **User Profile**:
  - View and edit user profile details such as name and bio.

- **Navigation**:
  - Navigate between the "Subjects" screen and the "About Me" screen using a clean and intuitive interface.

- **Dynamic Attendance Updates**:
  - Fetch updated attendance data from an external API (mocked in the current implementation).

- **Modern UI**:
  - Built using Jetpack Compose for a modern and responsive user interface.
  - Material 3 design principles for a clean and consistent look.

## Screenshots

*(Add screenshots of the app here)*

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern for better separation of concerns and maintainability.

### Key Components

1. **Model**:
   - `Subject`: Represents a subject with attributes like name, attendance percentage, class days, and possible leaves.

2. **ViewModel**:
   - `SubjectViewModel`: Manages the state and logic for the subject list.
   - `AboutMeViewModel`: Manages the state and logic for the user profile.

3. **View**:
   - `SubjectScreen`: Displays the list of subjects and allows adding, updating, and deleting subjects.
   - `AboutMeScreen`: Displays and allows editing of the user profile.

4. **Data**:
   - `PreferenceHelper`: Handles saving and retrieving user profile data using `SharedPreferences`.

5. **Navigation**:
   - `AppNavigation`: Manages navigation between screens using Jetpack Navigation Compose.

## Tech Stack

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Data Storage**: SharedPreferences
- **Dependency Injection**: None (can be extended with Hilt or Koin)
- **Networking**: Mocked API calls using `kotlinx.coroutines` and `Gson`

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-repo/AttendanceTracker.git
   cd AttendanceTracker
   ```

2. **Open the Project**: Launch Android Studio and open the project.

3. **Build and Run**: Compile and execute the app on an emulator or physical device.

## Usage

### Subjects

- Add a subject using the floating action button (+).
- Update attendance by selecting "Update" on a subject card.
- Remove a subject via the "Delete" button on a subject card.

### Profile

- Access the "About Me" screen by tapping the profile icon at the top-right.
- Modify your name and bio, then save your changes.

## API Integration

The application uses a mocked API for attendance data retrieval. The API URL and fake response are embedded in the `SubjectViewModel`. For production, substitute the mock with an actual API endpoint.
