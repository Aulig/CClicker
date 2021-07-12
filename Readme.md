# Fork Notes

Forked from Col-E/CClicker because (like he mentioned in pom.xml) it wasn't working with Java 9+. 
This fork removes the agent package which seems to have been responsible for the injecting into other JVM processes. Because of that it should be compatible with Java 9+ now. However, not all references to the agent code have been removed, so there's some unnecessary stuff left still.
Also fixed a small bug which made it always show the default settings instead of the loaded ones.

NOW HARDCODED VALUES -> see clickerthread.java because it kept breaking

Build a jar with
mvn clean compile assembly:single

or take it from the releases section.

A nice way to test the clicker is [Riimu's Clicking Speed Test](https://cookie.riimu.net/speed/) because it also shows the randomness.

# About

CClicker is a simple auto-clicker made using JNA and Apache's Math library.

# Features

* Hideable window
* Can be injected into other JVM processes
* [Normal-distribution](https://en.wikipedia.org/wiki/Normal_distribution) based randomization
  * Record your own clicking statistics and simulate it
* All settings accessible via GUI
* Translatable GUI text using txt files

# Images

![Screenshots](shot.png)