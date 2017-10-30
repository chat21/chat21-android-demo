package it.frontiere21.android.chat21.chat21demo;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.smart21.android.chat.Chat;
import it.smart21.android.chat.conversations.utils.ConversationUtils;
import it.smart21.android.chat.dao.message.MessageDAO;
import it.smart21.android.chat.dao.message.MessageDAOImpl;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {
    }

    /**
     * Returns a new instance of this fragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_home, container, false);

        // direct message activity
        Button mStartDirectMessage = (Button) rootView.findViewById(R.id.direct_message);
        mStartDirectMessage.setOnClickListener(this);

        // start the chat with an activity
        Button mStartChatActivity = (Button) rootView.findViewById(R.id.start_chat_activity);
        mStartChatActivity.setOnClickListener(this);

        // start group chat
        Button mStartGroupChat = (Button) rootView.findViewById(R.id.start_group_chat);
        mStartGroupChat.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.direct_message) {
            onDirectMessageAction();
        } else if (id == R.id.start_chat_activity) {
            onStartChatActivityAction();
        } else if (id == R.id.start_group_chat) {
            onGroupConversationAction();
        } else if (id == R.id.start_group_chat) {
            onGroupConversationAction();
        }
    }

    private void onDirectMessageAction() {
        String appId = "chat21_demo";

    // generates the conversation id it wants to start
    String conversationId = ConversationUtils.getConversationId(
            DummyDataManager.getLoggedUser().getId(),
            DummyDataManager.getContacts().get(1).getId());

    // create a chat configurations object
    Chat.Configuration configuration = new Chat.Configuration
            .Builder(getActivity().getApplicationContext(),
            DummyDataManager.getLoggedUser(), DummyDataManager.getContacts())
            .withTenant(appId)
            .startFromConversationId(conversationId)
            .build();

    // init and start the chat
        Chat.initialize(configuration);
}

    private void onStartChatActivityAction() {
        String appId = "chat21_demo";

        // create a chat configurations object
        Chat.Configuration configuration = new Chat.Configuration
                .Builder(getActivity().getApplicationContext(),
                DummyDataManager.getLoggedUser(), DummyDataManager.getContacts())
                .withTenant(appId)
                .build();

        // init and start the chat
        Chat.initialize(configuration);
    }

    private void onGroupConversationAction() {
        String appId = "chat21_demo";

        // set a hard coded existent group id
        String conversationId = "-KxSPS-GivDyEYDvemoD";

        // create a chat configurations object
        Chat.Configuration configuration = new Chat.Configuration
                .Builder(getActivity().getApplicationContext(),
                DummyDataManager.getLoggedUser(), DummyDataManager.getContacts())
                .withTenant(appId)
                .startFromConversationId(conversationId)
                .build();

        // init and start the chat
        Chat.initialize(configuration);
    }
}