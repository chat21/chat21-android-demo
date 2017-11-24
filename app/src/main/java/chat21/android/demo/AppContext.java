package chat21.android.demo;

import android.app.Application;

import chat21.android.Chat;

/**
 * Created by stefanodp91 on 25/09/17.
 */

public class AppContext extends Application {
    private static final String APP_ID = "chat21_demo";

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initChatSDK(instance);
    }

    private void initChatSDK(Application context) {

        // create a chat configurations object
        Chat.Configuration chatConfiguration = new Chat.Configuration
                .Builder(context, APP_ID,
                DummyDataManager.getLoggedUser().getId(),
                DummyDataManager.getLoggedUser().getEmail(),
                DummyDataManager.getLoggedUser().getFullName())
//                .contacts(DummyDataManager.getContacts())
                .build();

        chatConfiguration.setContacts(DummyDataManager.getContacts());

        // init and start the chat
        Chat.initialize(chatConfiguration);
    }
}