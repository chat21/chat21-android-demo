package chat21.android.demo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import chat21.android.core.ChatManager;
import chat21.android.ui.ChatUI;

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

    private void initChatSDK() {

        // it creates the chat configurations
        ChatManager.Configuration mChatConfiguration =
                new ChatManager.Configuration.Builder(getString(R.string.tenant)).firebaseUrl("https://chat-v2-dev.firebaseio.com/").build();

        // assuming you have a login, check if the logged user (converted to IChatUser) is valid
        if (DummyDataManager.getLoggedUser() != null) {
            ChatManager.start(instance, mChatConfiguration, DummyDataManager.getLoggedUser());
            Log.i(TAG, "chat has been initialized with success");
            ChatUI.getInstance().setContext(instance);
            Log.i(TAG, "ChatUI has been initialized with success");
        } else {
            Log.w(TAG, "chat can't be initialized because chatUser is null");
        }
    }
}