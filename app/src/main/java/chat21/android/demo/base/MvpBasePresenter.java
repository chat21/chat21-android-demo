package chat21.android.demo.base;

public interface MvpBasePresenter<V extends MvpBaseView> {

    void onAttach(V mvpView);

    void onDetach();

    boolean isMvpViewAttached();

    V getMvpView();
}
