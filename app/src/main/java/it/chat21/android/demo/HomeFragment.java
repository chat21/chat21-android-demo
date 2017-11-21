package it.chat21.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chat21.android.Chat;

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

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.direct_message) {
            onDirectMessageAction();
        } else if (id == R.id.start_chat_activity) {
            onStartChatActivityAction();
        }
    }

    private void onDirectMessageAction() {

        String contactId = DummyDataManager.getContacts().get(3).getId();

        Chat.showDirectConversationActivity(contactId);
    }

    private void onStartChatActivityAction() {

        // stats the chat
        Chat.showConversationsListActivity();
    }
}