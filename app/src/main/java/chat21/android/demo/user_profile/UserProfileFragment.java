package chat21.android.demo.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.chat21.android.core.exception.ChatRuntimeException;
import org.chat21.android.core.users.models.IChatUser;
import org.chat21.android.utils.ChatUtils;
import org.chat21.android.utils.image.CropCircleTransformation;

import chat21.android.demo.R;
import chat21.android.demo.base.BaseFragment;
import chat21.android.demo.user_profile.mvp.UserProfileMvpPresenter;
import chat21.android.demo.user_profile.mvp.UserProfileMvpView;
import chat21.android.demo.user_profile.mvp.UserProfilePresenter;

/**
 * Created by stefanodp91 on 08/01/18.
 */

public class UserProfileFragment extends BaseFragment implements UserProfileMvpView {

    private ImageView mProfilePicture;
    private TextView mFullName;
    private TextView mEmail;
    private TextView mUserId;
    private TextView mAppName;
    private TextView mAppVersion;
    private Button mLogout;

    private IChatUser mLoggedUser;


    private UserProfileMvpPresenter<UserProfileMvpView> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = UserProfilePresenter.instance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mPresenter.onAttach(this);
        mLoggedUser = mPresenter.getLoggedUser();

        setHasOptionsMenu(false); // disable fragment option menu


        return view;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProfilePicture = view.findViewById(R.id.profile_picture);
        mFullName = view.findViewById(R.id.fullname);
        mEmail = view.findViewById(R.id.email);
        mUserId = view.findViewById(R.id.userid);
        mAppName = view.findViewById(R.id.app_name);
        mAppVersion = view.findViewById(R.id.app_version);
        mLogout = view.findViewById(R.id.logout);

        updateUserUI(mLoggedUser);

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
                mPresenter.logout();
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


    @Override
    public void onContactChanged(IChatUser contact, ChatRuntimeException e) {
        if (e == null) {
            if (contact.getId().equals(mLoggedUser.getId())) {
                mLoggedUser = contact;
                updateUserUI(mLoggedUser);
            }
        }
    }

    @Override
    public void onUserLoggedOut() {
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
}
