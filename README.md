# AI Recipe Generator 🍳
![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![UI](https://img.shields.io/badge/UI-Swing%20%2B%20FlatLaf-blue.svg)
![AI](https://img.shields.io/badge/AI-Groq%20API%20(LLaMA%203)-green.svg)
![License](https://img.shields.io/badge/license-Educational-blue.svg)

A smart desktop application that generates custom cooking recipes based on the ingredients you have at home using Artificial Intelligence.

## 🌟 Highlights
- **AI-Powered Generation** - Uses Groq API (LLaMA 3) to create unique recipes
- **Modern UI** - Clean, dark-mode interface powered by FlatLaf
- **Local Storage** - Saves your favorite recipes using structured JSON data
- **Robust Error Handling** - Graceful exception management with user-friendly dialogs

## ℹ️ Overview
AI Recipe Generator is a desktop application created as a semester project. Have some random ingredients in your fridge and don't know what to cook? Just type them into the app, and the AI will generate a complete recipe with a title, required ingredients, and step-by-step instructions. You can even save your best creations to a local favorites list!

## ✍️ Author
Created by **Ondřej Tomášek** ([@ondrat-png](https://github.com/ondrat-png))

## ⬇️ Installation

### Requirements
- Java JDK 17 or higher
- Maven (for dependency management: `org.json` and `flatlaf`)
- Active Internet connection (for API calls)

### Setup Instructions
1. Clone or download the repository.
2. Open the project in **IntelliJ IDEA** (it will automatically resolve Maven dependencies from the `pom.xml`).
3. Run the `MainWindow.java` class directly.

*(Note: See the Configuration section below before running the app for the first time).*

## 🚀 Usage

- **Enter Ingredients**: Type what you have in your fridge into the main text area (e.g., eggs, flour, milk). You can use commas or enter each item on a new line.
- **Generate Recipe**: Click the `Generate recipe` button. Wait a few seconds while the AI crafts your custom dish.
- **Save to Favorites**: Once the recipe window pops up, click `Save to Favorites` to store it locally.
- **View Favorites**: Click the `Favorites` button on the main screen to browse your previously saved recipes.

## 🏗️ Architecture

### Key Components
- `MainWindow.java` - The entry point and main GUI of the application.
- `RecipeWindow.java` - Dynamic window that displays the generated recipe.
- `FavoritesWindow.java` - Displays a list of all locally saved recipes.
- `GroqApiClient.java` - Handles HTTP communication with the AI server, including prompt formatting and JSON response parsing.
- `FileRecipeStorage.java` - Implements the `RecipeStorage` interface. Handles reading and writing `Recipe` objects to `favorites.json`.

### Technical Features
- **API Communication**: Utilizes `HttpURLConnection` to send POST requests to the Groq API.
- **JSON Parsing**: Uses the `org.json` library to construct API requests, parse AI responses, and manage local storage.
- **Exception Handling**: Robust try-catch blocks with user-friendly `JOptionPane` error dialogs instead of raw console crashes.

## ⚙️ Configuration (API Key)
To ensure the AI generation works, you need a valid Groq API key.

1. Open `GroqApiClient.java`.
2. Locate the constant `API_KEY`.
3. Replace the placeholder string with your personal Groq API key if it's not already set.

## 💭 Feedback and Contributing
This is a completed academic project. Feel free to:
- Explore the code to learn about Java Swing and REST API integration
- Fork the repository and experiment
- Open an **Issue** if you find bugs

## 📝 License
Created for educational purposes.

---
**Bon Appétit! 🍽️**
