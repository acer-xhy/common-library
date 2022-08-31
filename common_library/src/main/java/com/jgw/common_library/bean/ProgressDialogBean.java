package com.jgw.common_library.bean;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.jgw.common_library.BR;
import com.jgw.common_library.utils.MathUtils;

public class ProgressDialogBean extends BaseObservable {
    private String tips;
    private int count;
    private int total;

    @Bindable
    public String getTips() {
        return TextUtils.isEmpty(tips) ? "加载中,请等待..." : tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
        notifyPropertyChanged(BR.tips);
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count > total) {
            count = total;
        }
        this.count = count;
        notifyPropertyChanged(BR.progress);
        notifyPropertyChanged(BR.progressText);
        notifyPropertyChanged(BR.countText);
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if (total < count) {
            total = count;
        }
        this.total = total;
        notifyPropertyChanged(BR.progress);
        notifyPropertyChanged(BR.progressText);
        notifyPropertyChanged(BR.total);
        notifyPropertyChanged(BR.totalText);
    }

    @Bindable
    public String getCountText() {
        return String.valueOf(getCount());
    }

    @Bindable
    public String getTotalText() {
        return String.valueOf(getTotal());
    }

    @Bindable
    public CharSequence getProgressText() {
        String source = getProgress() + "%";
        SpannableString span = new SpannableString(source);
        span.setSpan(new AbsoluteSizeSpan(12, true), source.length() - 1, source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    @Bindable
    public int getProgress() {
        if (total == 0) {
            return 0;
        }
        int i = (int) (MathUtils.div(count, total, 2) * 100);
        return i;
    }

    @Bindable
    public int getProgressVisible() {
        return count == 0 && total == 0 ? View.GONE : View.VISIBLE;
    }
}
