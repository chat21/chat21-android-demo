# Chat21 SDK Documentation 

## Pre requisites
It is assumed that you are using an existing Firebase project or that you have created a new one 
on the Firebase console.
if it was not done, follow the [Firebase Documentation](https://firebase.google.com/docs/android/setup) to create a new app on the Firebase console

## Add Chat21 SDK dependencies

### Gradle Scripts

Download [chat21 sdk](https://bitbucket.org/frontiere21/chat21-android-sdk)  and 
[chat21-emojilibrary](https://bitbucket.org/frontiere21/chat21-android-emojilibrary) in your work directory. 

#### /project/build.gradle

add Google Play Service classpath and Google dependencies and sync.

```
buildscript {

    repositories {
        jcenter()
    }

    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'
        classpath 'com.google.gms:google-services:3.0.0'
    }
}

allprojects {
    repositories {
        jcenter()

        maven {
            url "https://maven.google.com" // Google's Maven repository
        }

        maven {
            url 'https://maven.fabric.io/public'
        }
    }
}

. . . 

```

#### /project/settings.gradle

Open your settings.gradle, paste these two lines and sync

```
include ':chat'
project(':chat').projectDir = new File('<CHAT_LIBRARY_FOLDER_PATH>/chat21-android-sdk/chat/')

include ':emoji'
project(':emoji').projectDir = new File('<EMOJI_LIBRARY_FOLDER_PATH>/chat21-android-emojilibrary/emoji/')
```

replace `<EMOJI_LIBRARY_FOLDER_PATH>` with your chat21 folder and emojilibrary folder.

this allow you to load android projects from the file system

#### /project/app/build.gradle

##### Android
- Set yout minimun SDK at least at ***API 19*** 

- enable support for vector drawables

- enable multidex support

- exclude from ***packagingOptions***:
  - 'META-INF/LICENSE'
  - 'META-INF/NOTICE'
  - 'META-INF/license.txt'
  - 'META-INF/notice.txt'
  - 'META-INF/DEPENDENCIES'
  
your android should be like this: 

```
android {
    compileSdkVersion 26
    buildToolsVersion "26.0.2"

    defaultConfig {
        applicationId "it.frontiere21.android.chat21.chat21demo"
        minSdkVersion 19
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        vectorDrawables.useSupportLibrary = true
        resConfigs "auto"
        multiDexEnabled true
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    packagingOptions {
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/DEPENDENCIES'
    }
    
    . . . 
}
````

##### Dependencies 

```
add the following dependencies:

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    // multidex
    compile 'com.android.support:multidex:1.0.1' 
    
    // android
    compile 'com.android.support:appcompat-v7:26.+'
    compile 'com.android.support:design:26.+'
    compile 'com.android.support:cardview-v7:26.+'
    compile 'com.android.support:support-vector-drawable:26.+'

    // google play service
    compile 'com.google.android.gms:play-services:11.4.0'

    // user dependencies
    compile 'com.github.bumptech.glide:glide:3.8.0' // image loading
    // Required only if Facebook login support is required
    compile('com.facebook.android:facebook-android-sdk:4.22.1')
    // chat
    compile project(':chat')
    
    . . . 
}
```

##### Other configurations

paste this block at the bottom of your file

```
configurations.all {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        def requested = details.requested
        if (requested.group == 'com.android.support') {
            if (!requested.name.startsWith("multidex")) {
                details.useVersion '25.3.0'
            }
        }
    }
}
```

##### Google Play Services plugin

Contrary to what is described in the Firebase documentation, you do ***not*** need to 

`apply plugin: 'com.google.gms.google-services'` 

because it has already been applied in the chat module.

For the same reason there is no need to add the `google-services.json` 

### AndroidManifest.xml

Let's set up  the AndroidManifest.xml

#### Permissions

Runtime permissions are currently not supported.

Then you must declare the permissions in the manifest and set the targetSdkVersion at ***API 22*** 

The Chat21 SDK needs the following permissions: 

- Required to perform messaging:

    ```
    <uses-permission android:name="android.permission.INTERNET" />
    
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    ```

- Required to cache internal data and allow images and documents to be sent:

    ```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    ```

#### Application

In your `<application></application>` :

- define your custom application class:
    
    ***this is a mandatory step***. You have to create your own application class in which we'll 
     initialize and add extra customization for the Chat21 SDK

- add the `tools:replace="android:label"` to override the Chat21 SDK app name:

    ***this is a mandatory step***. It prevents the error: 
    
    ```
    /android/MyApp/app/src/main/AndroidManifest.xml:30:9 Error:
    Attribute application@label value=(@string/application_name) from AndroidManifest.xml:30:9
    is also present at {Library Name} value=(@string/app_name)
    Suggestion: add 'tools:replace="android:label"' to <application> element at AndroidManifest.xml:26:5 to override
    ```

Your `<application></application>` should be something like this:

```
<application
            android:name=".AppContext"
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme"
            tools:replace="android:label">
            
            . . . 
            
</application>
```

### Application class 

Create a class which extends the [MultiDexApplication](https://developer.android.com/reference/android/support/multidex/MultiDexApplication.html) class.

### Chat21 SDK initialization

The Chat21 SDK provide a ***Chat.Configuration*** object which allows to set some custom behaviour 
and settings for your chat.

To create a new instance of Chat21 SDK you have to create your own configuration (using the
Chat21 SDK Chat.Configuration.Builder) and use it as paramater for the method 

`Chat.initialize(configuration);`


#### From activity

```
    String appId = "chat21_demo";

    // create a chat configurations object
    Chat.Configuration configuration = new Chat.Configuration
            .Builder(getActivity().getApplicationContext(), loggedUser, contacts)
            .withTenant(appId)
            .build();

    // init and start the chat
    Chat.initialize(configuration);
```


#### From fragment

```
    // retrieve the tenant from the settings
    String appId = "chat21_demo";
    
    // create a chat configurations object
    Chat.Configuration configuration = new Chat.Configuration
            .Builder(getActivity().getApplicationContext(), loggedUser, contacts)
            .startFromConversationFragment(getChildFragmentManager(),
                    container.getId())
            .withTenant(appId)
            .build();
    
    // init and start the chat
    Chat.initialize(configuration);
```

## Limitations


The [sample project](https://github.com/chat21/chat21-android-demo)  uses fake users that do not have real authentication.

For this reason, in the demo, it is ***not*** possible:

- send pictures

- send / receive push notifications

However, image forwarding and push notifications ***are supported*** with real users.