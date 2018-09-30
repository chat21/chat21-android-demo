package chat21.android.demo.base;

public class BasePresenter<V extends MvpBaseView> implements MvpBasePresenter<V> {

    private V mMvpView;

    public BasePresenter() {
        // default constructor
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView =  null;

    }

    @Override
    public boolean isMvpViewAttached() {
        return mMvpView != null;
    }

    @Override
    public V getMvpView() {
        return mMvpView;
    }
}
