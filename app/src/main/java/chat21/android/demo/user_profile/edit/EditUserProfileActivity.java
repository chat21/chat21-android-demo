package chat21.android.demo.user_profile.edit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.chat21.android.core.exception.ChatRuntimeException;
import org.chat21.android.core.users.models.IChatUser;
import org.chat21.android.utils.image.CropCircleTransformation;

import chat21.android.demo.R;
import chat21.android.demo.base.FragmentLifecycleListener;
import chat21.android.demo.user_profile.edit.mvp.EditUserProfileMvpPresenter;
import chat21.android.demo.user_profile.edit.mvp.EditUserProfileMvpView;
import chat21.android.demo.user_profile.edit.mvp.EditUserProfilePresenter;

/**
 * Created by stefanodp91 on 08/01/18.
 */

public class EditUserProfileActivity extends AppCompatActivity implements EditUserProfileMvpView {

    private ImageView mProfilePicture;
    private TextView mFullName;
    private TextView mEmail;
    private IChatUser mLoggedUser;

    private EditUserProfileMvpPresenter<EditUserProfileMvpView> mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        mPresenter = EditUserProfilePresenter.instance();
        mPresenter.onAttach(this);
        mLoggedUser = mPresenter.getLoggedUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProfilePicture = findViewById(R.id.profile_picture);
        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);

        // change photo
        // TODO: 30/09/2018 open a material floating bottomsheet
        findViewById(R.id.change_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditUserProfileActivity.this, "change_photo", Toast.LENGTH_SHORT).show();
            }
        });

        // change fullname
        // TODO: 30/09/2018 click only on the image, not on the text
        findViewById(R.id.fullname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditUserProfileActivity.this, "change_fullname", Toast.LENGTH_SHORT).show();
            }
        });

        // change email
        // TODO: 30/09/2018 click only on the image, not on the text
        findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditUserProfileActivity.this, "change_email", Toast.LENGTH_SHORT).show();
            }
        });

        updateUserUI(mLoggedUser);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    private void updateUserUI(IChatUser user) {
        // profile picture
        Glide.with(getApplicationContext())
                .load(user.getProfilePictureUrl())
                .placeholder(R.drawable.ic_person_avatar)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(mProfilePicture);

        // fullname
        mFullName.setText(user.getFullName());

        // email
        mEmail.setText(user.getEmail());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addFragmentLifecycleListener(FragmentLifecycleListener fragmentLifecycleListener) {
//        do nothing
    }
}
