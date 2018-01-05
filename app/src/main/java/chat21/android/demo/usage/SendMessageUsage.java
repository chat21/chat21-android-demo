package chat21.android.demo.usage;

import android.util.Log;
import android.widget.Toast;

import chat21.android.core.ChatManager;
import chat21.android.core.exception.ChatRuntimeException;
import chat21.android.core.messages.listeners.SendMessageListener;
import chat21.android.core.messages.models.Message;
import chat21.android.ui.messages.activities.MessageListActivity;

import static chat21.android.ui.groups.fragments.BottomSheetGroupAdminPanelMemberFragment.TAG;

/**
 * Created by andrealeo on 05/01/18.
 */

public class SendMessageUsage {

    public void usageSimple() {


        ChatManager.getInstance()
                .sendTextMessage("UID", "Andrea Leo", "hello world!");


    }

    public void usageWithListener() {


        ChatManager.getInstance()
                .sendTextMessage("UID", "Andrea Leo", "hello world!", null,
                        new SendMessageListener() {
                            @Override
                            public void onBeforeMessageSent(Message message, ChatRuntimeException chatException) {
                                    Log.d(TAG, "onBeforeMessageSent called");
                            }

                            @Override
                            public void onMessageSentComplete(Message message, ChatRuntimeException chatException) {
                                if (chatException == null) {
                                    Log.d(TAG, "Message sent: " + message.toString());
                                } else {
                                    Log.e(TAG, "Error sending message : ", chatException);
                                }
                            }
                        });




    }
}
