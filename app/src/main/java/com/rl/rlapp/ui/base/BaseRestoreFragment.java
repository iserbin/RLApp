package com.rl.rlapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.rl.rlapp.BuildConfig;

public abstract class BaseRestoreFragment extends MvpAppCompatFragment {

    public static final String IS_SHOW = "is_show";
    private static final String TAG = BaseRestoreFragment.class.getSimpleName();
    protected View view;
    private DialogFragment dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onCreate:"));
        }
        if (savedInstanceState != null && getFragmentManager() != null) {
            boolean isShow = savedInstanceState.getBoolean(IS_SHOW, true);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (BuildConfig.DEBUG) {
                Log.d(TAG,  this.getClass().getSimpleName().concat(" restore show:" + isShow));
            }
            if (!isShow) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_SHOW, !isHidden());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG,  getClass().getSimpleName().concat(" onViewStateRestored:"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onCreateView:"));
        }
        view = onCreateContentView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, getClass().getSimpleName().concat(" onViewCreated: "));
        }
        initView(view, savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onStop() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onStop: "));
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onPause: "));
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onResume: "));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onDestroyView "));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            Log.d(TAG,  getClass().getSimpleName().concat(" onDestroy: "));
        }
    }

    public void showDialog(DialogFragment dialog, String tag) {
        Log.d(TAG, " showDialog(): tag[" + tag + "]");
        this.dialog = dialog;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            try {
                fragmentTransaction.remove(prev);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            } catch (Exception ignored) {}
        }
        dialog.show(fragmentManager, tag);
    }

    protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void initView(View root, Bundle savedInstanceState);
    protected abstract void initData(Bundle savedInstanceState);
}

