package chat21.android.demo.user_profile.edit.mvp;


import org.chat21.android.core.users.models.IChatUser;

import chat21.android.demo.base.MvpBasePresenter;

public interface EditUserProfileMvpPresenter<V extends EditUserProfileMvpView> extends MvpBasePresenter<V> {

    IChatUser getLoggedUser();

}
