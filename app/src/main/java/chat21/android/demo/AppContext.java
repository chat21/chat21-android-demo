package chat21.android.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import chat21.android.core.ChatManager;
import chat21.android.core.users.models.IChatUser;
import chat21.android.ui.ChatUI;
import chat21.android.ui.contacts.activites.ContactListActivity;
import chat21.android.ui.contacts.listeners.OnCreateGroupClickListener;
import chat21.android.ui.conversations.listeners.OnNewConversationClickListener;
import chat21.android.ui.messages.listeners.OnAttachClickListener;
import chat21.android.utils.IOUtils;

import static chat21.android.core.ChatManager._SERIALIZED_CHAT_CONFIGURATION_LOGGED_USER;

/**
 * Created by stefanodp91 on 25/09/17.
 */

public class AppContext extends Application {
    private static final String TAG = AppContext.class.getName();

    private static AppContext instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initChatSDK();
    }

    public void initChatSDK() {

        //enable persistence must be made before any other usage of FirebaseDatabase instance.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        // it creates the chat configurations
        ChatManager.Configuration mChatConfiguration =
                new ChatManager.Configuration.Builder("tilechat")
//                        .firebaseUrl("https://chat-v2-dev.firebaseio.com/")
                        .build();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // assuming you have a login, check if the logged user (converted to IChatUser) is valid
//        if (currentUser != null) {
        if (currentUser != null) {
            IChatUser iChatUser = (IChatUser) IOUtils.getObjectFromFile(instance, _SERIALIZED_CHAT_CONFIGURATION_LOGGED_USER);

//            IChatUser iChatUser = new ChatUser();
//            iChatUser.setId(currentUser.getUid());
//            iChatUser.setEmail(currentUser.getEmail());

            ChatManager.start(this, mChatConfiguration, iChatUser);
            Log.i(TAG, "chat has been initialized with success");

            ChatUI.getInstance().setContext(instance);
            ChatUI.getInstance().enableGroups(true);

            // set on new conversation click listener
//            final IChatUser support = new ChatUser("support", "Chat21 Support");
            final IChatUser support = null;
            ChatUI.getInstance().setOnNewConversationClickListener(new OnNewConversationClickListener() {
                @Override
                public void onNewConversationClicked() {
                    if (support != null) {
                        ChatUI.getInstance().showDirectConversationActivity(support);
                    } else {
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

        } else {
            Log.w(TAG, "chat can't be initialized because chatUser is null");
        }
    }
}