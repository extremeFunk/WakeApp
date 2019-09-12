# WakeApp
This application is a multi-device alarm clock that synchronize the timing and behavior of alarm events across different devices (Android, Windows and Mac platforms) communicating through a RESTful web service. 

Users our subscribing to an account and register their devices. Alarm event than can be synchronized between the devises with fixed time intervals (or simultaneously) and can be set from any of device.  

The project utilizes Gradle as a building tool. It implements:
  * RESTful web service (using Spring boot).
  * Android Mobile application.
  *	Windows/Mac Desktop application (using JavaFx).

This is a modular Gradle project. Both server and client side utilize common code (found in the “core” module) and both mobile and desktop application share common code as well (found in the “app” module).

Below is the outline of the module dependency relationship: 

  *	core 
    *	common code
    * (the folder is found under restapi folder)
  *	app
    *	client side abstraction
    *	dependencies -> core
  *	fx
    *	Windows/Mac UI
    *	dependencies -> core & app
  *	android
    *	Android UI
    *	dependencies -> core & app
  *	restapi
    *	server side (Spring boot Application)
    *	dependencies -> core
