## Project godot_gps .
- gps module for godot v3.1...

## Godot-GPS module

- GPS (global position system) support on Godot engine for Android. (https://github.com/godotengine/)

- Godot version v3.1 ... - download from here: https://github.com/godotengine/godot

## How to:
- Add module to godot code:  

Just copy folder gpsmodule to folder godot/modules/ of godot engine source code.

- Compile godot engine:  
See here:  http://docs.godotengine.org/en/3.0/development/compiling/compiling_for_windows.html  
For Linux:
>scons -j8 platform=x11 target=release_debug  
>scons -j8 platform=android target=release_debug  
>../godot/platform/android/java> gradle build  

 -j8  parametr can be removed. it shows how match CPU COREs do you want to use for compilation.  
Fresh compiled android_debug.apk and android_release.apk files from godot/bin/  
must be added in game progect to the export custom package.  
If you need clean build files:

> scons --clean platform=windows  
> scons --clean platform=x11  
> scons --clean platform=android  

Compilation time is more than 10 minutes.  

- Module using:  
See example project gps_example . Dont forget add to project_name.godod  link to module name:
> [android]
> 
> modules="org/godotengine/godot/GodotGpsModule"





