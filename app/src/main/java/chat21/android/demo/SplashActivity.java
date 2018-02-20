package chat21.android.demo;

import android.util.Log;

import org.chat21.android.ui.login.activities.ChatSplashActivity;

import static org.chat21.android.utils.DebugConstants.DEBUG_LOGIN;

/**
 * Created by stefanodp91 on 21/12/17.
 */

public class SplashActivity extends ChatSplashActivity {

    @Override
    protected Class<?> getTargetClass() {
        Log.d(DEBUG_LOGIN, "SplashActivity.getTargetClass");
        return TabActivity.class;
    }

//    @Override
//    protected Intent getLoginIntent() {
//        return new Intent(this, ChatLoginActivity.class);
//    }
}