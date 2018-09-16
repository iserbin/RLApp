package com.rl.rlapp.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.rl.rlapp.R;

public class RLDialog extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_BODY = "body";
    private DialogListener listener;
    private String title;
    private String body;
    private TextView tvTitle;
    private WebView tvBody;
    private View main;
    private View out;
    private View lOk;

    public RLDialog() {
    }

    public static RLDialog newInstance(Article article) {
        RLDialog dialog = new RLDialog();
        Bundle args = new Bundle();
        String title = article.getTitle();
        if (title != null && !title.isEmpty()) {
            args.putString(ARG_TITLE, title);
        }
        String body = article.getDescription();
        if (body != null && !body.isEmpty()) {
            args.putString(ARG_BODY, body);
        }
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        title = arguments.getString(ARG_TITLE);
        body = arguments.getString(ARG_BODY);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_rl, container, false);
        initAll(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK)) {
                if (listener != null) {
                    dismiss();
                    listener.onBackEvent();
                }
                return true;
            }
            else
                return false;
        });
    }

    public void initAll(View v) {
        findViews(v);
        initTitle();
        initBody();
    }

    public void findViews(View v) {
        main = v.findViewById(R.id.main);
        out = v.findViewById(R.id.out);
        tvTitle = v.findViewById(R.id.title);
        tvBody = v.findViewById(R.id.body);
        lOk = v.findViewById(R.id.lOk);
    }

    public void initBody() {
        if (body != null && !body.isEmpty()) {
            tvBody.setVisibility(View.VISIBLE);
            tvBody.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return true;
                }
            } );
            tvBody.loadData(body, "text/html; charset=UTF-8", null);
        }
        else {
            tvBody.setVisibility(View.GONE);
        }
    }

    public void initTitle() {
        if (title != null && !title.isEmpty()) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main.setOnClickListener(v -> {
            // noop
        });
        out.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDismiss();
            }
            dismiss();
        });

        lOk.setOnClickListener(v -> {
            if (listener != null) {
                listener.onYes();
            }
            dismiss();
        });
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public interface DialogListener {
        void onYes();
        void onBackEvent();
        void onDismiss();
    }
}
