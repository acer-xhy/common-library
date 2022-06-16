package com.jgw.common_library.utils;

import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.jgw.common_library.R;
import com.jgw.common_library.widget.commonDialog.CommonDialogFragment;

public class CommonDialogUtil {

    private static final String TAG_SELECT_DIALOG = "SelectDialogFragment";
    private static final String TAG_CONFIRM_DIALOG = "ConfirmDialogFragment";
    private static final String TAG_INPUT_DIALOG = "InputDialogFragment";

    // double button
    public static void showSelectDialog(@NonNull String title, @NonNull String detail, String leftText, String rightText,
                                        FragmentManager manager, final CommonDialogFragment.OnButtonClickListener listener) {
        showSelectDialog(title, detail, leftText, rightText, false, manager, listener);
    }

    public static void showSelectDialog(@NonNull String title, @NonNull String detail, String leftText, String rightText, boolean cancelable,
                                        FragmentManager manager, final CommonDialogFragment.OnButtonClickListener listener) {
        showSelectDialog(new SpannableString(title), new SpannableString(detail), leftText, rightText, cancelable, manager, listener);
    }

    public static void showSelectDialog(Spanned title, Spanned detail, String leftText, String rightText, boolean cancelable,
                                        FragmentManager manager, final CommonDialogFragment.OnButtonClickListener listener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance();
        dialogFragment.setCancelable(false);
        dialogFragment.setTitle(title)
                .setSelectType()
                .setTitleTextColor(R.color.gray_32)
                .setDetails(detail)
                .setDetailTextColor(R.color.gray_33)
                .setLeftButtonStr(leftText)
                .setRightButtonStr(rightText)
                .setOnButtonClickListener(listener)
                .setCancelable(cancelable);
        dialogFragment.show(manager, TAG_SELECT_DIALOG);
    }

    // single button
    public static void showConfirmDialog(String title, String detail, String confirmText,
                                         FragmentManager manager, final CommonDialogFragment.OnConfirmClickListener listener) {
        showConfirmDialog(new SpannableString(title), new SpannableString(detail), confirmText, manager, listener);
    }

    // single button
    public static void showConfirmDialog(Spanned title, Spanned detail, String confirmText,
                                         FragmentManager manager, final CommonDialogFragment.OnConfirmClickListener listener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance();
        dialogFragment.setCancelable(false);
        dialogFragment.setTitle(title)
                .setConfirmType()
                .setTitleTextColor(R.color.gray_32)
                .setDetails(detail)
                .setDetailTextColor(R.color.gray_33)
                .setRightButtonStr(confirmText)
                .setOnConfirmClickListener(listener);
        dialogFragment.show(manager, TAG_CONFIRM_DIALOG);
    }

    // input && double button
    public static void showInputDialog(String title, String hint, String leftText, String rightText,
                                       FragmentManager manager, final CommonDialogFragment.OnInputButtonClickListener listener) {
        showInputDialog(title, hint, leftText, rightText, manager, InputType.TYPE_CLASS_TEXT, listener);
    }

    public static void showInputDialog(String title, String hint, String leftText, String rightText,
                                       FragmentManager manager, int inputType, final CommonDialogFragment.OnInputButtonClickListener listener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance();
        dialogFragment.setCancelable(false);
        dialogFragment.setTitle(title)
                .setInputType()
                .setTitleTextColor(R.color.gray_32)
                .setInputHintText(hint)
                .setLeftButtonStr(leftText)
                .setRightButtonStr(rightText)
                .setOnInputButtonClickListener(listener)
                .setOnInputType(inputType);
        dialogFragment.show(manager, TAG_INPUT_DIALOG);
    }
}
