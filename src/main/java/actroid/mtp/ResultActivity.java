/**
 * ResultActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.List;
import java.util.Map;

import wiz.android.util.UncaughtExceptionHandlerFactory;
import wiz.project.jan.JanPai;
import wiz.project.jan.TenpaiPattern;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
     * 
     * @param savedInstanceState 再起動パラメータ。
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        final UncaughtExceptionHandlerFactory factory = new UncaughtExceptionHandlerFactory();
        Thread.setDefaultUncaughtExceptionHandler(factory.create(this));
        
        // 初期化順を変更してはならない
        final List<JanPai> hand = getCreateParam(MTPConst.KEY_HAND);
        initializeHandView(hand);
        
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
        
        // 確認ボタンをクリック可能にする
        CheckButtonManager.getInstance().unlock();
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
     * 手牌ビューを初期化
     * 
     * @param hand 手牌。
     */
    private void initializeHandView(final List<JanPai> hand) {
        final int size = hand.size();
        if (size > 14) {
            throw new IllegalArgumentException("Invalid hand size - " + size);
        }
        for (int i = 0; i < size; i++) {
            final int handID = MTPConst.RESULT_HAND_ID_LIST.get(i);
            final ImageButton button = (ImageButton)findViewById(handID);
            final JanPai pai = hand.get(i);
            final Bitmap image = ImageResourceManager.getInstance().getImage(pai);
            button.setImageBitmap(image);
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
