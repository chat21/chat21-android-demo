package it.chat21.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chat21.android.Chat;
import it.frontiere21.android.chat21.chat21demo.R;

/**
 * A fragment containing the chat.
 * <p>
 * The Chat21 SDK allows to start the chat from a fragment.
 * <p>
 * You have to define a container in which inflate the chat.
 */
public class ChatFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_chat, container, false);

        setHasOptionsMenu(false); // disable fragment option menu

        // starts the chat inside a container
        Chat.showConversationsListFragment(getChildFragmentManager(), R.id.container);

        return rootView;
    }
}