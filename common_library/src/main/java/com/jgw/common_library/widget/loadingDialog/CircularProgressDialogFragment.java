package com.jgw.common_library.widget.loadingDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.jgw.common_library.R;
import com.jgw.common_library.bean.ProgressDialogBean;
import com.jgw.common_library.databinding.DialogFragmentCountLoadingBinding;
import com.jgw.common_library.databinding.DialogFragmentCycleLoadingBinding;
import com.jgw.common_library.databinding.DialogFragmentPercentageLoadingBinding;

public class CircularProgressDialogFragment extends DialogFragment {

    private final ProgressDialogBean mProgressDialogBean;

    private DialogInterface.OnDismissListener mOnDismissListener;
    private ViewDataBinding view;
    private int progressType = -1;

    private CircularProgressDialogFragment() {
        mProgressDialogBean = new ProgressDialogBean();
    }

    public static CircularProgressDialogFragment newInstance() {
        return CircularProgressDialogFragment.newInstance(-1);
    }

    /**
     * @param progressType -1无限循环 1百分比进度 2计数进度
     * @return
     */
    public static CircularProgressDialogFragment newInstance(int progressType) {
        CircularProgressDialogFragment fragment = new CircularProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("progressType", progressType);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            setShowsDialog(false);
            //noinspection ConstantConditions
            return null;
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            progressType = arguments.getInt("progressType");
        }
        Dialog dialog = new Dialog(activity,R.style.CustomDialog);
        switch (progressType) {
            case 1:
                view = buildPercentageDialogView(activity);
                break;
            case 2:
                view = buildCountDialogView(activity);
                break;
            default:
                view = buildCycleDialogView(activity);
        }
        dialog.setContentView(view.getRoot());
        return dialog;
    }

    private ViewDataBinding buildCycleDialogView(FragmentActivity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        DialogFragmentCycleLoadingBinding viewDataBinding = DataBindingUtil.inflate(inflater
                , R.layout.dialog_fragment_cycle_loading, null, false);
        viewDataBinding.cpvFragmentLoading.setProgressMode(0);
        viewDataBinding.cpvFragmentLoading.startRotate();
        viewDataBinding.setData(mProgressDialogBean);
        return viewDataBinding;
    }

    private ViewDataBinding buildPercentageDialogView(FragmentActivity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        DialogFragmentPercentageLoadingBinding viewDataBinding = DataBindingUtil.inflate(inflater
                , R.layout.dialog_fragment_percentage_loading, null, false);
        mProgressDialogBean.setTotal(100);
        viewDataBinding.setData(mProgressDialogBean);
        viewDataBinding.cpvDialogFragmentPercentageLoading.setProgressMode(1);
        return viewDataBinding;
    }

    private ViewDataBinding buildCountDialogView(FragmentActivity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        DialogFragmentCountLoadingBinding viewDataBinding = DataBindingUtil.inflate(inflater
                , R.layout.dialog_fragment_count_loading, null, false);
        viewDataBinding.setData(mProgressDialogBean);
        return viewDataBinding;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener mOnDismissListener) {
        this.mOnDismissListener = mOnDismissListener;
    }

    public void setCount(int count) {
        mProgressDialogBean.setCount(count);
    }

    public void setTotal(int total) {
        mProgressDialogBean.setTotal(total);
    }

    public void setTips(String tip) {
        mProgressDialogBean.setTips(tip);
    }

    public ProgressDialogBean getProgressDialogBean() {
        return mProgressDialogBean;
    }


    public int getProgressType() {
        return progressType;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
        if (view instanceof DialogFragmentCycleLoadingBinding) {
            ((DialogFragmentCycleLoadingBinding) view).cpvFragmentLoading.stopRotate();
        }else if ( view instanceof DialogFragmentPercentageLoadingBinding){
            ((DialogFragmentPercentageLoadingBinding) view).cpvDialogFragmentPercentageLoading.stopRotate();
        }
    }
}