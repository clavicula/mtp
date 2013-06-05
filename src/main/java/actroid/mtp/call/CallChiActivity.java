/**
 * CallChiActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp.call;

import java.util.ArrayList;
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
 * チー画面
 */
public final class CallChiActivity extends Activity {
    
    /**
     * コンストラクタ
     */
    public CallChiActivity() {
    }
    
    
    
    /**
     * 画面生成時の処理
     */
    @Override
    protected void onCreate(final Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_call_chi);
        
        final UncaughtExceptionHandlerFactory factory = new UncaughtExceptionHandlerFactory();
        Thread.setDefaultUncaughtExceptionHandler(factory.create(this));
        
        initializeJanPaiButton();
        initializeCancelButton();
    }
    
    
    
    /**
     * キャンセルボタンを初期化
     */
    private void initializeCancelButton() {
        final Button button = (Button)findViewById(R.id.button_call_chi_cancel);
        button.setOnClickListener(new CancelButtonListener());
    }
    
    /**
     * 雀牌ボタンを初期化
     */
    private void initializeJanPaiButton() {
        final List<JanPai> invalidList = createInvalidJanPaiList();
        for (final Map.Entry<JanPai, Integer> entry : MTPConst.CHI_JAN_PAI_BUTTON_ID_MAP.entrySet()) {
            final ImageButton button = (ImageButton)findViewById(entry.getValue());
            final JanPai pai = entry.getKey();
            if (!invalidList.contains(pai)) {
                // チー可能ならばクリック時の処理を設定
                button.setOnClickListener(new JanPaiButtonListener(entry.getKey()));
            }
            else {
                // チー不可能ならば牌裏を表示
                button.setImageBitmap(ImageResourceManager.getInstance().getBlankImage());
            }
        }
    }
    
    /**
     * チーの先頭牌になれない牌のリストを生成
     * 
     * @return チーの先頭牌になれない牌のリスト。
     */
    private List<JanPai> createInvalidJanPaiList() {
        final List<JanPai> resultList = new ArrayList<JanPai>();
        final Map<JanPai, Integer> hand = HandManager.getInstance().getAllJanPaiMap();
        for (final Map.Entry<JanPai, Integer> entry : hand.entrySet()) {
            final JanPai pai = entry.getKey();
            if (pai.isJi()) {
                // 字牌ならば無条件で格納
                resultList.add(pai);
                continue;
            }
            
            switch (pai) {
            case MAN_8:
            case MAN_9:
            case PIN_8:
            case PIN_9:
            case SOU_8:
            case SOU_9:
                // 8, 9 の数牌ならば無条件で格納
                resultList.add(pai);
                continue;
            default:
                break;
            }
            
            if (entry.getValue() >= 4) {
                resultList.add(pai);
                continue;
            }
            
            final JanPai next = pai.getNext();
            if (hand.get(next) >= 4) {
                resultList.add(pai);
                continue;
            }
            if (hand.get(next.getNext()) >= 4) {
                resultList.add(pai);
            }   
        }
        return resultList;
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
         * チー面子を生成
         * 
         * @param pai チー牌。
         * @return チー面子。
         */
        private MenTsu createMenTsu(final JanPai pai) {
            final JanPai next = pai.getNext();
            final List<JanPai> source = Arrays.asList(pai, next, next.getNext());
            return new MenTsu(source, MenTsuType.CHI);
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
