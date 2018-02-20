package chat21.android.demo.usageui;

import org.chat21.android.ui.ChatUI;

/**
 * Created by andrealeo on 16/01/18.
 */

public class UIUsage {


    public void showConversationsListActivity() {
//        ChatUI.getInstance().setContext(context);
        ChatUI.getInstance().openConversationsListActivity();
    }


    public void showDirectConversationActivity() {
//        ChatUI.getInstance().setContext(context);
        ChatUI.getInstance().openConversationMessagesActivity("UID", "Andrea Leo");
    }

}
