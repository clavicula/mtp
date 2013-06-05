/**
 * ResultActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import wiz.android.util.UncaughtExceptionHandlerFactory;
import wiz.project.jan.JanPai;
import wiz.project.jan.MenTsu;
import wiz.project.jan.TenpaiPattern;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;



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
        synchronized (_PATTERN_VIEW_LOCK) {
            initializeTenpaiPatternView(patternList);
        }
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
     * @param pattern 聴牌パターン。
     */
    private void addTenpaiPattern(final TenpaiPattern pattern) {
        final JanPai discard = pattern.getDiscard();
        final List<JanPai> completableList = pattern.getCompletableList();
        final Map<JanPai, Integer> expectation = pattern.getExpectation();
        final View discardView = createDiscardView(discard);
        final View completableView = createCompletableView(completableList, expectation, completableList.contains(discard));
        final View expectationView = createExpectationView(expectation);
        
        // 上下に余白を設ける
        discardView.setPadding(0, 20, 0, 0);
        expectationView.setPadding(0, 0, 0, 20);
        
        _patternView.addView(discardView);
        _patternView.addView(completableView);
        _patternView.addView(expectationView);
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
            view.addView(createJanPaiImageView(pai));
            view.addView(createTextView(expectation.get(pai) + " "));
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
     * 手牌ビューを生成
     * 
     * @param index 手牌インデックス。
     * @param image 手牌画像。
     * @return 手牌画像ビュー。
     */
    private ImageView createHandView(final int index, final Bitmap image) {
        final ImageView view = new ImageView(this);
        view.setId(MTPConst.HAND_VIEW_RESULT_BASE_ID + index);
        view.setLayoutParams(createHandViewParam());
        view.setScaleType(ScaleType.FIT_CENTER);
        view.setImageBitmap(image);
        return view;
    }
    
    /**
     * 確定面子ビューのパラメータを生成
     * 
     * @return 確定面子ビューのパラメータ。
     */
    private LinearLayout.LayoutParams createFixedMenTsuViewParam() {
        final LinearLayout.LayoutParams param =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                          LinearLayout.LayoutParams.WRAP_CONTENT);
        param.weight = 1.0f;
        param.gravity = Gravity.BOTTOM;
        return param;
    }
    
    /**
     * 手牌ビューのパラメータを生成
     * 
     * @return 手牌ビューのパラメータ。
     */
    private LinearLayout.LayoutParams createHandViewParam() {
        final LinearLayout.LayoutParams param =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                          LinearLayout.LayoutParams.MATCH_PARENT);
        param.weight = 1.0f;
        return param;
    }
    
    /**
     * 不可視牌ビューを生成
     * 
     * @return 不可視牌ビュー。
     */
    private ImageView createInvisiblePaiView() {
        final ImageView view = new ImageView(this);
        view.setLayoutParams(createHandViewParam());
        view.setScaleType(ScaleType.FIT_CENTER);
        view.setImageBitmap(ImageResourceManager.getInstance().getBlankImage());
        view.setVisibility(View.INVISIBLE);
        return view;
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
        int count = 0;
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final LinearLayout.LayoutParams layoutParam = createFixedMenTsuViewParam();
        final List<List<ImageView>> viewList = new ArrayList<List<ImageView>>();
        for (final MenTsu menTsu : HandManager.getInstance().getFixedMenTsuList()) {
            final int menTsuID = MTPConst.FIXED_VIEW_RESULT_BASE_ID + count;
            final List<ImageView> view = factory.createMenTsuView(menTsu, menTsuID, layoutParam);
            view.get(0).setPadding(10, 0, 0, 0);
            viewList.add(view);
            count++;
        }
        
        for (; count < 4; count++) {
            // 常に四副露時のサイズで画像を表示するため、不可視のビューを追加
            final List<ImageView> view = factory.createInvisibleMenTsuView(layoutParam);
            view.get(0).setPadding(10, 0, 0, 0);
            viewList.add(view);
        }
        
        // 最初に副露した面子が右に来るように逆順ソート
        Collections.reverse(viewList);
        final LinearLayout fixedMenTsuView = (LinearLayout)findViewById(R.id.result_fixed_layout);
        for (final List<ImageView> view : viewList) {
            for (final ImageView pai : view) {
                fixedMenTsuView.addView(pai);
            }
        }
    }
    
    /**
     * 手牌ビューを初期化
     */
    private void initializeHandView() {
        final LinearLayout handView = (LinearLayout)findViewById(R.id.result_hand_layout);
        int count = 0;
        for (final JanPai pai : HandManager.getInstance().getMenZenList()) {
            final Bitmap image = ImageResourceManager.getInstance().getImage(pai);
            final ImageView view = createHandView(count, image);
            handView.addView(view);
            count++;
        }
        
        for (; count < 14; count++) {
            // 常に14牌時のサイズで画像を表示するため、不可視のビューを追加
            handView.addView(createInvisiblePaiView());
        }
    }
    
    /**
     * 聴牌パターンビューを初期化
     * 
     * @param patternList 聴牌パターン。
     */
    private void initializeTenpaiPatternView(final List<TenpaiPattern> patternList) {
        _patternView = (LinearLayout)findViewById(R.id.result_pattern_layout);
        
        for (final TenpaiPattern pattern : patternList) {
            addTenpaiPattern(pattern);
        }
    }
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _PATTERN_VIEW_LOCK = new Object();
    
    
    
    /**
     * 待ち牌パターンビュー
     */
    private LinearLayout _patternView = null;
    
}
