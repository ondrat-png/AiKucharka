# AI Recipe Generator 🍳
![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![UI](https://img.shields.io/badge/UI-Swing%20%2B%20FlatLaf-blue.svg)
![AI](https://img.shields.io/badge/AI-Groq%20API%20(LLaMA%203)-green.svg)
![License](https://img.shields.io/badge/license-Educational-blue.svg)

A smart desktop application that generates custom cooking recipes based on the ingredients you have at home using Artificial Intelligence.

## 🌟 Highlights
- **AI-Powered Generation** - Uses Groq API (LLaMA 3) to create unique recipes.
- **Modern UI** - Clean interface powered by FlatLaf.
- **Local Storage & Management** - Save and remove your favorite recipes using structured JSON data.
- **Robust Error Handling** - Graceful exception management with user-friendly dialogs instead of console crashes.

## ℹ️ Overview
AI Recipe Generator is a desktop application created as a semester project. Have some random ingredients in your fridge and don't know what to cook? Just type them into the app, and the AI will generate a complete recipe with a title, required ingredients, and step-by-step instructions. You can even save your best creations to a local favorites list and manage them later!

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
3. **Important:** Follow the *Configuration* steps below before running the app for the first time.
4. Run the `Main.java` class.

## ⚙️ Configuration (API Key & UI)
To ensure the AI generation works and the UI renders correctly, you need to configure your IDE:

### 1. Setup API Key (Environment Variable)
Because the Groq API key is hidden for security reasons, you must set it up locally:
* In **IntelliJ IDEA**, go to `Run` -> `Edit Configurations...`
* Select your `Main` run configuration.
* Find the **Environment variables** field.
* Add your key like this: `GROQ_API_KEY=your_actual_api_key_here`

### 2. Setup VM Options (UI Warning Fix)
This project uses FlatLaf for a modern look. If you use Java 21+, you might see a native-access warning in the console. To hide it:
* In the same `Edit Configurations...` window, go to **VM options** (you might need to click `Modify options` -> `Add VM options` first).
* Add this line: `--enable-native-access=ALL-UNNAMED`

## 🚀 Usage

- **Enter Ingredients**: Type what you have in your fridge into the main text area.
- **Generate Recipe**: Click the `Generate recipe` button. Wait a few seconds while the AI crafts your custom dish.
- **Save to Favorites**: Once the recipe window pops up, click `Save to Favorites` to store it locally.
- **Manage Favorites**: Click the `Favorites` button on the main screen to browse your saved recipes. You can double-click a recipe to read it, or select it and click `Remove` to delete it permanently.

## 🏗️ Architecture

### Key Components
- `Main.java` - The entry point of the application.
- `MainWindow.java` - The main GUI of the application.
- `RecipeWindow.java` - Dynamic window that displays the generated recipe.
- `FavoritesWindow.java` - Displays a list of all locally saved recipes with viewing and deleting options.
- `GroqApiClient.java` - Handles HTTP communication with the AI server, including prompt formatting and JSON response parsing.
- `FileRecipeStorage.java` - Implements the `RecipeStorage` interface. Handles reading, writing, and deleting `Recipe` objects using `favorites.json`.

## 💭 Feedback and Contributing
This is a completed academic project. Feel free to:
- Explore the code to learn about Java Swing and REST API integration
- Fork the repository and experiment
- Open an **Issue** if you find bugs

## 📝 License
Created for educational purposes.

---
**Bon Appétit! 🍽️**