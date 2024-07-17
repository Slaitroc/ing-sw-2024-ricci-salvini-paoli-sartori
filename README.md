# Ingegneria del Software 2024

![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Misc/Codex-Copertina.jpg)

## Software Engineering Project - PoliMi

<div align="center">
    <h5> Official deadline: 28/06/2024</h1>
    <h5>A distribuited version of the game CodexNaturalis made by</h5>
    <h5><a href="https://github.com/Slaitroc">Ricci Lorenzo</a></h5>
    <h5><a href="https://github.com/salvoc02">Salvini Christian</a></h5>
    <h5><a href="https://github.com/Krotox">Paoli Matteo</a></h5>
    <h5><a href="https://github.com/AleSarto">Sartori Alessanro</a></h5>
</div>

# Table of Contents

- [Features](#features)
- [How To Use](#how-to-use)
  - [Docker Requirements](#docker-requirements)
    - [1. Pull the Image](#1-Pull-the-Image)
    - [2. Run the Container](#2-Run-the-Container)
    - [3. Run the Application](#3-Run-the-Application)
  - [Git Repo Requirements](#Git-Repo-Requirements)
    - [1. Building the Application .jar File](#1-Building-the-Application-jar-File)
    - [2. Running the Application](#2-Running-the-Application)
  - [Using IntelliJ](#Using-IntelliJ)
- [How to Generate Code Coverage](#How-to-Generate-Code-Coverage)
- [Game Screenshots](#Game-Screenshots)
  - [TUI](#TUI)
  - [GUI](#GUI)
- [Credits](#Credits)

## Features <img src="https://i.ibb.co/RzyJZXm/imp.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">

| Feature                              | Implemented |
| ------------------------------------ | ----------- |
| Simplified Rules                     | ✔️          |
| Complete Rules                       | ✔️          |
| Socket and RMI                       | ✔️          |
| TUI + GUI                            | ✔️          |
| Multiple Games                       | ✔️          |
| Resilience to clients disconnections | ✔️          |
| Server disconnections                | ❌          |
| Chat                                 | ✔️          |

[Requirements PDF](./documents/requirements.pdf)

[RuleBook ENG PDF](./documents/CODEX_Rulebook_EN.pdf)
🔵
[RuleBook IT PDF](./documents/CODEX_Rulebook_IT.pdf)

# How To Use <img src="https://i.ibb.co/QHmskqv/run.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">

### Docker Requirements <img src="./src/main/resources/it/polimi/ingsw/gc31/Images/Misc/docker-brands-solid.svg" alt="Docker" width="20" height="20">

To run the jar app without the project files and with all the needed dependencies, we create a docker image already configured.
The following steps should work in Ubuntu or WLS2.

You need you need [Docker](https://www.docker.com/get-started) installed on your machine.

#### 1\. Pull the Image

- Open a terminal and run:

  ```bash
  docker pull slaitrocka/codex-test:v1.0
  ```

#### 2\. Run the Container

- Allow the container to access the display by running:

  ```bash
  xhost +local:docker
  ```

- run the container using the image and passing the <code>$DISPALY</code> environment variable:
  ```bash
  docker run -it --rm --name codex-naturalis-test -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix  slaitrocka/codex-test:v1.0 /bin/bash
  ```

#### 3\. Run the Application

- Inside the container run:

  ```bash
  gnome-terminal &
  ```

  to open a terminal window with all the special characters correctly configured for the tui version of the game.

- In gnome-terminal you can run the server or the client with the following commands:

  ```bash
  java -jar /app/jarFiles/GC31-1.0-SNAPSHOT-server.jar
  ```

  ```bash
  java -jar /app/jarFiles/GC31-1.0-SNAPSHOT-client.jar
  ```

### Git Repo Requirements

---

To run the application it is required to have correctly installed:

- JDK21: [Java SE Development Kit 21.0.2](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).

- JavaFX: [JavaFX - Gluon](https://gluonhq.com/products/javafx).

- Maven: [Apache Maven 3.9.8](https://maven.apache.org/download.cgi).

### 1\. Building the Application .jar File

- Navigate to the project directory.

  On Windows:

  ```powershell
  cd C:\<path-to-repo>\ing-sw-2024-ricci-salvini-paoli-sartori\
  ```

  On Unix:

  ```bash
  cd <path-to-repo>/ing-sw-2024-ricci-salvini-paoli-sartori/
  ```

- To create the application .jar file relative to your OS, execute the following command in the project root.

  ```
  mvn clean install
  ```

- The compiled jar will be found in the
  `ing-sw-2024-ricci-salvini-paoli-sartori/target`
  folder.

### 2\. Running the Application

- To run the jar file, navigate to the directory containing the jar files.

  On Windows:

  ```powershell
  cd C:\<path-to-repo>\ing-sw-2024-ricci-salvini-paoli-sartori\target
  ```

  On Unix:

  ```bash
  cd <path-to-repo>/ing-sw-2024-ricci-salvini-paoli-sartori/target
  ```

From here, type in the following commands:

- Server:
  ```
  java -jar GC31-1.0-SNAPSHOT-server.jar
  ```
  to force the server ip:
  ```
  java -jar GC31-1.0-SNAPSHOT-server.jar <yourIP>
  ```
- Client
  ```
  java -jar GC31-1.0-SNAPSHOT-server.jar
  ```

### Using IntelliJ

1.  In IntelliJ, navigate to `maven -> GC31 -> Lifecycle -> Clean`
2.  Then, go to `maven -> GC31 -> Lifecycle -> Package`
3.  Open Powershell and navigate to the project directory:

    On Windows:

    ```powershell
    cd C:\<path-to-repo>\ing-sw-2024-ricci-salvini-paoli-sartori\target
    ```

4.  From here, run the following commands:
    - `java -jar softeng-gc04-1.0-SNAPSHOT-client.jar` (to run the client)
    - `java -jar softeng-gc04-1.0-SNAPSHOT-server.jar` (to run the server)

Repeat from step 1 each time the code is modified.

# How to Generate Code Coverage <img src="https://i.ibb.co/4PYzV7D/cov.png" align="right" alt="logo" width="130" height="139" style="border: none; float: right;">

To generate the Code Coverage with JaCoCo for your project, follow these steps:

- Open CMD and navigate to the project directory:

  On Windows:

  ```powershell
  cd C:\<path-to-repo>\ing-sw-2024-ricci-salvini-paoli-sartori
  ```

  On Unix:

  ```bash
  cd /<path-to-repo>/ing-sw-2024-ricci-salvini-paoli-sartori
  ```

- From here, run the following commands:

  ```
  mvn clean verify
  ```

  The JaCoCo Code Coverage report will be generated in `/<path-to-repo>/ing-sw-2024-ricci-salvini-paoli-sartori/target/site/`

# Game Screenshots

### TUI

![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Presentation/tuiExample1.png)
![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Presentation/tuiExample2.png)
![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Presentation/tuiExample3.png)

### GUI

![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Presentation/guiExample1.png)
![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Presentation/guiExample2.png)
![CodexNaturalis](./src/main/resources/it/polimi/ingsw/gc31/Images/Presentation/guiExample3.png)

## Other Features

- Full screen mode responsive

## Credits

This software uses the following open source packages:

- [OpenJfx](https://openjfx.io/)
- [MaterialFX](https://github.com/palexdev/MaterialFX)
- [JUnit](https://junit.org/junit5/)
- [Gson](https://github.com/google/gson)
- [Jansi](https://fusesource.github.io/jansi/)
