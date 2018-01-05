package chat21.android.demo.usage;

import android.util.Log;

import chat21.android.core.ChatManager;
import chat21.android.core.conversations.listeners.ConversationsListener;
import chat21.android.core.conversations.models.Conversation;
import chat21.android.core.exception.ChatRuntimeException;
import chat21.android.core.messages.handlers.ConversationMessagesHandler;
import chat21.android.core.messages.listeners.ConversationMessagesListener;
import chat21.android.core.messages.models.Message;

import static chat21.android.ui.groups.fragments.BottomSheetGroupAdminPanelMemberFragment.TAG;

/**
 * Created by andrealeo on 05/01/18.
 */

public class ConversationMessagesHandlerUsage {

    public void usageSimple() {


        ConversationMessagesHandler conversationMessagesHandler = ChatManager.getInstance().getConversationMessagesHandler("UID", "Andrea Leo");

        ConversationMessagesListener conversationMessagesListener = new ConversationMessagesListener() {
            @Override
            public void onConversationMessageReceived(Message message, ChatRuntimeException e) {
                Log.d(TAG, "onConversationMessageReceived" + message);

            }

            @Override
            public void onConversationMessageChanged(Message message, ChatRuntimeException e) {
                Log.d(TAG, "onConversationMessageChanged" + message);
            }
        };

        conversationMessagesHandler.connect(conversationMessagesListener);



        // Remember to remove the listener
        conversationMessagesHandler.removeConversationMessagesListener(conversationMessagesListener);
        //or
        conversationMessagesHandler.removeAllConversationMessagesListeners();

    }


    public void usage() {
        ConversationMessagesHandler conversationMessagesHandler = ChatManager.getInstance().getConversationMessagesHandler("UID", "Andrea Leo");

        conversationMessagesHandler.connect();


        ConversationMessagesListener conversationMessagesListener = new ConversationMessagesListener() {
            @Override
            public void onConversationMessageReceived(Message message, ChatRuntimeException e) {
                Log.d(TAG, "onConversationMessageReceived" + message);

            }

            @Override
            public void onConversationMessageChanged(Message message, ChatRuntimeException e) {
                Log.d(TAG, "onConversationMessageChanged" + message);
            }
        };


        // Remember to remove the listener
        conversationMessagesHandler.removeConversationMessagesListener(conversationMessagesListener);
        //or
        conversationMessagesHandler.removeAllConversationMessagesListeners();

    }
}
