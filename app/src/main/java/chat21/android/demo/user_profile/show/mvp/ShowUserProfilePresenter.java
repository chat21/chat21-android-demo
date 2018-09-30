package chat21.android.demo.user_profile.show.mvp;


import com.google.firebase.auth.FirebaseAuth;

import org.chat21.android.core.ChatManager;
import org.chat21.android.core.contacts.listeners.ContactListener;
import org.chat21.android.core.contacts.synchronizers.ContactsSynchronizer;
import org.chat21.android.core.exception.ChatRuntimeException;
import org.chat21.android.core.users.models.IChatUser;

import chat21.android.demo.base.BasePresenter;

public class ShowUserProfilePresenter<V extends ShowUserProfileMvpView> extends BasePresenter<V> implements ShowUserProfileMvpPresenter<V> {

    private static volatile ShowUserProfilePresenter mInstance;

    private ContactsSynchronizer mContactsSynchronizer;
    private ContactListener mContactListener;

    //private constructor.
    private ShowUserProfilePresenter() {

        //Prevent form the reflection api.
        if (mInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        mContactsSynchronizer = ChatManager.getInstance().getContactsSynchronizer();

        mContactListener = new ContactListener() {
            @Override
            public void onContactReceived(IChatUser contact, ChatRuntimeException e) {

            }

            @Override
            public void onContactChanged(IChatUser contact, ChatRuntimeException e) {
                getMvpView().onContactChanged(contact, e);
            }

            @Override
            public void onContactRemoved(IChatUser contact, ChatRuntimeException e) {

            }
        };
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        mContactsSynchronizer.upsertContactsListener(mContactListener);
        mContactsSynchronizer.connect();
    }

    @Override
    public void onDetach() {
        if (mContactsSynchronizer != null) {
            mContactsSynchronizer.removeContactsListener(mContactListener);
        }
        super.onDetach();
    }

    public static ShowUserProfilePresenter instance() {
        if (mInstance == null) { //if there is no instance available... create new one
            synchronized (ShowUserProfilePresenter.class) {
                if (mInstance == null) mInstance = new ShowUserProfilePresenter();
            }
        }

        return mInstance;
    }

    @Override
    public IChatUser getLoggedUser() {
        return ChatManager.getInstance().getLoggedUser();
    }

    @Override
    public void logout() {
        // sign out from firebase
        FirebaseAuth.getInstance().signOut();

//        // check if the user has been really signed out
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Log.e(TAG, "user not signed out");
//        } else {
//            Log.d(TAG, "user signed out with success");
//        }

        ChatManager.getInstance().dispose();

        getMvpView().onUserLoggedOut();
    }
}
