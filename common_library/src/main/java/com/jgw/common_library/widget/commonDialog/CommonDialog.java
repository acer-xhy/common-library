package com.jgw.common_library.widget.commonDialog;

import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.jgw.common_library.R;
import com.jgw.common_library.base.view.CustomDialog;
import com.jgw.common_library.bean.CommonDialogBean;

public class CommonDialog extends CustomDialog implements View.OnClickListener {

    public static final int TYPE_CONFIRM_DIALOG = 1;
    public static final int TYPE_SELECT_DIALOG = 2;
    public static final int TYPE_INPUT_DIALOG = 3;

    private OnButtonClickListener mOnButtonClickListener;
    private com.jgw.common_library.databinding.DialogFragmentCommonBinding viewDataBinding;
    private final CommonDialogBean mData = new CommonDialogBean();

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public static CommonDialog newInstance(Context context) {
        return newInstance(context, R.style.CustomDialog);
    }

    public static CommonDialog newInstance(Context context, int resId) {
        return new CommonDialog(context, resId);
    }

    private void init() {
        LayoutInflater inflater = getLayoutInflater();
        viewDataBinding = DataBindingUtil.inflate(inflater
                , R.layout.dialog_fragment_common, null, false);
        viewDataBinding.setData(mData);
        viewDataBinding.tvDialogCommonLeft.setOnClickListener(this);
        viewDataBinding.tvDialogCommonRight.setOnClickListener(this);
        viewDataBinding.tvDialogCommonRight.setOnClickListener(this);
        setContentView(viewDataBinding.getRoot());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == viewDataBinding.tvDialogCommonLeft.getId()) {
            onLeftClick();
        } else if (id == viewDataBinding.tvDialogCommonRight.getId()) {
            onRightClick();
        }
    }

    private void onRightClick() {
        switch (mData.getType()) {
            case CommonDialog.TYPE_CONFIRM_DIALOG:
            case CommonDialog.TYPE_SELECT_DIALOG:
                doRightButtonClick();
                break;
            case CommonDialog.TYPE_INPUT_DIALOG:
                hideSoftKeyboard(getContext(), viewDataBinding.etDialogInput);
                doInputRightButtonClick();
                break;
        }
        if (mOnButtonClickListener != null && mOnButtonClickListener.onAutoDismiss()) {
            dismiss();
        }
    }

    private void onLeftClick() {
        switch (mData.getType()) {
            case CommonDialog.TYPE_CONFIRM_DIALOG:
                //do nothing
                break;
            case CommonDialog.TYPE_SELECT_DIALOG:
                doLeftButtonClick();
                break;
            case CommonDialog.TYPE_INPUT_DIALOG:
                hideSoftKeyboard(getContext(), viewDataBinding.etDialogInput);
                break;
        }
        if (mOnButtonClickListener != null && mOnButtonClickListener.onAutoDismiss()) {
            dismiss();
        }
    }

    public CommonDialog setDialogType(int type) {
        mData.setType(type);
        return this;
    }

    public CommonDialog setCustomTitle(CharSequence title) {
        if (title instanceof String) {
            mData.setTitle((String) title);
        } else if (title instanceof Spanned) {
            mData.setTitleSpanned((Spanned) title);
        }
        return this;
    }

    public CommonDialog setDetails(CharSequence details) {
        if (details instanceof String) {
            mData.setDetails((String) details);
        } else if (details instanceof Spanned) {
            mData.setDetailsSpanned((Spanned) details);
        }
        return this;
    }

    public CommonDialog setInputHintText(String hintText) {
        mData.setInputHint(hintText);
        return this;
    }

    public CommonDialog setLeftButtonStr(String leftButtonStr) {
        mData.setLeft(leftButtonStr);
        return this;
    }

    public CommonDialog setRightButtonStr(String rightButtonStr) {
        mData.setRight(rightButtonStr);
        return this;
    }

    public CommonDialog setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
        return this;
    }

    public CommonDialog setOnInputType(int type) {
        viewDataBinding.etDialogInput.setInputType(type);
        return this;
    }

    private void doLeftButtonClick() {
        if (mOnButtonClickListener != null) {
            mOnButtonClickListener.onLeftClick();
        }
    }

    private void doRightButtonClick() {
        if (mOnButtonClickListener != null) {
            mOnButtonClickListener.onRightClick();
        }
    }


    private void doInputRightButtonClick() {
        if (mOnButtonClickListener != null) {
            if (mOnButtonClickListener.onInput(mData.getInput())) {
                mOnButtonClickListener.onRightClick();
            }
        }
    }


    public interface OnButtonClickListener {
        default void onLeftClick() {
        }

        default void onRightClick() {
        }

        default boolean onInput(String input) {
            return true;
        }

        default boolean onAutoDismiss() {
            return true;
        }
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
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}