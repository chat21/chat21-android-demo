package it.frontiere21.android.chat21.chat21demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import it.smart21.android.chat.Chat;
import it.smart21.android.chat.user.models.IChatUser;

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

        // container in which inflat the chat
        FrameLayout mContainer = (FrameLayout) rootView.findViewById(R.id.container);

        // retrieves the logged user and contacts list
        startChat(mContainer, DummyDataManager.getLoggedUser(), DummyDataManager.getContacts());

        return rootView;
    }

    // starts the chat inside a container
    private void startChat(View container, IChatUser loggedUser, List<IChatUser> contacts) {

        try {
            // retrieve the tenant from the settings
            String appId = "chat21_demo";

            // create a chat configurations object
            Chat.Configuration configuration = new Chat.Configuration
                    .Builder(getActivity().getApplicationContext(), loggedUser, contacts)
                    .startFromConversationFragment(getChildFragmentManager(),
                            container.getId())
                    .withTenant(appId)
                    .build();

            // init and start the chat
            Chat.initialize(configuration);

            container.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            // TODO: 25/10/17

            container.setVisibility(View.GONE);
        }
    }
}