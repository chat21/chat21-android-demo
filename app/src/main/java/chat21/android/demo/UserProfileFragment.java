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
import chat21.android.core.users.models.IChatUser;
import chat21.android.utils.ChatUtils;
import chat21.android.utils.image.CropCircleTransformation;

/**
 * Created by stefanodp91 on 08/01/18.
 */

public class UserProfileFragment extends Fragment {
    private static final String TAG = UserProfileFragment.class.getName();

    private ImageView mProfilePicture;
    private TextView mFullName;
    private TextView mEmail;
    private TextView mUserId;
    private TextView mAppName;
    private TextView mAppVersion;
    private Button mLogout;

    private IChatUser loggedUser;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // profile picture
        Glide.with(getActivity().getApplicationContext())
                .load(loggedUser.getProfilePictureUrl())
                .placeholder(R.drawable.ic_person_avatar)
                .bitmapTransform(new CropCircleTransformation(getActivity().getApplicationContext()))
                .into(mProfilePicture);

        // fullname
        mFullName.setText(loggedUser.getFullName());

        // email
        mEmail.setText(loggedUser.getEmail());

        // user id
        mUserId.setText(loggedUser.getId());

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
                performLogout();
            }
        });
    }

    private void performLogout() {

        // sign out from firebase
        FirebaseAuth.getInstance().signOut();

//        // check if the user has been really signed out
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Log.e(TAG, "user not signed out");
//        } else {
//            Log.d(TAG, "user signed out with success");
//        }

        ChatManager.getInstance().dispose();

        // get the main activity name from manifest
        String packageName = getActivity().getApplicationContext().getPackageName();
        Intent launchIntent = getActivity().getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        try {
            Class<?> clazz = Class.forName(className);
            Intent intent = new Intent(getActivity().getApplicationContext(), clazz);
            // clear the activity stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish(); // finish this activity
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
