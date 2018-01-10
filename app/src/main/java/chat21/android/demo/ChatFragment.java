package chat21.android.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chat21.android.core.ChatManager;
import chat21.android.core.presence.MyPresenceHandler;
import chat21.android.core.presence.listeners.MyPresenceListener;
import chat21.android.ui.ChatUI;

import static chat21.android.utils.DebugConstants.DEBUG_MY_PRESENCE;
import static chat21.android.utils.DebugConstants.DEBUG_USER_PRESENCE;

/**
 * A fragment containing the chat.
 * <p>
 * The Chat21 SDK allows to start the chat from a fragment.
 * <p>
 * You have to define a container in which inflate the chat.
 */
public class ChatFragment extends Fragment /*implements MyPresenceListener */{

    private static final String TAG = ChatFragment.class.getName();

//    private MyPresenceHandler myPresenceHandler;

    public ChatFragment() {
    }

    /**
     * Returns a new instance of this fragment
     */
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myPresenceHandler = ChatManager.getInstance().getMyPresenceHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_chat, container, false);

        setHasOptionsMenu(false); // disable fragment option menu

        // starts the chat inside a container
        ChatUI.getInstance().showConversationsListFragment(getChildFragmentManager(), R.id.container);

        return rootView;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        myPresenceHandler.upsertPresenceListener(this);
//        Log.d(DEBUG_MY_PRESENCE, "  ConversationListFragment.onCreateView: myPresenceHandler attached");
//        myPresenceHandler.connect();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        myPresenceHandler.removePresenceListener(this);
//        Log.d(DEBUG_MY_PRESENCE, "  ConversationListFragment.onDestroy: myPresenceHandler detached");
//    }
//
//
//
//    @Override
//    public void isLoggedUserOnline(boolean isConnected, String deviceId) {
//        // TODO: 09/01/18
//        Log.d(DEBUG_MY_PRESENCE, "ChatFragment.isUserOnline: " +
//                "isConnected == " + isConnected + ", deviceId == " + deviceId);
//    }
//
//    @Override
//    public void onMyPresenceError(Exception e) {
//        Log.e(DEBUG_MY_PRESENCE, "ChatFragment.onMyPresenceError: " + e.toString());
//    }
}