package chat21.android.demo;

import android.app.Application;
import android.util.Log;

import chat21.android.core.ChatManager;

/**
 * Created by stefanodp91 on 25/09/17.
 */

public class AppContext extends Application {
    private static final String TAG = AppContext.class.getName();

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initChatSDK();
    }

    private void initChatSDK() {

        // it creates the chat configurations
        ChatManager.Configuration mChatConfiguration =
                new ChatManager.Configuration.Builder(getString(R.string.tenant)).build();

        // assuming you have a login, check if the logged user (converted to IChatUser) is valid
        if (DummyDataManager.getLoggedUser() != null) {
            ChatManager.start(instance, mChatConfiguration, DummyDataManager.getLoggedUser());
            Log.i(TAG, "chat has been initialized with success");
        } else {
            Log.w(TAG, "chat can't be initialized because chatUser is null");
        }
    }
}