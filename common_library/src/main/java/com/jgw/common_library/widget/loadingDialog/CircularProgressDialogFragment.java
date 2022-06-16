package com.jgw.common_library.widget.loadingDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.jgw.common_library.R;
import com.jgw.common_library.utils.ResourcesUtils;

public class CircularProgressDialogFragment extends DialogFragment {

    private static final int ANIM_TIME = 50;

    private CircularProgressView mProgressView;
    private TextView mTvProgress;

    private int mProgress;
    private String mTip;
    private int mProgressTextColor = -1;
    private int mTipTextColor = -1;

    /**
     * 获取当前进度
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置当前进度
     *
     * @param progress 当前进度
     */
    public void setProgress(int progress) {
        mProgress = progress;
        mTvProgress.setText(String.format(ResourcesUtils.getString(R.string.circular_progress_dialog_progress), progress, "%"));
        mProgressView.setProgress(mProgress, ANIM_TIME);
    }

    /**
     * 设置初始化进度
     *
     * @param progress 当前进度
     */
    public CircularProgressDialogFragment setDefaultProgress(int progress) {
        mProgress = progress;
        return this;
    }

    public CircularProgressDialogFragment setDefaultProgressTextColor(@ColorRes int rid) {
        mProgressTextColor = rid;
        return this;
    }

    public CircularProgressDialogFragment setDefaultTipTextColor(@ColorRes int rid) {
        mTipTextColor = rid;
        return this;
    }

    public CircularProgressDialogFragment setDefaultTip(String tip) {
        mTip = tip;
        return this;
    }

    /**
     * 初始化后 - 设置背景圆环宽度
     *
     * @param width 背景圆环宽度
     */
    public void setBackCircularWidth(int width) {
        mProgressView.setBackWidth(width);
    }

    /**
     * 初始化后 - 设置背景圆环颜色
     *
     * @param color 背景圆环颜色
     */
    public void setBackCircularColor(@ColorRes int color) {
        mProgressView.setBackColor(color);
    }

    /**
     * 初始化后 - 设置进度圆环宽度
     *
     * @param width 进度圆环宽度
     */
    public void setCircularWidth(int width) {
        mProgressView.setProgWidth(width);
    }

    /**
     * 初始化后 - 设置进度圆环颜色
     *
     * @param color 景圆环颜色
     */
    public void setCircularColor(@ColorRes int color) {
        mProgressView.setProgColor(color);
    }

//    /**
//     * 设置进度圆环颜色(支持渐变色)
//     *
//     * @param startColor 进度圆环开始颜色
//     * @param firstColor 进度圆环结束颜色
//     */
//    public void setCircularColor(@ColorRes int startColor, @ColorRes int firstColor) {
//        mProgressView.setProgColor(startColor, firstColor);
//    }

//    /**
//     * 设置进度圆环颜色(支持渐变色)
//     *
//     * @param colorArray 渐变色集合
//     */
//    public void setCircularColor(@ColorRes int[] colorArray) {
//        mProgressView.setProgColor(colorArray);
//    }

    public static CircularProgressDialogFragment newInstance() {
        return CircularProgressDialogFragment.newInstance(0);
    }

    /**
     *
     * @param progressType -1无限循环
     * @return
     */
    public static CircularProgressDialogFragment newInstance(int progressType) {
        CircularProgressDialogFragment fragment = new CircularProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("progressType",progressType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return null;
        }
        Bundle arguments = getArguments();
        int progressType = 0;
        if (arguments != null) {
            progressType = arguments.getInt("progressType");
        }
        Dialog dialog;
        if (progressType == -1) {
            //无限循环
            dialog = new Dialog(activity,R.style.CustomDialog);
            LayoutInflater inflater = activity.getLayoutInflater();
            View rootView = inflater.inflate(R.layout.dialog_fragment_loading2, null);
            dialog.setContentView(rootView);
        } else {
            //有限进度
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View rootView = inflater.inflate(R.layout.dialog_fragment_loading, null);
            mProgressView = rootView.findViewById(R.id.progress_circular);
            mTvProgress = rootView.findViewById(R.id.tv_progress);
            TextView mTvProgressTip = rootView.findViewById(R.id.tv_progress_tip);

            mTvProgress.setText(String.format(ResourcesUtils.getString(R.string.circular_progress_dialog_progress), mProgress, "%"));
            mTvProgressTip.setText(mTip);
            mProgressView.setProgress(mProgress);

            if (mProgressTextColor != -1) {
                mTvProgress.setTextColor(getResources().getColor(mProgressTextColor));
            }

            if (mTipTextColor != -1) {
                mTvProgressTip.setTextColor(getResources().getColor(mTipTextColor));
            }

            builder.setView(rootView);
            dialog = builder.create();
        }

        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}