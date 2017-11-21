# Chat21 SDK Documentation 

## Let's see it in action

<img src="https://image.ibb.co/jzFpwR/Screenshot_20171026_120927.png" width="250"> | <img src="https://image.ibb.co/kQuhGR/Screenshot_20171026_120933.png" width="250"> | <img src="https://image.ibb.co/c47PVm/Screenshot_20171026_120940.png" width="250">

<img src="https://image.ibb.co/e8M7i6/Screenshot_20171026_120951.png" width="250"> | <img src="https://image.ibb.co/cUptO6/Screenshot_20171026_121106.png" width="250">


## Pre requisites
It is assumed that you are using an existing Firebase project or that you have created a new one 
on the Firebase console.
if it was not done, follow the [Firebase Documentation](https://firebase.google.com/docs/android/setup) to create a new app on the Firebase console

## Add Chat21 SDK dependencies

### Gradle Scripts

Download [chat21 sdk](https://bitbucket.org/frontiere21/chat21-android-sdk) in your work directory. 

#### /project/build.gradle

add Google Play Service classpath and Google dependencies and sync.


```
buildscript {

    repositories {
        jcenter()
    }

    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0'
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

<div style="text-align:right;">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/build.gradle">build.gradle
    <span style="vertical-align:middle;"><img style="color:#0566D6;"src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
    </a>
</div>


#### /project/settings.gradle

Open your settings.gradle, paste these two lines and sync

```
include ':chat'
project(':chat').projectDir = new File('<CHAT_LIBRARY_FOLDER_PATH>/chat21-android-sdk/chat/')
```

<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/settings.gradle">settings.gradle
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
    </a>
</div>


replace `<CHAT_LIBRARY_FOLDER_PATH>` with your Chat21 SDK folder path.

#### /project/app/build.gradle

##### Android
Set yout minimun SDK at least at ***API 19*** 

Your android should be like this: 

```
android {
    compileSdkVersion 26
    buildToolsVersion "26.0.2"
    
    defaultConfig {
        applicationId "it.frontiere21.android.chat21.chat21demo"
        minSdkVersion 19
        targetSdkVersion 22
        versionCode 1
        versionName "1.0"

        // vectors drawable support
        vectorDrawables.useSupportLibrary = true
        resConfigs "auto"
        
        // multidex support
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
}
```
<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/build.gradle">/app/build.gradle
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
    </a>
</div>

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
<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/build.gradle">/app/build.gradle
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
    </a>
</div>

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


    ```
    <uses-permission android:name="android.permission.INTERNET" />
    
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    ```
    
<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/src/main/AndroidManifest.xml">AndroidManifest.xml
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
    </a>
</div>

#### Application

In your `<application></application>` :

- define your custom application class:
    
    ***this is a mandatory step***. You have to create your own application class in which we'll 
     initialize and add extra customization for the Chat21 SDK

- add the `tools:replace="android:label"` to override the Chat21 SDK app name:
  
    ```
    <application
        android:name=".AppContext"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:replace="android:label"> <!-- add this -->
        
        . . . 
                
    </application>
    ```
    
   <div style="text-align:right">
       <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/src/main/AndroidManifest.xml">AndroidManifest.xml
       <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
       </a>
   </div>
    
   It prevents the error: 
    
    ```
    /android/MyApp/app/src/main/AndroidManifest.xml:30:9 Error:
    Attribute application@label value=(@string/application_name) from AndroidManifest.xml:30:9
    is also present at {Library Name} value=(@string/app_name)
    Suggestion: add 'tools:replace="android:label"' to <application> element at AndroidManifest.xml:26:5 to override
   ```

### Chat21 SDK initialization

The Chat21 SDK provide a ***Chat.Configuration*** object which allows to set some custom behaviour 
and settings for your chat.

To create a new instance of Chat21 SDK you have to create your own configuration (using the
Chat21 SDK Chat.Configuration.Builder) and use it as paramater for the method `Chat.initialize(configuration);` 

```
// create a chat configurations object
Chat.Configuration chatConfiguration = new Chat.Configuration
        .Builder(context, <LOGGED_USER_ID>, <LOGGED_USER_EMAIL>, <LOGGED_USER_FULLNAME>
        .contatcs(<CONTACT_LIST>)
        .build();
        

// init and start the chat
Chat.initialize(chatConfiguration);
```

<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/src/main/java/it/frontiere21/android/chat21/chat21demo/AppContext.java">AppContext.java
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>
    </a>
</div>

Replace:

- `<LOGGED_USER_ID>` with your logged user id;
- `<LOGGED_USER_EMAIL>` with your logged user email;
- `<LOGGED_USER_FULLNAME>` with your logged user display name;
- `<CONTACT_LIST>` is the list of users to chat with;


### Launch your chat

The Chat21 SDK lets you start your chat with both an activity and a inside a fragment.

#### Launch with an activity

It starts a new activity that contains the list of conversations.

```
Chat.showConversationsListActivity();

```

<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/src/main/java/it/frontiere21/android/chat21/chat21demo/HomeFragment.java">HomeFragment.java
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>       
    </a>
</div>

#### Launch with a fragment

You have to create a fragment with a container inside.

The chat will start inside this container where the list of conversations is shown.


```
<android.support.design.widget.CoordinatorLayout 
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <FrameLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</android.support.design.widget.CoordinatorLayout>
```

Now you can show your chat with the following method:

```
 Chat.showConversationsListFragment(getChildFragmentManager(), R.id.container);

```

<div style="text-align:right">
    <a target="_top" href="https://github.com/chat21/chat21-android-demo/blob/master/app/src/main/java/it/frontiere21/android/chat21/chat21demo/ChatFragment.java">ChatFragment.java
    <span><img src="https://github.com/chat21/chat21-android-demo/blob/master/resources/ic_open_in_new_white_24px.svg" alt="open"></span>        
    </a>
</div>

## Limitations


The [sample project](https://github.com/chat21/chat21-android-demo)  uses fake users that do not have real authentication.

For this reason, in the demo, it is ***not*** possible:

- send pictures
- send / receive push notifications

However, image forwarding and push notifications ***are supported*** with real users.
