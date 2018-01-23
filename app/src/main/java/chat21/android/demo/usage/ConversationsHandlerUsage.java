package chat21.android.demo.usage;

import android.util.Log;

import chat21.android.core.ChatManager;
import chat21.android.core.conversations.ConversationsHandler;
import chat21.android.core.conversations.listeners.ConversationsListener;
import chat21.android.core.conversations.models.Conversation;
import chat21.android.core.exception.ChatRuntimeException;

import static chat21.android.ui.groups.fragments.BottomSheetGroupAdminPanelMemberFragment.TAG;

/**
 * Created by andrealeo on 05/01/18.
 */

public class ConversationsHandlerUsage {


    public void usageSimple() {

        ConversationsHandler conversationsHandler = ChatManager.getInstance().getConversationsHandler();

        ConversationsListener conversationsListener = new ConversationsListener() {
            @Override
            public void onConversationAdded(Conversation conversation, ChatRuntimeException e) {
                Log.d(TAG, "onConversationAdded" + conversation);
            }

            @Override
            public void onConversationChanged(Conversation conversation, ChatRuntimeException e) {
                Log.d(TAG, "onConversationChanged" + conversation);
            }

            @Override
            public void onConversationRemoved(ChatRuntimeException e) {
                Log.d(TAG, "onConversationRemoved");
            }
        };


        conversationsHandler.connect(conversationsListener);


        // Remember to remove the listener with
        conversationsHandler.removeConversationsListener(conversationsListener);
        //or with
        conversationsHandler.removeAllConversationsListeners();


    }


    public void usage() {

        ConversationsHandler conversationsHandler = ChatManager.getInstance().getConversationsHandler();

        conversationsHandler.connect();


        ConversationsListener conversationsListener = new ConversationsListener() {
            @Override
            public void onConversationAdded(Conversation conversation, ChatRuntimeException e) {
                Log.d(TAG, "onConversationAdded" + conversation);
            }

            @Override
            public void onConversationChanged(Conversation conversation, ChatRuntimeException e) {
                Log.d(TAG, "onConversationChanged" + conversation);
            }

            @Override
            public void onConversationRemoved(ChatRuntimeException e) {
                Log.d(TAG, "onConversationRemoved");
            }
        };

        conversationsHandler.addConversationsListener(conversationsListener);


       // Remember to remove the listener
        conversationsHandler.removeConversationsListener(conversationsListener);
        //or
        conversationsHandler.removeAllConversationsListeners();


    }
}
