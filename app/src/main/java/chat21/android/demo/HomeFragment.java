package chat21.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chat21.android.core.contacts.synchronizers.ContactsFirestoreSynchronizer;
import chat21.android.core.users.models.IChatUser;
import chat21.android.ui.ChatUI;
import chat21.android.ui.contacts.activites.ContactListActivity;

public class HomeFragment extends Fragment {

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
        mStartDirectMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ContactsFirestoreSynchronizer ch = new ContactsFirestoreSynchronizer();
//                ch.getAllContacts();

                Intent intent = new Intent(getActivity().getApplicationContext(), ContactListActivity.class);
                startActivity(intent);
            }
        });

//        // start the chat with an activity
//        Button mStartChatActivity = (Button) rootView.findViewById(R.id.start_chat_activity);
//        mStartChatActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // stats the chat
//                ChatUI.getInstance().openConversationsListActivity();
//            }
//        });

        return rootView;
    }
}