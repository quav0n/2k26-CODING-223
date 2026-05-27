# Face Recognition Attendance System (AMS)

An elegant, modern biometric attendance monitoring system built with **JavaFX 13** following **Google Material Design 3** specifications and high-fidelity iOS-inspired user interface workflows.

The system features a deterministic probability biometric lockscreen with dynamic SVG face transformations, responsive workspace layout capabilities, and complete CRUD memory integration.

---

## 📌 Project Architecture & Requirements Fulfillment

As outlined in the course evaluation criteria, this application focuses on proving foundational competency across core software engineering metrics:
- **Biometric Mock Login System:** Implements an asynchronous facial verification scanner with smart failure/success randomizers.
- **Graphical User Interface (GUI):** Built completely upon a modern, dynamic Material Design 3 single-window paradigm.
- **Dynamic Window Properties:** Native operating system maximization/minimization responsive handlers linked with custom visibility layout toggles.
- **Basic CRUD Operations:** Real-time checking data validation grids providing insertion and elimination pipelines.
- **Software Planning & Architecture:** Modeled explicitly upon an organized Model-View-Controller (MVC) separation pattern.

---

## 🛠 Tech Stack & Environment Matrix

- **Programming Language:** Java 11+
- **GUI Framework:** JavaFX 13 (Compatible with standard JDK runtimes)
- **Layout Architecture:** FXML (Separated View layer declarations)
- **Build Management Tool:** Maven / NetBeans Integrated Platform
- **Design System Rules:** Google Material Design 3 Token Palette + iOS Bracket Viewfinder Elements

---

## 🗂 Project Structure Map

```text
facescan/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── groupsix/
│       │           └── facescan/
│       │               ├── MainApp.java            # Primary Stage Application Lifecycle Runner
│       │               ├── LoginController.java    # Logic, Threads, Animation & Biometric Probabilities
│       │               ├── DashboardController.java# Active View Toggle & Tables CRUD Logic Controller
│       │               └── AttendanceRecord.java   # Data Encapsulation POJO Entity Object
│       │
│       └── resources/
│           └── com/
│               └── groupsix/
│                   └── facescan/
│                       ├── Login.fxml              # Glassmorphic UI with Vector Layouts
│                       └── Dashboard.fxml          # Responsive Grid with Dynamic Sidebar Workspace
│
└── pom.xml                                         # Maven Dependency Configuration Node