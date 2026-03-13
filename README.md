<h1>Possibly Enough Elements</h1>
<p>
  A Minecraft Mod for version 1.12.2 <br>
  Designed in Java 1.8.0             <br>
  License: BSD-3-Clause              <br>
</p>

<h2>Features</h2>
<p>
  I) Hydrogen <br>
  II) Helium  <br>
  III) Sodium <br><br>

  Planed to add every element into Minecraft.
</p>

<h2>Prerequisites</h2>
<p>
  Before we can get to building this Mod from source, we need to check what JDK you have installed. <br>
  In your terminal, (xterm, powershell, etc), run the command 
  <blockquote>
    java -version
  </blockquote>
  Now if it returns something around the lines of
  <blockquote>
    java version "1.8.0_451"
  </blockquote>
  You are set skip to the building step. <br>
  However if it prints
  <blockquote>
    java version "17.0.18"
  </blockquote>
  Or something like this
  <blockquote>
    sh: java: command not found
  </blockquote>
  Then you need to install Java 1.8.0. Thankfully it's not rocket science. <br>
</p>

<h2>Java setup</h2>
<p>
  As stated before you need Java 1.8.0 is needed for building. So let's install it. <br>
  Now you could head to <a href=https://www.oracle.com/java/technologies/downloads/>https://www.oracle.com/java/technologies/downloads/</a> for the latest JDK 1.8.0. <br>
  If you want to use OpenJDK you'll either have to compile it from source or use your Unix's package manager. <br>
  Once you have Java 8 installed you have only two paths. If that is the only Java version you have installed you're finished! But if you have another version installed you'll need to set your enviroment variables if you're on Windows. Or your JAVA_HOME on unix. <br>
  Another W for Unix is that setting your JAVA_HOME for that terminal session is esay.
  <blockqoute>
    export JAVA_HOME=/path/to/java8
  </blockqoute>
  Replace 'path/to/java8' with the path of your installiation. 
</p>

<h2>Building</h2>
<p>
  Once you have everything setup building this mod is dead simple! <br>
  On Unix run
  <blockquote>
    ./gradlew build
  </blockquote>
  It should build the mod and deposit it in the /build/libs directory. <br>
  Congrats! You build P.E.E. direct from source. Now put this in your .minecraft/mods folder and enjoy!
</p>
