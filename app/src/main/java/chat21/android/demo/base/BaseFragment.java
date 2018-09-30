package chat21.android.demo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements MvpBaseView {

    private FragmentLifecycleListener mFragmentLifecycleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerCreate();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  super.onCreateView(inflater, container, savedInstanceState);

        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerCreateView(view);

       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerActivityCreated();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerResume();
    }

    @Override
    public void onPause() {
        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerPause();

        super.onPause();
    }

    @Override
    public void onStop() {
        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerStop();

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerDestroyView();

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if(mFragmentLifecycleListener != null)
            mFragmentLifecycleListener.onFragmentListenerDetach();
        super.onDetach();
    }

    @Override
    public void addFragmentLifecycleListener(FragmentLifecycleListener fragmentLifecycleListener) {
        mFragmentLifecycleListener = fragmentLifecycleListener;
    }
}