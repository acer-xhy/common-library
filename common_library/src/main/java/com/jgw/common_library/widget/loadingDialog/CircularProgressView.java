package com.jgw.common_library.widget.loadingDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jgw.common_library.R;
import com.jgw.common_library.base.ui.BaseActivity;
import com.jgw.common_library.http.rxjava.CustomObserver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 圆形进度条控件
 */

public class CircularProgressView extends View {

    private final Paint mBackPaint;
    private final Paint mProgressPaint;   // 绘制画笔
    private float mProgress;      // 圆环进度(0-100)
    private int mRectLength;
    private int mRectL;
    private int mRectT;
    private int progressMode;//0无限旋转 1进度模式
    private Disposable mDisposable;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView);

        // 初始化背景圆环画笔
        mBackPaint = new Paint();
        mBackPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mBackPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mBackPaint.setAntiAlias(true);              // 设置抗锯齿
        mBackPaint.setDither(true);                 // 设置抖动
        mBackPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircularProgressView_backWidth, 5) * BaseActivity.xMultiple);
        mBackPaint.setColor(typedArray.getColor(R.styleable.CircularProgressView_backColor, Color.LTGRAY));

        // 初始化进度圆环画笔
        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mProgressPaint.setAntiAlias(true);              // 设置抗锯齿
        mProgressPaint.setDither(true);                 // 设置抖动
        mProgressPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircularProgressView_backWidth, 5) * BaseActivity.xMultiple);
        mProgressPaint.setColor(typedArray.getColor(R.styleable.CircularProgressView_progColor, Color.BLUE));
        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.CircularProgressView_progress, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        mRectLength = (int) ((Math.min(viewWide, viewHigh)) - (Math.max(mBackPaint.getStrokeWidth(), mProgressPaint.getStrokeWidth())));
        mRectL = getPaddingLeft() + (viewWide - mRectLength) / 2;
        mRectT = getPaddingTop() + (viewHigh - mRectLength) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength, 0, 360, false, mBackPaint);
        canvas.drawArc(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength, getStartAngle(), getSweepAngle(), false, mProgressPaint);
    }

    private float getSweepAngle() {
        if (progressMode == 1) {
            return 360 * mProgress / (100f);
        } else {
            return 90;
        }
    }

    private int getStartAngle() {
        if (progressMode == 1) {
            return 270;
        } else {
            return (int) (mProgress * 3.6);
        }
    }

    public void setProgressMode(int progressMode) {
        this.progressMode = progressMode;
    }

    public void startRotate() {
        Observable.interval(1000 / 60, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Float>() {
                    float frame;

                    @Override
                    public Float apply(Long aLong) {
                        frame = frame > 100 ? 0 : frame;
                        frame++;
                        return frame;
                    }
                })
                .subscribe(new CustomObserver<Float>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Float i) {
                        mProgress = i;
                        invalidate();
                    }
                });
    }

    public void stopRotate() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }

    /**
     * 获取当前进度
     *
     * @return 当前进度（0-100）
     */
    public float getProgress() {
        return mProgress;
    }

    /**
     * 设置当前进度
     * 更新频率每秒更新一次 设置频率应和更新频率一致
     *
     * @param progress 当前进度（0-100）
     */
    public void setProgress(int progress) {
        float v = progress - mProgress;
        if (v == 0) {
            return;
        }
        if (v > 0) {
            setProgressRange((int) v);
        } else {
            mProgress = progress;
            invalidate();
        }
    }

    public void setProgressRange(int range) {
        float currentProgress = this.mProgress;
        ArrayList<Float> floats = new ArrayList<>();
        for (int i = 0; i < range; i++) {
            float f = currentProgress + (range / 10f * i);
            floats.add(f);
        }
        Observable<Long> interval = Observable.interval(10, TimeUnit.MILLISECONDS);
        Observable<Float> floatObservable = Observable.fromIterable(floats);
        Observable.zip(interval, floatObservable, (aLong, aFloat) -> aFloat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomObserver<Float>() {
                    @Override
                    public void onNext(Float f) {
                        mProgress = f;
                        invalidate();
                    }
                });
    }

}