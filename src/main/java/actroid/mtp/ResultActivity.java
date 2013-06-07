/**
 * ResultActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wiz.android.button.ButtonManager;
import wiz.android.util.UncaughtExceptionHandlerFactory;
import wiz.project.jan.JanPai;
import wiz.project.jan.MenTsu;
import wiz.project.jan.TenpaiPattern;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * 結果表示画面
 */
public final class ResultActivity extends Activity {
    
    /**
     * コンストラクタ
     */
    public ResultActivity() {
    }
    
    
    
    /**
     * 画面生成時の処理
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        final UncaughtExceptionHandlerFactory factory = new UncaughtExceptionHandlerFactory();
        Thread.setDefaultUncaughtExceptionHandler(factory.create(this));
        
        // 初期化順を変更してはならない
        initializeHandView();
        initializeFixedMenTsuView();
        
        final List<TenpaiPattern> patternList = getCreateParam(MTPConst.KEY_TENPAI_PATTERN);
        initializeTenpaiPatternView(patternList);
    }
    
    /**
     * 画面破棄時の処理
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // メイン画面の確認ボタンをクリック可能にする
        ButtonManager.getInstance().unlock(R.id.button_check);
    }
    
    
    
    /**
     * 聴牌パターンを追加
     * 
     * @param patternView 聴牌パターンビュー。
     * @param pattern 聴牌パターン。
     */
    private void addTenpaiPattern(final LinearLayout patternView, final TenpaiPattern pattern) {
        final JanPai discard = pattern.getDiscard();
        final List<JanPai> completableList = pattern.getCompletableList();
        final Map<JanPai, Integer> expectation = pattern.getExpectation();
        final View discardView = createDiscardView(discard);
        final View completableView = createCompletableView(completableList, expectation, completableList.contains(discard));
        final View expectationView = createExpectationView(expectation);
        
        // 上下に余白を設ける
        discardView.setPadding(0, 10, 0, 0);
        expectationView.setPadding(0, 0, 0, 10);
        
        // スクロール可能ならば常にスクロールバーを表示
        completableView.setScrollbarFadingEnabled(false);
        
        patternView.addView(discardView);
        patternView.addView(completableView);
        patternView.addView(expectationView);
    }
    
    /**
     * 待ち牌ビューを生成
     * 
     * @param completableList 待ち牌リスト。
     * @param expectation 期待枚数。
     * @param poor フリテンか。
     * @return 待ち牌ビュー。
     */
    private View createCompletableView(final List<JanPai> completableList, final Map<JanPai, Integer> expectation, final boolean poor) {
        final LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setGravity(Gravity.BOTTOM);
        for (final JanPai pai : completableList) {
            final int count = expectation.get(pai);
            if (count != 0) {
                view.addView(createJanPaiImageView(pai));
                view.addView(createTextView(count + " "));
            }
        }
        final String text = poor ? "待ち [フリテン]" : "待ち";
        view.addView(createTextView(text));
        
        final HorizontalScrollView scroll = new HorizontalScrollView(this);
        scroll.addView(view);
        return scroll;
    }
    
    /**
     * 捨て牌ビューを生成
     * 
     * @param pai 捨て牌。
     * @return 捨て牌ビュー。
     */
    private View createDiscardView(final JanPai pai) {
        final LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setGravity(Gravity.BOTTOM);
        view.addView(createJanPaiImageView(pai));
        view.addView(createTextView("切り"));
        return view;
    }
    
    /**
     * 期待枚数ビューを生成
     */
    private View createExpectationView(final Map<JanPai, Integer> expectation) {
        final LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.HORIZONTAL);
        
        int total = 0;
        for (final Map.Entry<JanPai, Integer> entry : expectation.entrySet()) {
            total += entry.getValue();
        }
        view.addView(createTextView("合計期待枚数 ： " + total));
        return view;
    }
    
    /**
     * 確定面子ビューのパラメータを生成
     * 
     * @param baseID 起点ID。
     * @return 確定面子ビューのパラメータ。
     */
    private RelativeLayout.LayoutParams createFixedMenTsuViewParam(final int baseID) {
        final RelativeLayout.LayoutParams param =
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.LEFT_OF, baseID);
        param.addRule(RelativeLayout.ALIGN_BOTTOM, baseID);
        return param;
    }
    
    /**
     * 手牌ビューのパラメータを生成
     * 
     * @param handID 手牌ID。
     * @return 手牌ビューのパラメータ。
     */
    private RelativeLayout.LayoutParams createHandViewParam(final int handID) {
        final int index = handID - MTPConst.HAND_VIEW_RESULT_BASE_ID;
        if (index < 0) {
            throw new IllegalArgumentException("Invalid hand ID - " + handID);
        }
        
        final RelativeLayout.LayoutParams param =
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);
        if (index != 0) {
            param.addRule(RelativeLayout.RIGHT_OF, handID - 1);
        }
        return param;
    }
    
    /**
     * 牌画像ビューを生成
     * 
     * @param pai 生成元雀牌。
     * @return 牌画像ビュー。
     */
    private View createJanPaiImageView(final JanPai pai) {
        final ImageView view = new ImageView(this);
        view.setImageBitmap(ImageResourceManager.getInstance().getImage(pai));
        return view;
    }
    
    /**
     * 面子ビューの基点を生成
     * 
     * @return 面子ビューの基点。
     */
    private LinearLayout createMenTsuViewBase() {
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final RelativeLayout.LayoutParams layoutParam =
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);
        layoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        final int viewID = MTPConst.FIXED_VIEW_RESULT_BASE_ID - 1;
        return factory.createMenTsuMinHeightDummyView(viewID, layoutParam);
    }
    
    /**
     * テキストビューを生成
     * 
     * @param text 生成元テキスト。
     * @return テキストビュー。
     */
    private View createTextView(final String text) {
        final TextView view = new TextView(this);
        view.setText(text);
        return view;
    }
    
    /**
     * ビュー生成オブジェクトを生成
     * 
     * @return ビュー生成オブジェクト。
     */
    private JanPaiViewFactory createJanPaiViewFactory() {
        return new JanPaiViewFactory(this);
    }
    
    /**
     * 画面生成パラメータを取得
     * 
     * @param key パラメータキー。
     * @return 画面生成パラメータ。
     */
    @SuppressWarnings("unchecked")
    private <T> T getCreateParam(final String key) {
        final Object source = getIntent().getExtras().getSerializable(key);
        if (source == null) {
            throw new NullPointerException("Source object is null.");
        }
        return (T)source;
    }
    
    /**
     * 確定面子ビューを初期化
     */
    private void initializeFixedMenTsuView() {
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final List<LinearLayout> viewList = new ArrayList<LinearLayout>();
        
        // 常にカン牌と同じ高さのビュー領域を確保するためのダミー
        final LinearLayout baseView = createMenTsuViewBase();
        viewList.add(baseView);
        
        int count = 0;
        for (final MenTsu menTsu : HandManager.getInstance().getFixedMenTsuList()) {
            final int menTsuID = MTPConst.FIXED_VIEW_RESULT_BASE_ID + count;
            final int baseID = (count == 0) ? baseView.getId() : (menTsuID - 1);
            final RelativeLayout.LayoutParams layoutParam = createFixedMenTsuViewParam(baseID);
            final LinearLayout view = factory.createMenTsuView(menTsu, menTsuID, layoutParam);
            view.setPadding(10, 0, 0, 0);
            viewList.add(view);
            count++;
        }
        
        final RelativeLayout fixedMenTsuView = (RelativeLayout)findViewById(R.id.result_fixed_layout);
        for (final LinearLayout view : viewList) {
            fixedMenTsuView.addView(view);
        }
    }
    
    /**
     * 手牌ビューを初期化
     */
    private void initializeHandView() {
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final List<ImageView> viewList = new ArrayList<ImageView>();
        int count = 0;
        for (final JanPai pai : HandManager.getInstance().getMenZenList()) {
            final int handID = MTPConst.HAND_VIEW_RESULT_BASE_ID + count;
            final RelativeLayout.LayoutParams layoutParam = createHandViewParam(handID);
            final Bitmap image = ImageResourceManager.getInstance().getImage(pai);
            final ImageView view = factory.createJanPaiView(handID, image, layoutParam);
            viewList.add(view);
            count++;
        }
        
        final RelativeLayout handView = (RelativeLayout)findViewById(R.id.result_hand_layout);
        for (final ImageView view : viewList) {
            handView.addView(view);
        }
    }
    
    /**
     * 聴牌パターンビューを初期化
     * 
     * @param patternList 聴牌パターン。
     */
    private void initializeTenpaiPatternView(final List<TenpaiPattern> patternList) {
        final LinearLayout patternView = (LinearLayout)findViewById(R.id.result_pattern_layout);
        for (final TenpaiPattern pattern : patternList) {
            addTenpaiPattern(patternView, pattern);
        }
    }
    
}
