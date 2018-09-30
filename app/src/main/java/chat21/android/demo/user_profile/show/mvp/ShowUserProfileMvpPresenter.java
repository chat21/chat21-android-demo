package chat21.android.demo.user_profile.show.mvp;


import org.chat21.android.core.users.models.IChatUser;

import chat21.android.demo.base.MvpBasePresenter;

public interface ShowUserProfileMvpPresenter<V extends ShowUserProfileMvpView> extends MvpBasePresenter<V> {

    IChatUser getLoggedUser();

    void logout();

}
