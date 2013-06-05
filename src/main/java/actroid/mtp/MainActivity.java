/**
 * MainActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;



import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import wiz.android.util.ButtonManager;
import wiz.android.util.DialogFactory;
import wiz.android.util.JoinProgressThread;
import wiz.android.util.ProgressThread;
import wiz.android.util.UncaughtExceptionHandlerFactory;
import wiz.project.jan.Hand;
import wiz.project.jan.JanPai;
import wiz.project.jan.TenpaiPattern;
import actroid.mtp.call.CallDialogFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



/**
 * 手牌入力画面
 */
public final class MainActivity extends Activity implements Observer {
    
    /**
     * コンストラクタ
     */
    public MainActivity() {
    }
    
    
    
    /**
     * 更新通知時の処理
     * 
     * @param subject 監視対象。
     * @param param 通知パラメータ。
     */
    public void update(final Observable subject, final Object param) {
        if (!(param instanceof Hand)) {
            return;
        }
        
        final Hand hand = (Hand)param;
        final int usable = hand.getUsableSize();
        switch (usable) {
        case 0:
            // 手牌が全て埋まっている
            ButtonManager.getInstance().enable(R.id.button_check);
            ButtonManager.getInstance().disable(R.id.button_call);
            updateTenpaiPattern(hand);
            break;
        case 1:
        case 2:
            // 手牌に空きがあるが、副露不可
            ButtonManager.getInstance().disable(R.id.button_check);
            ButtonManager.getInstance().disable(R.id.button_call);
            break;
        default:
            // 手牌に空きがあり、副露可能
            ButtonManager.getInstance().disable(R.id.button_check);
            ButtonManager.getInstance().enable(R.id.button_call);
            break;
        }
    }
    
    
    
    /**
     * 画面生成時の処理
     */
    @Override
    protected void onCreate(final Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
        
        final UncaughtExceptionHandlerFactory factory = new UncaughtExceptionHandlerFactory();
        Thread.setDefaultUncaughtExceptionHandler(factory.create(this));
        
        // 初期化順を変更してはならない
        initializeResourceManager();
        
        initializeJanPaiButton();
        initializeClearButton();
        initializeCheckButton();
        initializeCallButton();
        
        initializeHandView();
    }
    
    /**
     * 画面リロード時の状態保存
     */
    @Override
    protected void onSaveInstanceState(final Bundle state) {
        super.onSaveInstanceState(state);
    }
    
    /**
     * 画面リロード時の状態読み込み
     */
    @Override
    protected void onRestoreInstanceState(final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        HandManager.getInstance().updateView();
    }
    
    
    
    /**
     * 警告ダイアログ
     * 
     * @param message 警告メッセージ。
     */
    private void alert(final String message) {
        final DialogFactory factory = new DialogFactory();
        final AlertDialog dialog =
            factory.createSimpleAlert(this, MTPConst.APP_NAME, message);
        dialog.show();
    }
    
    /**
     * 副露ボタンを初期化
     */
    private void initializeCallButton() {
        final Button button = (Button)findViewById(R.id.button_call);
        button.setOnClickListener(new CallButtonListener());
        ButtonManager.getInstance().addButton(button);
    }
    
    /**
     * 確認ボタンを初期化
     */
    private void initializeCheckButton() {
        final Button button = (Button)findViewById(R.id.button_check);
        button.setOnClickListener(new CheckButtonListener());
        ButtonManager.getInstance().addButton(button);
        ButtonManager.getInstance().disable(R.id.button_check);
    }
    
    /**
     * リセットボタンを初期化
     */
    private void initializeClearButton() {
        final Button button = (Button)findViewById(R.id.button_clear);
        button.setOnClickListener(new ClearButtonListener());
    }
    
    /**
     * 手牌ビューを初期化
     */
    private void initializeHandView() {
        HandManager.getInstance().initialize(this, this);
        HandManager.getInstance().updateView();
    }
    
    /**
     * 雀牌ボタンを初期化
     */
    private void initializeJanPaiButton() {
        for (final Map.Entry<JanPai, Integer> entry : MTPConst.JAN_PAI_BUTTON_ID_MAP.entrySet()) {
            final ImageButton button = (ImageButton)findViewById(entry.getValue());
            button.setOnClickListener(new JanPaiButtonListener(entry.getKey()));
        }
    }
    
    /**
     * リソース管理を初期化
     */
    private void initializeResourceManager() {
        ImageResourceManager.getInstance().initialize(this);
    }
    
    /**
     * 進捗ダイアログを表示しつつ同期
     * 
     * @param thread 同期対象スレッド。
     */
    private void joinWithProgressDialog(final Thread thread) {
        final ProgressThread progress = new JoinProgressThread(thread);
        progress.show(this);
    }
    
    /**
     * 待ち牌判定結果を表示
     * 
     * @param patternList パターン。
     */
    private void showResultPattern(final List<TenpaiPattern> patternList) {
        final Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(MTPConst.KEY_TENPAI_PATTERN, (Serializable)patternList);
        startActivity(intent);
    }
    
    /**
     * 聴牌パターンを更新
     */
    private void updateTenpaiPattern(final Hand hand) {
        synchronized (_PATTERN_THREAD_LOCK) {
            if (_tenpaiPatternThread != null) {
                _tenpaiPatternThread.interrupt();
            }
            
            // バックグラウンドで処理しておく
            _tenpaiPatternThread = new TenpaiPatternThread(hand);
            _tenpaiPatternThread.start();
        }
    }
    
    
    
    /**
     * ロックオブジェクト (聴牌パターン取得スレッド)
     */
    private final Object _PATTERN_THREAD_LOCK = new Object();
    
    
    
    /**
     * 聴牌パターン取得スレッド
     */
    private TenpaiPatternThread _tenpaiPatternThread = null;
    
    
    
    /**
     * 副露ボタンリスナー
     */
    private final class CallButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public CallButtonListener() {
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            // ボタン連打対策
            ButtonManager.getInstance().lock(R.id.button_call);
            
            final CallDialogFactory factory = new CallDialogFactory();
            final AlertDialog dialog = factory.create(MainActivity.this);
            dialog.show();
        }
    }
    
    /**
     * 確認ボタンリスナー
     */
    private final class CheckButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public CheckButtonListener() {
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            // ボタン連打対策
            ButtonManager.getInstance().lock(R.id.button_check);
            
            boolean changeView = false;
            try {
                synchronized (_PATTERN_THREAD_LOCK) {
                    if (_tenpaiPatternThread == null) {
                        // 14牌あるのにスレッドが作動していない
                        throw new IllegalStateException("Pattern thread is null.");
                    }
                    if (!_tenpaiPatternThread.isFinished()) {
                        joinWithProgressDialog(_tenpaiPatternThread);
                    }
                    
                    final List<TenpaiPattern> patternList = _tenpaiPatternThread.getPatternList();
                    if (patternList.isEmpty()) {
                        alert("不聴です");
                        return;
                    }
                    
                    changeView = true;
                    if (_tenpaiPatternThread.isCompleted()) {
                        final DialogInterface.OnClickListener onOK = new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int selected) {
                                showResultPattern(patternList);
                            }
                        };
                        final DialogFactory.CancelListener onCancel = new CancelListener();
                        confirm("和了済みです。\n判定しますか？", onOK, onCancel);
                    }
                    else {
                        showResultPattern(patternList);
                    }
                }
            }
            finally {
                if (!changeView) {
                    ButtonManager.getInstance().unlock(R.id.button_check);
                }
            }
        }
        
        /**
         * 確認ダイアログ
         * 
         * @param message 確認メッセージ。
         * @param onOK OKボタン押下時の処理。
         */
        private void confirm(final String message, final DialogInterface.OnClickListener onOK, final DialogFactory.CancelListener onCancel) {
            final DialogFactory factory = new DialogFactory();
            final AlertDialog dialog = factory.createConfirmAlert(MainActivity.this, MTPConst.APP_NAME, message, onCancel, onOK);
            dialog.show();
        }
        
        /**
         * キャンセル処理リスナー
         */
        private final class CancelListener implements DialogFactory.CancelListener {
            
            /**
             * コンストラクタ
             */
            public CancelListener() {
            }
            
            /**
             * リスナー名を取得
             */
            public String getName() {
                return "キャンセル";
            }
            
            /**
             * キャンセル時の処理
             */
            public void onCancel(final DialogInterface dialog) {
                ButtonManager.getInstance().unlock(R.id.button_check);
            }
        };
    }
    
    /**
     * リセットボタンリスナー
     */
    private static final class ClearButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public ClearButtonListener() {
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            boolean cleared = false;
            if (HandManager.getInstance().getMenZenSize() > 0) {
                HandManager.getInstance().clearMenZenHand();
                cleared = true;
            }
            if (HandManager.getInstance().getFixedMenTsuCount() > 0) {
                HandManager.getInstance().clearFixedMenTzu();
                cleared = true;
            }
            if (cleared) {
                HandManager.getInstance().updateView();
            }
        }
    }
    
    /**
     * 雀牌ボタンリスナー
     */
    private static final class JanPaiButtonListener implements View.OnClickListener {
        
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
            if (HandManager.getInstance().isLimitSize()) {
                return;
            }
            final int count = HandManager.getInstance().getJanPaiCount(_pai);
            if (count >= 4) {
                return;
            }
            HandManager.getInstance().addJanPai(_pai);
            HandManager.getInstance().updateView();
        }
        
        /**
         * 牌の種類
         */
        private final JanPai _pai;
    }
    
}
