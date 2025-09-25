# Campus Course & Records Manager (CCRM)

A comprehensive Java console application for managing student records, courses, enrollments, and grades.

## Features
- Student Management (Add, Update, List)
- Course Management with Search & Filter
- Enrollment System with Grade Recording
- GPA Calculation and Transcript Generation
- File Import/Export (CSV format)
- Automated Backups with NIO.2

## Technologies Used
- Java SE 17+
- NIO.2 File API
- Streams API
- Java Time API
- OOP Principles

## How to Run
1. Clone this repository
2. Open in IntelliJ IDEA
3. Set JDK 17 or higher
4. Run `src/edu/ccrm/cli/Main.java`

## Project Structure
src/
└── edu/ccrm/
├── cli/ # Command Line Interface (Main menu system)
├── config/ # Application configuration (Singleton pattern)
├── domain/ # Domain models (Student, Course, Enrollment, etc.)
├── service/ # Business logic services
├── io/ # File operations (NIO.2, Streams API)
└── util/ # Utility classes and helpers


## 🏗 Design Patterns Implemented

- **Singleton Pattern**: `AppConfig` class for application configuration
- **Builder Pattern**: `Course.Builder` for flexible object creation
- **Factory Pattern**: Object creation utilities

## 📊 Java Evolution Timeline

| Version | Year | Key Features |
|---------|------|--------------|
| Java 1.0 | 1996 | Initial release |
| Java 5 | 2004 | Generics, Autoboxing, Annotations |
| Java 8 | 2014 | **Lambdas, Streams API, Date/Time API** |
| Java 11 | 2018 | LTS release, HTTP Client |
| Java 17 | 2021 | Current LTS, Sealed classes |

## 🌐 Java Platforms Comparison

| Platform | Purpose | Use Cases |
|----------|---------|-----------|
| **Java SE** | Standard Edition | **Desktop applications (This project)** |
| Java EE | Enterprise Edition | Web applications, distributed systems |
| Java ME | Micro Edition | Mobile devices, embedded systems |

## 🔧 JDK/JRE/JVM Architecture

- **JDK (Java Development Kit)**: Development tools, compiler, debugger
- **JRE (Java Runtime Environment):** Libraries, JVM for execution  
- **JVM (Java Virtual Machine)**: Executes Java bytecode, platform independence

## 📥 Installation & Setup

### Prerequisites
- **JDK 17 or higher** ([Download here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html))
- **IntelliJ IDEA** (Recommended) or any Java IDE

### Windows Installation Steps
1. Download and install JDK 17
2. Set `JAVA_HOME` environment variable
3. Add `%JAVA_HOME%\bin` to PATH
4. Verify installation:
   ```cmd
   java -version
   javac -version
