package com.org.ccl.roundrectimageview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.org.ccl.roundrectimageview.R;

/**
 * Created by chencanlin on 2017/5/26.
 */

public class RoundRectImageView extends ImageView {
    private Paint mPaint;
    private int mSrcSize;
    private Bitmap mBackgroundBitmap;
    private float mXRadiu;
    private float mYRadiu;

    public RoundRectImageView(Context context) {
        this(context, null);
    }

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView);
        mXRadiu = typedArray.getDimension(R.styleable.RoundRectImageView_xradius,0);
        mYRadiu = typedArray.getDimension(R.styleable.RoundRectImageView_yradius,0);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSrcSize = w > h ? h : w;
    }

    private Bitmap makeDst() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        Bitmap dstBitmap = Bitmap.createBitmap(mSrcSize, mSrcSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBitmap);
//        canvas.drawARGB(255, 255, 255, 255);
        RectF rect = new RectF(0, 0, dstBitmap.getWidth(), dstBitmap.getHeight());
        canvas.drawRoundRect(rect, mXRadiu,mYRadiu,paint);
        return dstBitmap;
    }

    private Bitmap makeSrc() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        Drawable drawable = getDrawable();
        Bitmap bitmap = null;
        if (drawable != null) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else if (mBackgroundBitmap != null) {
            bitmap = mBackgroundBitmap;
        }
        return bitmap;
    }

    @Override
    public void setBackground(Drawable background) {
//        super.setBackground(background);
        if (background != null) {
            mBackgroundBitmap = ((BitmapDrawable) background).getBitmap();
        }
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Bitmap dstBitmap = makeDst();
        Bitmap srcBitmap = makeSrc();
        if (srcBitmap == null) {
            return;
        }
        int saveLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        Rect dstBitmapRect = getRect(dstBitmap.getWidth(), dstBitmap.getHeight());
        Rect srcBitmapRect = getRect(srcBitmap.getWidth(), srcBitmap.getHeight());
        Rect screenRect = getRect(getWidth()-30,getHeight()-30);
        canvas.drawBitmap(dstBitmap, dstBitmapRect, screenRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, srcBitmapRect, screenRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);

    }

    @NonNull
    private Rect getRect(int width, int height) {
        int left = width > height ? (width - height) / 2 : 0;
        int right = width > height ? left + height : left + width;
        int top = width > height ? 0 : (height - width) / 2;
        int bottom = width > height ? top + height : top + width;
        return new Rect(left, top, right, bottom);
    }
}
