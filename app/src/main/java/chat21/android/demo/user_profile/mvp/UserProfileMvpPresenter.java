package chat21.android.demo.user_profile.mvp;

import org.chat21.android.core.users.models.IChatUser;

import chat21.android.demo.base.MvpBasePresenter;

public interface UserProfileMvpPresenter<V extends UserProfileMvpView> extends MvpBasePresenter<V> {

    IChatUser getLoggedUser();

    void logout();

}
