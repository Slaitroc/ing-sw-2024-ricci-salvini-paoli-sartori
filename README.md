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

## Features <img src="https://i.ibb.co/RzyJZXm/imp.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">

| Feature | Implemented  |
| ---------------- | ---- | 
| Simplified Rules |✔️   |
| Complete Rules   |✔️   | 
| Socket and RMI   |✔️   | 
| TUI + GUI        |✔️   | 
| Multiple Games   |✔️   | 
| Resilience to clients disconnections   | ✔️   | 
| Server disconnections   | ❌   | 
| Chat   | ✔️   | 

[Requirements PDF](./documents/requirements.pdf)

[RuleBook ENG PDF](./documents/CODEX_Rulebook_EN.pdf)
🔵
[RuleBook IT PDF](./documents/CODEX_Rulebook_IT.pdf)


# How To Use <img src="https://i.ibb.co/QHmskqv/run.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">   


<body>
<div class="container">
    <p>The application requires an installation of Java 21. It has been tested on Linux and Windows Operating Systems only.</p>
<h2>Steps to Create and Run the Application</h2>

<h3>1. Building the Application .jar File</h3>
<p>To create the application .jar file relative to your OS, execute the following command in the project root:</p>
<pre><code>mvn clean install</code></pre>
<p>The compiled jar will be found in the <code>ing-sw-2024-ricci-salvini-paoli-sartori/target</code> folder.</p>

<h3>2. Running the Application</h3>
<p>To run the jar file, open the Command Prompt (CMD) and navigate to the directory containing the jar files:</p>
<pre><code>cd C:/[dir]/[folder with the jar files]</code></pre>
<p>From here, type in the following commands:</p>
<ul>
    <li><code>java -jar GC31-1.0-SNAPSHOT-client.jar</code> (to run the client)</li>
    <li><code>java -jar GC31-1.0-SNAPSHOT-server.jar</code> (to run the server)</li>
</ul>

<h3>For Developers Using IntelliJ</h3>
<ol>
    <li>In IntelliJ, navigate to <code>maven -> GC31 -> Lifecycle -> Clean</code></li>
    <li>Then, go to <code>maven -> GC31 -> Lifecycle -> Package</code></li>
    <li>Open CMD and navigate to the project directory:
        <pre><code>cd C:/[dir]/ing-sw-2024-ricci-salvini-paoli-sartori/target</code></pre>
    </li>
    <li>From here, run the following commands:
        <ul>
            <li><code>java -jar softeng-gc04-1.0-SNAPSHOT-client.jar</code> (to run the client)</li>
            <li><code>java -jar softeng-gc04-1.0-SNAPSHOT-server.jar</code> (to run the server)</li>
        </ul>
    </li>
</ol>
<p>Repeat from step 1 each time the code is modified.</p>

<h2>Requirements</h2>
To run the application it is required to have correctly installed:

<p> 1. JDK21: <a href="https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html">Java SE Development Kit 21.0.2</a>.</p>
<p> 2. JavaFX: <a href="https://gluonhq.com/products/javafx">JavaFX - Gluon</a>.</p>
<p> 3. Maven: <a href="https://maven.apache.org/download.cgi">Apache Maven 3.9.8</a>.</p>

</div>
</body>
</html>

# How to Generate Code Coverage <img src="https://i.ibb.co/4PYzV7D/cov.png" align="right" alt="logo" width="130" height="139" style="border: none; float: right;">

To generate the Code Coverage for your project, follow these steps:

<li>Open CMD and navigate to the project directory:
    <pre><code>cd C:/[dir]/ing-sw-2024-ricci-salvini-paoli-sartori</code></pre>
    </li>
    <li>From here, run the following commands:
        <pre><code>mvn clean verify</code></pre>
    </li>
The Code Coverage report will be generated in the following location:
<pre><code>cd C:/[dir]/ing-sw-2024-ricci-salvini-paoli-sartori/target/site/</code></pre>

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
* Full screen mode responsive
