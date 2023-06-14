package com.hanjinliang.androidstudy.systemwidget.LevelAnim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 砼行司机端 等级动画 demo
 */
public class LevelAnimActivity extends AppCompatActivity {
    ImageView  starDiamond;
    TextView tvLevel;
    ArrayList<ImageView> allStars = new ArrayList<>();

    ArrayList<ImageView> allCrown = new ArrayList<>();
    ArrayList<ImageView> allDiamond = new ArrayList<>();
    LinearLayout llCrownOrDiamond;
    LinearLayout llStar;

    public boolean cancelAllAnim = false;

    int oldTotalStar = 124;

    int currentStar = 0;
    int currentCrown = 0;
    int currentDiamond = 0;
    int currentLevel = 0;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_anim);
        tvLevel = findViewById(R.id.tvLevel);


        starDiamond= findViewById(R.id.starDiamond);

        llCrownOrDiamond = findViewById(R.id.llCrownOrDiamond);
        llStar = findViewById(R.id.llStar);
        //计算默认的 星星 钻石  皇冠 Level
        currentStar = oldTotalStar%5;
        currentDiamond = oldTotalStar%25 / 5;
        currentCrown = oldTotalStar%125 / 25;
        currentLevel = oldTotalStar/125;

        //默认皇冠
        for(int i=0;i<currentCrown;i++){
            addCrownOrDiamond(1,null);
        }
        //默认钻石
        for(int i=0;i<currentDiamond;i++){
            addCrownOrDiamond(2,null);
        }
        //默认星星
        for(int i=0;i<currentStar;i++){
            addStar( null);
        }

        tvLevel.setText("Lv."+currentLevel);
        setTextColorGradient(tvLevel);
    }



    private static final int DIAMOND_WIDTH = SizeUtils.dp2px(20);
    private static final int DIAMOND_MARGIN = SizeUtils.dp2px(2);

    private static final int STAR_WIDTH = SizeUtils.dp2px(30);
    private static final int STAR_MARGIN = SizeUtils.dp2px(5);

    private static final Bitmap BITMAP_STAR = ImageUtils.getBitmap(R.drawable.level_star);
    private static final Bitmap BITMAP_DIAMOND = ImageUtils.getBitmap(R.drawable.level_diamond);
    private static final Bitmap BITMAP_CROWN = ImageUtils.getBitmap(R.drawable.level_crown);

    int addedStar;
    public void startAnim(View v) {
        int grow = 17;
        addedStar = 0;
        cancelAllAnim = false;
        addStar(new LevelAnimCallBack() {
            @Override
            public void onAnimFinished() {
                addedStar++;

                if(addedStar < grow){
                    addStar(this);
                }else {
                    LogUtils.e("结束动画");
                    for(int i=0;i<llCrownOrDiamond.getChildCount();i++){
                        if(llCrownOrDiamond.getChildAt(i).getVisibility()==View.INVISIBLE) {
                            llCrownOrDiamond.getChildAt(i).setVisibility(View.GONE);
                            //llCrownOrDiamond.removeViews(i, llCrownOrDiamond.getChildCount() - i);
                            //break;
                        }
                    }
                }
            }
        });

    }

    private void shakeView(View view,  Animator.AnimatorListener animatorListener) {
        int moveTime = 300;
        view.setPivotX(view.getLayoutParams().width / 2.0f);
        view.setPivotY(view.getLayoutParams().height / 2.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 0.6f, 1.8f, 1f).setDuration(moveTime),
                ObjectAnimator.ofFloat(view, "scaleY", 0.6f, 1.8f, 1f).setDuration(moveTime));
        if(animatorListener!=null){
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();
    }

    private void starUpLevelAnim(LevelAnimCallBack levelAnimCallBack){
        int moveTime = 300;
        int[] location1 = new int[2];
        allStars.get(0).getLocationOnScreen(location1);
        int[] location2 = new int[2];
        allStars.get(1).getLocationOnScreen(location2);
        int[] location3 = new int[2];
        allStars.get(2).getLocationOnScreen(location3);

        int star1MoveDistance = location3[0] - location1[0];
        int star2MoveDistance = location3[0] - location2[0];

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(allStars.get(0), "translationX", 0, star1MoveDistance).setDuration(moveTime),
                ObjectAnimator.ofFloat(allStars.get(1), "translationX", 0,star2MoveDistance).setDuration(moveTime),
                ObjectAnimator.ofFloat(allStars.get(3), "translationX", 0,-star2MoveDistance).setDuration(moveTime),
                ObjectAnimator.ofFloat(allStars.get(4), "translationX", 0,-star1MoveDistance).setDuration(moveTime));
        animatorSet.addListener(new SimpleAnimatorCallBack() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                //星星聚合完毕  变成钻石
                for(ImageView star:allStars){
                    star.setVisibility(View.GONE);
                    llStar.removeView(star);
                }
                allStars.clear();

                starUp2Diamond(levelAnimCallBack);

            }

        });
        animatorSet.start();
    }


    /**
     * 新增皇冠 或 钻石
     * @param type 1皇冠 2 钻石
     */
    public void addCrownOrDiamond(int type,LevelAnimCallBack levelAnimCallBack){
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DIAMOND_WIDTH, DIAMOND_WIDTH);
        layoutParams.setMargins(DIAMOND_MARGIN,0,DIAMOND_MARGIN,0);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(type==1?BITMAP_CROWN:BITMAP_DIAMOND);

        llCrownOrDiamond.addView(imageView,type==1?allCrown.size():(allCrown.size()+allDiamond.size()));

        if(type==1){
            allCrown.add(imageView);
        }else if(type==2){
            allDiamond.add(imageView);
        }

        if(allDiamond.size()>=5){//五个钻石需要升级成一个皇冠
            diamondUp2Crown(levelAnimCallBack);
        }else {
            if(levelAnimCallBack!=null){
                levelAnimCallBack.onAnimFinished();
            }
        }


    }

    /**
     * 新增星星
     */
    public void addStar(LevelAnimCallBack levelAnimCallBack){
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(STAR_WIDTH, STAR_WIDTH);
        layoutParams.setMargins(STAR_MARGIN,STAR_MARGIN,STAR_MARGIN,STAR_MARGIN);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(BITMAP_STAR);

        llStar.addView(imageView);
        allStars.add(imageView);
        if(levelAnimCallBack == null){
            return;//表示是第一次添加
        }
        shakeView(imageView, new SimpleAnimatorCallBack() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                if(allStars.size()>=5){//五个星星 升级 到钻石
                    starUpLevelAnim(levelAnimCallBack);
                }else {
                    if(levelAnimCallBack!=null){
                        levelAnimCallBack.onAnimFinished();
                    }
                }
            }

        });
    }

    /**
     * 星星 升级变成 钻石
     */
    public void starUp2Diamond(LevelAnimCallBack levelAnimCallBack){
        int diamondShakeTime = 300;
        starDiamond.setVisibility(View.VISIBLE);

        starDiamond.setPivotX(starDiamond.getWidth() / 2.0f);
        starDiamond.setPivotY(starDiamond.getHeight() / 2.0f);

        int xMove,yMove;
        int[] starDiamondLocation = new int[2];
        starDiamond.getLocationOnScreen(starDiamondLocation);
        int[] lastDiamondLocation = new int[2];//最后一个钻石的位置
        if(llCrownOrDiamond.getChildCount()>0){
            llCrownOrDiamond.getChildAt(llCrownOrDiamond.getChildCount()-1).getLocationOnScreen(lastDiamondLocation);
            xMove = lastDiamondLocation[0] + DIAMOND_MARGIN*2 - starDiamondLocation[0];
        }else {//没有一个钻石或者皇冠
            llCrownOrDiamond.getLocationOnScreen(lastDiamondLocation);
            xMove = lastDiamondLocation[0] + llCrownOrDiamond.getWidth()/2 - DIAMOND_WIDTH/2 - starDiamondLocation[0];
        }
        yMove = lastDiamondLocation[1] - starDiamondLocation[1];
        int moveTime = 300;
        ObjectAnimator translationX = ObjectAnimator.ofFloat(starDiamond, "translationX", 0, xMove).setDuration(moveTime);
        translationX.setStartDelay(moveTime);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(starDiamond, "translationY", 0, yMove).setDuration(moveTime);
        translationY.setStartDelay(moveTime);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(starDiamond, "alpha", 1, 0.2f).setDuration(moveTime);
        alpha.setStartDelay(moveTime);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(starDiamond, "scaleX", 0.6f, 1.5f, 1f).setDuration(diamondShakeTime),
                ObjectAnimator.ofFloat(starDiamond, "scaleY", 0.6f, 1.5f, 1f).setDuration(diamondShakeTime),
                translationX,translationY,alpha);
        animatorSet.addListener(new SimpleAnimatorCallBack() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                //动画结束  新增一个钻石
                addCrownOrDiamond(2,levelAnimCallBack);

                starDiamond.setVisibility(View.INVISIBLE);
                starDiamond.setAlpha(1f);
                starDiamond.setTranslationX(0);
                starDiamond.setTranslationY(0);
            }

        });
        animatorSet.start();
    }

    /**
     * 钻石升级 为 皇冠
     */
    public void diamondUp2Crown(LevelAnimCallBack levelAnimCallBack){
        int diamondShakeTime= 300;
        List<Animator> diamondAnims=new ArrayList<>();

        int[] oneDiamondLocation = new int[2];
        allDiamond.get(0).getLocationOnScreen(oneDiamondLocation);

        for(int i=0;i<allDiamond.size();i++){
            //闪烁动画
            diamondAnims.add(ObjectAnimator.ofFloat(allDiamond.get(i), "alpha", 1f,0.7f,0.3f,0.7f,1f,0.7f,0.3f,0.7f,1f).setDuration(diamondShakeTime));
            //平移动画
            ObjectAnimator translationX = ObjectAnimator.ofFloat(allDiamond.get(i), "translationX", 0, -i*(DIAMOND_WIDTH+DIAMOND_MARGIN*2) ).setDuration(diamondShakeTime);
            translationX.setStartDelay(diamondShakeTime);
            diamondAnims.add(translationX);
        }
        diamondAnims.get(diamondAnims.size()-1).addListener(new SimpleAnimatorCallBack() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                //钻石移动结束
                for(int i= 1;i<allDiamond.size();i++){
                    allDiamond.get(i).setVisibility(View.INVISIBLE);
                    //llCrownOrDiamond.removeView(allDiamond.get(i));
                }
                //第一个钻石变成皇冠
                allDiamond.get(0).setImageBitmap(BITMAP_CROWN);
                allCrown.add(allDiamond.get(0));
                allDiamond.clear();
            }
        });
        //移动到 第一个钻石  合成 皇冠 闪烁动画
        ObjectAnimator firstDiamondAlpha = ObjectAnimator.ofFloat(allDiamond.get(0), "alpha", 1f, 0.7f, 0.3f, 0.7f, 1f, 0.7f, 0.3f, 0.7f, 1f).setDuration(diamondShakeTime);
        firstDiamondAlpha.setStartDelay(diamondShakeTime*2);
        diamondAnims.add(firstDiamondAlpha);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(diamondAnims);

        animatorSet.addListener(new SimpleAnimatorCallBack() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                if(allCrown.size()>=5){//五个皇冠 升级一个level
                    crownUp2Level(levelAnimCallBack);
                }else {
                    levelAnimCallBack.onAnimFinished();
                }
            }

        });
        animatorSet.start();
    }

    /**
     *  皇冠 升级 Level
     */
    public void crownUp2Level(LevelAnimCallBack levelAnimCallBack){
        int diamondShakeTime= 300;
        List<Animator> crownAnims=new ArrayList<>();

        int[] oneDiamondLocation = new int[2];
        allCrown.get(0).getLocationOnScreen(oneDiamondLocation);

        for(int i=0;i<allCrown.size();i++){
            //闪烁动画
            crownAnims.add(ObjectAnimator.ofFloat(allCrown.get(i), "alpha", 1f,0.7f,0.3f,0.7f,1f,0.7f,0.3f,0.7f,1f).setDuration(diamondShakeTime));
            //平移动画
            ObjectAnimator translationX = ObjectAnimator.ofFloat(allCrown.get(i), "translationX", 0, -i*(DIAMOND_WIDTH+DIAMOND_MARGIN*2) ).setDuration(diamondShakeTime);
            translationX.setStartDelay(diamondShakeTime);
            crownAnims.add(translationX);
        }
        //移动到 第一个钻石  合成 皇冠 动画
        allCrown.get(0).setPivotX(allCrown.get(0).getLayoutParams().width / 2.0f);
        allCrown.get(0).setPivotY(allCrown.get(0).getLayoutParams().height / 2.0f);
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(allCrown.get(0), "scaleX", 1f, 1.5f,2.0f).setDuration(diamondShakeTime);
        scaleX.setStartDelay(diamondShakeTime*2);
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(allCrown.get(0), "scaleY", 1f, 1.5f,2.0f).setDuration(diamondShakeTime);
        scaleY.setStartDelay(diamondShakeTime*2);
        crownAnims.add(scaleX);
        crownAnims.add(scaleY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(crownAnims);

        animatorSet.addListener(new SimpleAnimatorCallBack() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                for(int i= 1;i<allCrown.size();i++){
                    allCrown.get(i).setVisibility(View.INVISIBLE);
                    //llCrownOrDiamond.removeView(crown);
                }
                crownMove2Level(levelAnimCallBack);
            }

        });
        animatorSet.start();
    }

    private void crownMove2Level(LevelAnimCallBack levelAnimCallBack){
        List<Animator> crownAnims=new ArrayList<>();

        int crownMoveTime = 300;

        allCrown.get(0).setPivotX(allCrown.get(0).getLayoutParams().width / 2.0f);
        allCrown.get(0).setPivotY(allCrown.get(0).getLayoutParams().height / 2.0f);
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(allCrown.get(0), "scaleX", 2.0f,1.5f,1.0f,0.5f,0).setDuration(crownMoveTime);
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(allCrown.get(0), "scaleY", 2.0f,1.5f,1.0f,0.5f,0).setDuration(crownMoveTime);
        ObjectAnimator alpha=ObjectAnimator.ofFloat(allCrown.get(0), "alpha", 1.0f,0.5f,0.3f,0.0f).setDuration(crownMoveTime);

        crownAnims.add(scaleX);
        crownAnims.add(scaleY);
        crownAnims.add(alpha);


        int[] oneDiamondLocation = new int[2];
        allCrown.get(0).getLocationOnScreen(oneDiamondLocation);

        //移动到--level
        int[] levelLocation = new int[2];//Level的位置
        tvLevel.getLocationOnScreen(levelLocation);

        crownAnims.add(ObjectAnimator.ofFloat(allCrown.get(0), "translationX", 0, levelLocation[0] - oneDiamondLocation[0]).setDuration(crownMoveTime));
        crownAnims.add(ObjectAnimator.ofFloat(allCrown.get(0), "translationY", 0, levelLocation[1] - oneDiamondLocation[1]).setDuration(crownMoveTime));

        //level 放大
        tvLevel.setPivotX(tvLevel.getWidth() / 2.0f);
        tvLevel.setPivotY(tvLevel.getHeight() / 2.0f);
        ObjectAnimator levelScaleX=ObjectAnimator.ofFloat(tvLevel, "scaleX", 1.0f,2.0f,1.0f).setDuration(crownMoveTime);
        ObjectAnimator levelScaleY=ObjectAnimator.ofFloat(tvLevel, "scaleY", 1.0f,2.0f,1.0f).setDuration(crownMoveTime);
        crownAnims.add(levelScaleX);
        crownAnims.add(levelScaleY);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(crownAnims);

        animatorSet.addListener(new SimpleAnimatorCallBack() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if(cancelAllAnim)return;
                for(int i=0;i<llCrownOrDiamond.getChildCount();i++){
                    ((ImageView)llCrownOrDiamond.getChildAt(i)).setImageBitmap(null);
                }
                allCrown.get(0).setVisibility(View.INVISIBLE);
                allCrown.clear();
                //清空布局
                llCrownOrDiamond.removeAllViews();
                //升级Level
                tvLevel.setText("Lv."+ ++currentLevel);

                levelAnimCallBack.onAnimFinished();
            }

        });
        animatorSet.start();
    }

    /**
     * 字体渐变
     * @param textView
     */
    public static void setTextColorGradient(TextView textView) {
        if (textView == null || textView.getContext() == null) {
            return;
        }
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(spec, spec);
        int measuredWidth = textView.getMeasuredWidth();

        // 定义渐变颜色数组 Color.parseColor("#735900"), Color.parseColor("#55430A"),Color.parseColor("#3B2D00")
        int[] colors = {Color.parseColor("#735900"), Color.parseColor("#3B2D00")};
        // 定义渐变的起点和终点
        float[] positions = {0f, 1f};
        // 定义LinearGradient，并指定渐变的起点和终点
        LinearGradient linearGradient = new LinearGradient(0, 0f, measuredWidth, 0f, colors, positions, Shader.TileMode.CLAMP);
        // 设置TextView的渐变颜色
        textView.getPaint().setShader(linearGradient);
        textView.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消动画
        cancelAllAnim = true;
    }

    public interface LevelAnimCallBack{
        public void onAnimFinished();
    }

    /**
     * 简化的回调
     */
    public abstract static class SimpleAnimatorCallBack implements  Animator.AnimatorListener{
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
