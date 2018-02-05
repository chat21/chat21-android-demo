package chat21.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import chat21.android.core.ChatManager;
import chat21.android.core.contacts.listeners.ContactListener;
import chat21.android.core.contacts.synchronizers.ContactsSynchronizer;
import chat21.android.core.exception.ChatRuntimeException;
import chat21.android.core.users.models.IChatUser;
import chat21.android.ui.login.listeners.OnLogoutClickListener;
import chat21.android.utils.ChatUtils;
import chat21.android.utils.DebugConstants;
import chat21.android.utils.image.CropCircleTransformation;

import static chat21.android.utils.DebugConstants.DEBUG_CONTACTS_SYNC;

/**
 * Created by stefanodp91 on 08/01/18.
 */

public class UserProfileFragment extends Fragment implements ContactListener {
    private static final String TAG = UserProfileFragment.class.getName();

    private ImageView mProfilePicture;
    private TextView mFullName;
    private TextView mEmail;
    private TextView mUserId;
    private TextView mAppName;
    private TextView mAppVersion;
    private Button mLogout;

    private IChatUser loggedUser;
    private ContactsSynchronizer contactsSynchronizer;

    public UserProfileFragment() {
    }

    /**
     * Returns a new instance of this fragment
     */
    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsSynchronizer = ChatManager.getInstance().getContactsSynchronizer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        setHasOptionsMenu(false); // disable fragment option menu

        mProfilePicture = view.findViewById(R.id.profile_picture);
        mFullName = view.findViewById(R.id.fullname);
        mEmail = view.findViewById(R.id.email);
        mUserId = view.findViewById(R.id.userid);
        mAppName = view.findViewById(R.id.app_name);
        mAppVersion = view.findViewById(R.id.app_version);
        mLogout = view.findViewById(R.id.logout);

        loggedUser = ChatManager.getInstance().getLoggedUser();

        contactsSynchronizer.upsertContactsListener(this);
        Log.d(DEBUG_CONTACTS_SYNC, "  UserProfileFragment.onCreateView: contactsSynchronizer attached");
        contactsSynchronizer.connect();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contactsSynchronizer.removeContactsListener(this);
        Log.d(DEBUG_CONTACTS_SYNC, "  UserProfileFragment.onDestroy: contactsSynchronizer detached");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateUserUI(loggedUser);

        // app name
        mAppName.setText(getString(R.string.app_name));

        // app version
        int versionCode = ChatUtils.getVersionCode(getActivity().getApplicationContext());
        String versionName = ChatUtils.getVersionName(getActivity().getApplicationContext());
        mAppVersion.setText("ver. " + versionName + " build " + versionCode);

        // logout
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 09/01/18 logout must be embebbed inside chatUI
                performLogout(new OnLogoutClickListener() {
                    @Override
                    public void onLogoutClicked() {
                        // get the main activity name from manifest
                        String packageName = getActivity().getApplicationContext().getPackageName();
                        Intent launchIntent = getActivity().getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
                        String className = launchIntent.getComponent().getClassName();
                        try {
                            Class<?> clazz = Class.forName(className);
                            Intent intent = new Intent(getActivity().getApplicationContext(), clazz);
                            // clear the activity stack
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent); // start the main activity
                            getActivity().finish(); // finish this activity
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void updateUserUI(IChatUser user) {
        // profile picture
        Glide.with(getActivity().getApplicationContext())
                .load(user.getProfilePictureUrl())
                .placeholder(R.drawable.ic_person_avatar)
                .bitmapTransform(new CropCircleTransformation(getActivity().getApplicationContext()))
                .into(mProfilePicture);

        // fullname
        mFullName.setText(user.getFullName());

        // email
        mEmail.setText(user.getEmail());

        // user id
        mUserId.setText(user.getId());
    }

    private void performLogout(OnLogoutClickListener onLogoutClickListener) {

        // sign out from firebase
        FirebaseAuth.getInstance().signOut();

//        // check if the user has been really signed out
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Log.e(TAG, "user not signed out");
//        } else {
//            Log.d(TAG, "user signed out with success");
//        }

        ChatManager.getInstance().dispose();

        onLogoutClickListener.onLogoutClicked();
    }

    @Override
    public void onContactReceived(IChatUser contact, ChatRuntimeException e) {
        // do nothing
    }

    @Override
    public void onContactChanged(IChatUser contact, ChatRuntimeException e) {
        if (e == null) {
            if (contact.getId().equals(this.loggedUser.getId())) {
                this.loggedUser = ChatManager.getInstance().getLoggedUser();
                updateUserUI(this.loggedUser);
            }
        }
    }

    @Override
    public void onContactRemoved(IChatUser contact, ChatRuntimeException e) {
        // do nothing
    }
}
