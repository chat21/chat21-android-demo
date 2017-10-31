package it.frontiere21.android.chat21.chat21demo;

import android.app.Application;

import it.smart21.android.chat.Chat;

/**
 * Created by stefanodp91 on 25/09/17.
 */

public class AppContext extends Application {
    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initChatSDK(instance);
    }

    public static AppContext getInstance() {
        return instance;
    }

    private void initChatSDK(Application context) {
        String appId = "chat21_demo";

        // create a chat configurations object
        Chat.Configuration chatConfiguration = new Chat.Configuration
                .Builder(context, appId,
                "userId", "email", "fullName")
//                .contacts(DummyDataManager.getContacts())
                .build();

//        chatConfiguration.setContacts(DummyDataManager.getContacts());

        // init and start the chat
        Chat.initialize(chatConfiguration);
    }
}