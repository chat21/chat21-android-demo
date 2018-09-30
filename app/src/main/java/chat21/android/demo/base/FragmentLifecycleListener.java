package chat21.android.demo.base;

import android.content.Context;
import android.view.View;

public interface FragmentLifecycleListener {

    void onFragmentListenerAttach(Context context);

    void onFragmentListenerCreate();

    void onFragmentListenerCreateView(View view);

    void onFragmentListenerActivityCreated();

    void onFragmentListenerStart();

    void onFragmentListenerResume();

    void onFragmentListenerPause();

    void onFragmentListenerStop();

    void onFragmentListenerDestroyView();

    void onFragmentListenerDestroy();

    void onFragmentListenerDetach();
}
