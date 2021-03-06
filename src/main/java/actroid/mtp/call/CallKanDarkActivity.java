/**
 * CallKanDarkActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp.call;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import wiz.android.util.UncaughtExceptionHandlerFactory;
import wiz.project.jan.JanPai;
import wiz.project.jan.MenTsu;
import wiz.project.jan.MenTsuType;
import actroid.mtp.HandManager;
import actroid.mtp.ImageResourceManager;
import actroid.mtp.MTPConst;
import actroid.mtp.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



/**
 * 暗カン画面
 */
public final class CallKanDarkActivity extends Activity {
    
    /**
     * コンストラクタ
     */
    public CallKanDarkActivity() {
    }
    
    
    
    /**
     * 画面生成時の処理
     */
    @Override
    protected void onCreate(final Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_call_kan_dark);
        
        final UncaughtExceptionHandlerFactory factory = new UncaughtExceptionHandlerFactory();
        Thread.setDefaultUncaughtExceptionHandler(factory.create(this));
        
        initializeJanPaiButton();
        initializeCancelButton();
    }
    
    
    
    /**
     * キャンセルボタンを初期化
     */
    private void initializeCancelButton() {
        final Button button = (Button)findViewById(R.id.button_call_kan_dark_cancel);
        button.setOnClickListener(new CancelButtonListener());
    }
    
    /**
     * 雀牌ボタンを初期化
     */
    private void initializeJanPaiButton() {
        final Map<JanPai, Integer> hand = HandManager.getInstance().getAllJanPaiMap();
        for (final Map.Entry<JanPai, Integer> entry : MTPConst.KAN_DARK_JAN_PAI_BUTTON_ID_MAP.entrySet()) {
            final ImageButton button = (ImageButton)findViewById(entry.getValue());
            final JanPai pai = entry.getKey();
            if (hand.get(pai) == 0) {
                // カン可能ならばクリック時の処理を設定
                button.setOnClickListener(new JanPaiButtonListener(entry.getKey()));
            }
            else {
                // カン不可能ならば牌裏を表示
                button.setImageBitmap(ImageResourceManager.getInstance().getBlankImage());
            }
        }
    }
    
    
    
    /**
     * 雀牌ボタンリスナー
     */
    private final class JanPaiButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         * 
         * @param pai 雀牌。
         */
        public JanPaiButtonListener(final JanPai pai) {
            _pai = pai;
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            HandManager.getInstance().addFixedMenTsu(createMenTsu(_pai));
            HandManager.getInstance().updateView();
            finish();
        }
        
        /**
         * 暗カン面子を生成
         * 
         * @param pai 暗カン牌。
         * @return 暗カン面子。
         */
        private MenTsu createMenTsu(final JanPai pai) {
            final List<JanPai> source = Arrays.asList(pai, pai, pai, pai);
            return new MenTsu(source, MenTsuType.KAN_DARK);
        }
        
        /**
         * 牌の種類
         */
        private final JanPai _pai;
    }
    
    /**
     * キャンセルボタンリスナー
     */
    private final class CancelButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public CancelButtonListener() {
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            finish();
        }
    }
    
}
