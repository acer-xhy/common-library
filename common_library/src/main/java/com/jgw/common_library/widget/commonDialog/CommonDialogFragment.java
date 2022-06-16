package com.jgw.common_library.widget.commonDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jgw.common_library.R;

import org.jetbrains.annotations.NotNull;

public class CommonDialogFragment extends DialogFragment {

    // single button & two button
    private static final int TYPE_CONFIRM_DIALOG = 1;
    private static final int TYPE_SELECT_DIALOG = 2;
    private static final int TYPE_INPUT_DIALOG = 3;

    private EditText mEtInput;

    private Spanned mTitle;
    private Spanned mDetails;
    private String mInputHintText;
    private String mLeftButtonStr;
    private String mRightButtonStr;
    private int mTitleTextColor = -1;
    private int mDetailTextColor = -1;

    private int type = TYPE_SELECT_DIALOG;

    private OnButtonClickListener mOnButtonClickListener;
    private OnConfirmClickListener mOnConfirmClickListener;
    private OnInputButtonClickListener mOnInputButtonClickListener;
    private int inputType;

    public int getType() {
        return type;
    }

    public boolean isSelectType() {
        return type == TYPE_SELECT_DIALOG;
    }

    public boolean isConfirmType() {
        return type == TYPE_CONFIRM_DIALOG;
    }

    public boolean isInputType() {
        return type == TYPE_INPUT_DIALOG;
    }

    public CommonDialogFragment setSelectType() {
        type = TYPE_SELECT_DIALOG;
        return this;
    }

    public CommonDialogFragment setConfirmType() {
        type = TYPE_CONFIRM_DIALOG;
        return this;
    }

    public CommonDialogFragment setInputType() {
        type = TYPE_INPUT_DIALOG;
        return this;
    }

    public CommonDialogFragment setTitle(String title) {
        mTitle = new SpannableString(title);
        return this;
    }

    public CommonDialogFragment setTitle(Spanned title) {
        mTitle = title;
        return this;
    }

    public CommonDialogFragment setTitleTextColor(int rid) {
        mTitleTextColor = rid;
        return this;
    }

    public CommonDialogFragment setDetailTextColor(int rid) {
        mDetailTextColor = rid;
        return this;
    }

    public CommonDialogFragment setDetails(String details) {
        mDetails = new SpannableString(details);
        return this;
    }

    public CommonDialogFragment setDetails(Spanned details) {
        mDetails = details;
        return this;
    }

    public CommonDialogFragment setInputHintText(String hintText) {
        mInputHintText = hintText;
        return this;
    }

    public CommonDialogFragment setLeftButtonStr(String leftButtonStr) {
        mLeftButtonStr = leftButtonStr;
        return this;
    }

    public CommonDialogFragment setRightButtonStr(String rightButtonStr) {
        mRightButtonStr = rightButtonStr;
        return this;
    }

    public static CommonDialogFragment newInstance() {
        CommonDialogFragment fragment = new CommonDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CommonDialogFragment setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
        return this;
    }

    public CommonDialogFragment setOnConfirmClickListener(OnConfirmClickListener l) {
        mOnConfirmClickListener = l;
        return this;
    }

    public CommonDialogFragment setOnInputButtonClickListener(OnInputButtonClickListener l) {
        mOnInputButtonClickListener = l;
        return this;
    }

    public CommonDialogFragment setOnInputType(int status) {
        inputType = status;
        return this;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new IllegalStateException();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = activity.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_fragment_common, null);
        TextView mTvTitle = rootView.findViewById(R.id.tv_dialog_title);
        TextView mTvDetails = rootView.findViewById(R.id.tv_dialog_details);
        TextView mTvLeft = rootView.findViewById(R.id.tv_left);
        TextView mTvRight = rootView.findViewById(R.id.tv_right);
        mEtInput = rootView.findViewById(R.id.et_dialog_input);

        mTvTitle.setText(mTitle);
        mTvDetails.setText(mDetails);
        mTvLeft.setText(mLeftButtonStr);
        mTvRight.setText(mRightButtonStr);
        mEtInput.setHint(mInputHintText);
        if (inputType != 0) {
            mEtInput.setInputType(inputType);
        }
        mEtInput.setVisibility(isInputType() ? View.VISIBLE : View.GONE);
        mTvDetails.setVisibility(isInputType() ? View.GONE : View.VISIBLE);

        if (mTitleTextColor != -1) {
            mTvTitle.setTextColor(getResources().getColor(mTitleTextColor));
        }

        if (mDetailTextColor != -1) {
            mTvDetails.setTextColor(getResources().getColor(mDetailTextColor));
        }

        mTvDetails.setVisibility(TextUtils.isEmpty(mDetails) ? View.GONE : View.VISIBLE);
        mTvLeft.setVisibility(isConfirmType() ? View.GONE : View.VISIBLE);

        switch (type) {
            case TYPE_SELECT_DIALOG:
                mTvLeft.setOnClickListener(v -> {
                    doLeftButtonClick(v);
                    dismiss();
                });
                mTvRight.setOnClickListener(v -> {
                    doRightButtonClick(v);
                    dismiss();
                });
                break;
            case TYPE_CONFIRM_DIALOG:
                mTvRight.setOnClickListener(v -> {
                    doConfirmButtonClick(v);
                    dismiss();
                });
                break;
            case TYPE_INPUT_DIALOG:
                mTvLeft.setOnClickListener(v -> {
                    doInputLeftButtonClick(v);
                    hideSoftKeyboard(getContext(), mEtInput);
                    dismiss();
                });
                mTvRight.setOnClickListener(v -> {
                    doInputRightButtonClick(v, mEtInput.getText().toString());
                    hideSoftKeyboard(getContext(), mEtInput);
                    dismiss();
                });
                break;
        }
        builder.setView(rootView);
        return builder.create();
    }

    private void doLeftButtonClick(View v) {
        if (mOnButtonClickListener != null) {
            mOnButtonClickListener.onLeftButtonClick(v);
        }
    }

    private void doRightButtonClick(View v) {
        if (mOnButtonClickListener != null) {
            mOnButtonClickListener.onRightButtonClick(v);
        }
    }

    private void doConfirmButtonClick(View v) {
        if (mOnConfirmClickListener != null) {
            mOnConfirmClickListener.onClick(v);
        }
    }

    private void doInputLeftButtonClick(View v) {
        if (mOnInputButtonClickListener != null) {
            mOnInputButtonClickListener.onLeftButtonClick(v);
        }
    }

    private void doInputRightButtonClick(View v, String input) {
        if (mOnInputButtonClickListener != null) {
            mOnInputButtonClickListener.onRightButtonClick(v, input);
        }
    }

    public interface OnButtonClickListener {
        void onLeftButtonClick(View v);

        void onRightButtonClick(View v);
    }

    public interface OnConfirmClickListener {
        void onClick(View v);
    }

    public interface OnInputButtonClickListener {
        void onLeftButtonClick(View v);

        void onRightButtonClick(View v, String input);
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    private static void hideSoftKeyboard(Context context, View v) {
        if (v == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
//        try {
//            Field dismissed = DialogFragment.class.getDeclaredField("mDismissed");
//            dismissed.setAccessible(true);
//            dismissed.set(this, false);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        try {
//            Field shown = DialogFragment.class.getDeclaredField("mShownByMe");
//            shown.setAccessible(true);
//            shown.set(this, true);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}