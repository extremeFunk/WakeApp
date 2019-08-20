# WakeApp
This project is an alarm clock application which run on Android, Windows and Mac.

The user is subscribing to an account and can sync multiple alarm events across different device under their account.
The project utilizes Gradle as building tool. the project has:

  * RestApi server (using Spring boot).
  * Android client and UI.
  *	Windows/Mac client and UI (using Javafx).

The is project structured by modules which form a dependency relationship between them:

  *	core 
    *	common code
    * (folder is found under restapi)
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

