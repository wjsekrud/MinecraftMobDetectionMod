
# Minecraft Mob Detection Mod

Minecraft Mob Detection Mod is a modification for Minecraft that uses YOLOv5 object detection Network to detect mobs in the game. This mod can be used to enhance the gameplay experience by providing real-time information about mob presence and behavior.

## Features

- Real-time detection of various Minecraft mobs.
- Utilizes machine learning algorithms for accurate detection.
- Easy integration with existing Minecraft setups.
- Customizable settings to adjust detection angle. It currently scans opposite sight of your sight, and scans once per 120 frames.

## Requirements

- Minecraft version 1.16.5
- python version 3.10.6 or higher
- Java JDK 8
- pytorch

## Installation

1. **Download the Mod:**
   - Clone the repository or download the latest release from the [releases page](https://github.com/wjsekrud/MinecraftMobDetectionMod/releases).

   ```bash
   git clone https://github.com/wjsekrud/MinecraftMobDetectionMod.git
   ```

2. **Install Dependencies:**
   - Ensure you have Java and pytorch installed on your system.

   ```bash
   sudo apt-get install openjdk-8-jdk
   pip install pytorch
   ```

3. **Build the Mod:**
   - Navigate to the mod directory and build the project.

   ```bash
   cd MinecraftMobDetectionMod
   ./gradlew build
   ```

4. **Add to Minecraft:**
   - Copy the built `.jar` file from the `build/libs` directory to your Minecraft `mods` folder.

   ```bash
   cp build/libs/MinecraftMobDetectionMod.jar ~/.minecraft/mods/
   ```

## Usage

1. **Launch Minecraft & Local Server:**
   - Start Minecraft with the Forge profile to load the mod.
   - Execute local image detection server "detect_server.py" so that object detection network can work on background.

2. **Configure Settings:**
   - In the game, go to the mod settings to customize the detection parameters according to your preferences.

3. **Start Detecting Mobs:**
   - Play the game as usual. The mod will detect mobs in real-time and provide alerts as console message or information based on your configuration.

## Contribution

This project is created with improvision, so there are many incompleted & unstable issues. Contributions are welcome!

1. **Fork the Repository:**
   - Click the `Fork` button on the top right of the repository page.

2. **Create a Branch:**
   - Create a new branch for your feature or bugfix.

   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Changes:**
   - Implement your feature or fix the bug and commit your changes.

   ```bash
   git commit -m "Add feature/fix bug: description"
   ```

4. **Push Changes:**
   - Push your changes to your forked repository.

   ```bash
   git push origin feature/your-feature-name
   ```

5. **Create Pull Request:**
   - Open a pull request to the main repository and describe your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Acknowledgements

- [pyTorch](https://pytorch.org/) for the machine learning framework.
- [Minecraft Monster Dataset](https://universe.roboflow.com/minecraft-object-detection/minecraft-mob-detection) for the dataset used for training the Network.
- [Forge](https://files.minecraftforge.net/) for the Minecraft modding platform.
