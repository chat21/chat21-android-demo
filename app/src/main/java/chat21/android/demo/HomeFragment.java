package chat21.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chat21.android.core.contacts.synchronizer.ContactsFirestoreSynchronizer;
import chat21.android.core.users.models.IChatUser;
import chat21.android.ui.ChatUI;

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

        ContactsFirestoreSynchronizer ch = new ContactsFirestoreSynchronizer();
        ch.getAllContacts();

        //laurice_hoadley
        IChatUser contact = DummyDataManager.getContact();

        ChatUI.getInstance().showDirectConversationActivity(contact);
    }

    private void onStartChatActivityAction() {

//        ChatManager.getInstance().getContactsSynchronizer().setContacts(DummyDataManager.getContacts()); // TODO: 19/12/17 remove this line
//
        //andrea.leo
//        IChatUser andrealeo = new ChatUser("U4HL3GWjBsd8zLX4Vva0s7W2FN92", "Andrea Leo");
//
//        ChatManager.getInstance().getContactsSynchronizer().addContact(andrealeo);
//
        // stats the chat
        ChatUI.getInstance().showConversationsListActivity();
    }
}