package chat21.android.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import chat21.android.core.ChatManager;
import chat21.android.core.authentication.task.RefreshFirebaseInstanceIdTask;
import chat21.android.core.users.models.IChatUser;
import chat21.android.ui.ChatUI;
import chat21.android.ui.contacts.activites.ContactListActivity;
import chat21.android.ui.conversations.listeners.OnNewConversationClickListener;
import chat21.android.utils.IOUtils;

import static chat21.android.core.ChatManager._SERIALIZED_CHAT_CONFIGURATION_LOGGED_USER;
import static chat21.android.utils.DebugConstants.DEBUG_NOTIFICATION;

/**
 * Created by stefanodp91 on 25/09/17.
 */

public class AppContext extends Application {
    private static final String TAG = "CHAT21_D_APP_CONTEXT";

    private static AppContext instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "AppContex.attachBaseContext");
        Log.d(DEBUG_NOTIFICATION, "AppContex.attachBaseContext");
        MultiDex.install(this);
        Log.d(TAG, "AppContex.attachBaseContext: MultiDex installed");
        Log.d(DEBUG_NOTIFICATION, "AppContex.attachBaseContext: MultiDex installed");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "AppContex.onCreate");
        Log.d(DEBUG_NOTIFICATION, "AppContex.onCreate");
        instance = this;
        Log.d(TAG, "AppContex.onCreate: instance created");
        Log.d(DEBUG_NOTIFICATION, "AppContex.onCreate: instance created");

        initChatSDK();
    }

    public void initChatSDK() {
        Log.d(TAG, "AppContex.initChatSDK");
        Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK");

        //enable persistence must be made before any other usage of FirebaseDatabase instance.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.d(TAG, "AppContex.initChatSDK: persistence enabled");
        Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: persistence enabled");

        // it creates the chat configurations
        ChatManager.Configuration mChatConfiguration =
                new ChatManager.Configuration.Builder(getString(R.string.chat_firebase_appId))
                        .firebaseUrl(getString(R.string.chat_firebase_url))
                        .storageBucket(getString(R.string.chat_firebase_storage_bucket))
                        .build();
        Log.d(TAG, "AppContex.initChatSDK: chat configuration created");
        Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: chat configuration created");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // assuming you have a login, check if the logged user (converted to IChatUser) is valid
//        if (currentUser != null) {
        if (currentUser != null) {
            IChatUser iChatUser = (IChatUser) IOUtils.getObjectFromFile(instance, _SERIALIZED_CHAT_CONFIGURATION_LOGGED_USER);
            Log.d(TAG, "AppContex.initChatSDK: iChatUser == " + iChatUser.toString());
            Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: iChatUser == " + iChatUser.toString());

//            IChatUser iChatUser = new ChatUser();
//            iChatUser.setId(currentUser.getUid());
//            iChatUser.setEmail(currentUser.getEmail());

            ChatManager.start(this, mChatConfiguration, iChatUser);
            Log.i(TAG, "chat has been initialized with success");
            Log.i(DEBUG_NOTIFICATION, "chat has been initialized with success");

            ChatManager.getInstance().initContactsSyncronizer();
            Log.d(TAG, "AppContex.initChatSDK: contacts syncronizer started within ChatManager");
            Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: contacts syncronizer started within ChatManager");

            // get device token
            new RefreshFirebaseInstanceIdTask().execute();
            Log.d(TAG, "AppContex.initChatSDK:  device token refreshed");
            Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK:  device token refreshed");

            ChatUI.getInstance().setContext(instance);
            Log.d(TAG, "AppContex.initChatSDK: context set within ChatUI");
            Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: context set within ChatUI");
            ChatUI.getInstance().enableGroups(true);
            Log.d(TAG, "AppContex.initChatSDK: groups enabled within ChatUI");
            Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: groups enabled within ChatUI");

            // set on new conversation click listener
//            final IChatUser support = new ChatUser("support", "Chat21 Support");
            final IChatUser support = null;
            ChatUI.getInstance().setOnNewConversationClickListener(new OnNewConversationClickListener() {
                @Override
                public void onNewConversationClicked() {
                    if (support != null) {
                        ChatUI.getInstance().showDirectConversationActivity(support);
                        Log.d(TAG, "AppContex.initChatSDK: support enabled within ChatUI");
                        Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: support enabled within ChatUI");
                    } else {
                        Log.d(TAG, "AppContex.initChatSDK: support not enabled. Starting MessageListActivity enabled within ChatUI");
                        Log.d(DEBUG_NOTIFICATION, "AppContex.initChatSDK: support not enabled. Starting MessageListActivity enabled within ChatUI");
                        Intent intent = new Intent(instance, ContactListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // start activity from context
                        startActivity(intent);
                    }
                }
            });

//            // on attach button click listener
//            ChatUI.getInstance().setOnAttachClickListener(new OnAttachClickListener() {
//                @Override
//                public void onAttachClicked(Object object) {
//                    Toast.makeText(instance, "onAttachClickListener", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            // on create group button click listener
//            ChatUI.getInstance().setOnCreateGroupClickListener(new OnCreateGroupClickListener() {
//                @Override
//                public void onCreateGroupClicked() {
//                    Toast.makeText(instance, "setOnCreateGroupClickListener", Toast.LENGTH_SHORT).show();
//                }
//            });
            Log.i(TAG, "ChatUI has been initialized with success");
            Log.i(DEBUG_NOTIFICATION, "ChatUI has been initialized with success");

        } else {
            Log.w(TAG, "chat can't be initialized because chatUser is null");
            Log.w(DEBUG_NOTIFICATION, "chat can't be initialized because chatUser is null");
        }
    }
}