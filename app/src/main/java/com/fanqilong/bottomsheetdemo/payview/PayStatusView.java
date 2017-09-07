package com.fanqilong.bottomsheetdemo.payview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.fanqilong.bottomsheetdemo.R;

/**
 * Created by fanqilong on 2017/9/6.
 * 支付状态得view
 */

public class PayStatusView extends View {

    public static final int PAY_LOADING = 0;
    public static final int PAY_SUCCESS = 1;
    public static final int PAY_FAILD = 2;


    private int mPayStatu;
    private Context mContext;

    private int mProgressColor;    //进度颜色
    private int mLoadSuccessColor;    //成功的颜色
    private int mLoadFailureColor;   //失败的颜色
    private float mProgressWidth;    //进度宽度
    private float mProgressRadius;   //圆环半径
    private Paint mPaint;

    private int startAngle = -90;
    private int minAngle = -90;
    private int sweepAngle = 120;
    private int curAngle = 0;

    //追踪Path的坐标
    private PathMeasure mPathMeasure;
    //画圆的Path
    private Path mPathCircle;
    //截取PathMeasure中的path
    private Path mPathCircleDst;
    private Path successPath;
    private Path failurePathLeft;
    private Path failurePathRight;

    private ValueAnimator circleAnimator;
    private float circleValue;
    private float successValue;
    private float failValueRight;
    private float failValueLeft;


    public PayStatusView(Context context) {
        this(context, null);
    }

    public PayStatusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PayStatusView);
        mProgressColor = typedArray.getColor(R.styleable.PayStatusView_payStatusView_progress_color, ContextCompat.getColor(context, R.color.colorPrimary));
        mProgressWidth = typedArray.getDimension(R.styleable.PayStatusView_payStatusView_progress_width, 6);
        mProgressRadius = typedArray.getDimension(R.styleable.PayStatusView_payStatusView_progress_radius, 100);
        mLoadSuccessColor = typedArray.getColor(R.styleable.PayStatusView_payStatusView_load_success_color, ContextCompat.getColor(context, R.color.load_success));
        mLoadFailureColor = typedArray.getColor(R.styleable.PayStatusView_payStatusView_load_failure_color, ContextCompat.getColor(context, R.color.load_failure));
        typedArray.recycle();
        initPaint();
        initPath();
        initAnim();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);    //设置画笔为圆角笔触
    }


    private void initPath() {
        mPathCircle = new Path();
        mPathMeasure = new PathMeasure();
        mPathCircleDst = new Path();
        successPath = new Path();
        failurePathLeft = new Path();
        failurePathRight = new Path();
    }

    private void initAnim() {
        circleAnimator = ValueAnimator.ofFloat(0, 1);
        circleAnimator.addUpdateListener((animation) -> {
            circleValue = (float) animation.getAnimatedValue();
            invalidate();
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();
        canvas.translate(paddingLeft + mProgressWidth, paddingTop + mProgressWidth);
        canvas.save();
        //canvas.drawRect(0, 0f, mProgressRadius * 2, mProgressRadius * 2, mPaint);
        if (mPayStatu == PAY_LOADING) {
            //加载中
            if (startAngle == minAngle) {
                sweepAngle += 6;
            }
            if (sweepAngle >= 300 || startAngle > minAngle) {
                startAngle += 6;
                if (sweepAngle > 20) {
                    sweepAngle -= 6;
                }
            }
            if (startAngle > minAngle + 300) {
                startAngle %= 360;
                minAngle = startAngle;
                sweepAngle = 20;
            }
            canvas.rotate(curAngle += 4, mProgressRadius, mProgressRadius);  //旋转的弧长为4
            canvas.drawArc(new RectF(0, 0, mProgressRadius * 2, mProgressRadius * 2), startAngle, sweepAngle, false, mPaint);
            invalidate();

        } else if (mPayStatu == PAY_SUCCESS) {
            //加载成功
            mPaint.setColor(mLoadSuccessColor);
            mPathCircle.addCircle(getWidth() / 2 - paddingRight - paddingLeft, getWidth() / 2 - paddingTop - paddingBottom, mProgressRadius, Path.Direction.CW);
            mPathMeasure.setPath(mPathCircle, false);
            mPathMeasure.getSegment(0, circleValue * mPathMeasure.getLength(), mPathCircleDst, true);   //截取path并保存到mPathCircleDst中
            canvas.drawPath(mPathCircleDst, mPaint);

            if (circleValue == 1) {      //表示圆画完了,可以钩了
                successPath.moveTo(getWidth() / 8 * 3 - paddingRight - paddingLeft, getWidth() / 2 - paddingRight - paddingLeft);
                successPath.lineTo(getWidth() / 2 - paddingRight - paddingLeft, getWidth() / 5 * 3 - paddingRight - paddingLeft);
                successPath.lineTo(getWidth() / 3 * 2 - paddingRight - paddingLeft, getWidth() / 5 * 2 - paddingRight - paddingLeft);
                mPathMeasure.nextContour();
                mPathMeasure.setPath(successPath, false);
                mPathMeasure.getSegment(0, successValue * mPathMeasure.getLength(), mPathCircleDst, true);
                canvas.drawPath(mPathCircleDst, mPaint);
            }

        } else if (mPayStatu == PAY_FAILD) {
            //加载失败
            mPaint.setColor(mLoadFailureColor);
            mPathCircle.addCircle(getWidth() / 2 - paddingRight - paddingLeft, getWidth() / 2 - paddingRight - paddingLeft, mProgressRadius, Path.Direction.CW);
            mPathMeasure.setPath(mPathCircle, false);
            mPathMeasure.getSegment(0, circleValue * mPathMeasure.getLength(), mPathCircleDst, true);
            canvas.drawPath(mPathCircleDst, mPaint);

            if (circleValue == 1) {  //表示圆画完了,可以画叉叉的右边部分
                failurePathRight.moveTo(getWidth() / 3 * 2 - paddingRight - paddingLeft, getWidth() / 3 - paddingRight - paddingLeft);
                failurePathRight.lineTo(getWidth() / 3 - paddingRight - paddingLeft, getWidth() / 3 * 2 - paddingRight - paddingLeft);
                mPathMeasure.nextContour();
                mPathMeasure.setPath(failurePathRight, false);
                mPathMeasure.getSegment(0, failValueRight * mPathMeasure.getLength(), mPathCircleDst, true);
                canvas.drawPath(mPathCircleDst, mPaint);
            }

            if (failValueRight == 1) {    //表示叉叉的右边部分画完了,可以画叉叉的左边部分
                failurePathLeft.moveTo(getWidth() / 3 - paddingRight - paddingLeft, getWidth() / 3 - paddingRight - paddingLeft);
                failurePathLeft.lineTo(getWidth() / 3 * 2 - paddingRight - paddingLeft, getWidth() / 3 * 2 - paddingRight - paddingLeft);
                mPathMeasure.nextContour();
                mPathMeasure.setPath(failurePathLeft, false);
                mPathMeasure.getSegment(0, failValueLeft * mPathMeasure.getLength(), mPathCircleDst, true);
                canvas.drawPath(mPathCircleDst, mPaint);
            }
        }
        canvas.restore();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            //父View要求当前ViewGroup的宽度或者高度必须等于MeasureSpec传入的尺寸
            width = size;
        } else {
            width = (int) (2 * mProgressRadius + 2 * mProgressWidth + getPaddingLeft() + getPaddingRight());
        }

        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            //父View要求当前ViewGroup的宽度或者高度必须等于MeasureSpec传入的尺寸
            height = size;
        } else {
            height = (int) (2 * mProgressRadius + 2 * mProgressWidth + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(width, height);

    }

    private void setStatus(int status) {
        mPayStatu = status;
    }

    public void loadLoading() {
        setStatus(PAY_LOADING);
        invalidate();
    }

    public void loadSuccess() {
        setStatus(PAY_SUCCESS);
        startSuccessAnim();
    }

    public void loadFailure() {
        setStatus(PAY_FAILD);
        startFailAnim();
    }

    private void startSuccessAnim() {
        ValueAnimator success = ValueAnimator.ofFloat(0f, 1.0f);
        success.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                successValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //组合动画,一先一后执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(success).after(circleAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void startFailAnim() {
        ValueAnimator failLeft = ValueAnimator.ofFloat(0f, 1.0f);
        failLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                failValueRight = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator failRight = ValueAnimator.ofFloat(0f, 1.0f);
        failRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                failValueLeft = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //组合动画,一先一后执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(failLeft).after(circleAnimator).before(failRight);
        animatorSet.setDuration(500);
        animatorSet.start();
    }
}
