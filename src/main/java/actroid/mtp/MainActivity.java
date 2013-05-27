/**
 * MainActivity.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wiz.android.util.AlertFactory;
import wiz.android.util.ProgressThread;
import wiz.android.util.UncaughtExceptionHandlerFactory;
import wiz.project.jan.JanPai;
import wiz.project.jan.TenpaiPattern;
import wiz.project.jan.TenpaiPatternThread;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



/**
 * 手牌入力画面
 */
public final class MainActivity extends Activity {
    
    /**
     * コンストラクタ
     */
    public MainActivity() {
    }
    
    
    
    /**
     * 画面回転時の処理
     * 
     * @param config 再描画パラメータ。
     */
    @Override
    public void onConfigurationChanged(final Configuration config) {
        super.onConfigurationChanged(config);
    }
    
    
    
    /**
     * 画面生成時の処理
     * 
     * @param savedInstanceState 再起動パラメータ。
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final UncaughtExceptionHandlerFactory factory = new UncaughtExceptionHandlerFactory();
        Thread.setDefaultUncaughtExceptionHandler(factory.create(this));
        
        // 初期化順を変更してはならない
        initializeResourceManager();
        initializeHandView();
        initializeJanPaiButton();
        initializeClearButton();
        initializeCheckButton();
    }
    
    
    
    /**
     * 確認ボタンを初期化
     */
    private void initializeCheckButton() {
        CheckButtonManager.getInstance().initialize(this);
        CheckButtonManager.getInstance().setOnClickListener(new CheckButtonListener());
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
        final List<ImageButton> handButtonList = new ArrayList<ImageButton>();
        for (final int handID : MTPConst.HAND_BUTTON_ID_LIST) {
            final ImageButton button = (ImageButton)findViewById(handID);
            button.setOnClickListener(new HandButtonListener(handID));
            handButtonList.add(button);
        }
        HandView.getInstance().initialize(handButtonList);
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
     * 待ち牌判定結果を表示
     * 
     * @param hand 手牌。
     * @param patternList パターン。
     */
    private void showResultPattern(final List<JanPai> hand, final List<TenpaiPattern> patternList) {
        final Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra(MTPConst.KEY_HAND, (Serializable)hand);
        intent.putExtra(MTPConst.KEY_TENPAI_PATTERN, (Serializable)patternList);
        startActivity(intent);
    }
    
    /**
     * 聴牌パターンを更新
     */
    private void updateTenpaiPattern() {
        synchronized (_PATTERN_THREAD_LOCK) {
            if (_tenpaiPatternThread != null) {
                _tenpaiPatternThread.interrupt();
            }
            
            // バックグラウンドで処理しておく
            _tenpaiPatternThread = new TenpaiPatternThread(_handManager.getPaiMap());
            _tenpaiPatternThread.start();
        }
    }
    
    /**
     * 手牌表示を更新
     */
    private void updateHandView() {
        int count = 0;
        for (final JanPai pai : _handManager.getPaiList()) {
            HandView.getInstance().setButtonImage(count, pai);
            count++;
        }
        
        if (count == 14) {
            updateTenpaiPattern();
        }
        else {
            // 合計14牌になるまで牌裏で埋める
            for (int i = count; i < 14; i++) {
                HandView.getInstance().clearButtonImage(i);
            }
        }
    }
    
    
    
    /**
     * ロックオブジェクト (手牌管理)
     */
    private final Object _HAND_MANAGER_LOCK = new Object();
    
    /**
     * ロックオブジェクト (パターンスレッド)
     */
    private final Object _PATTERN_THREAD_LOCK = new Object();
    
    /**
     * 手牌管理
     */
    private final HandManager _handManager = new MenzenHandManager();
    
    /**
     * 聴牌パターン取得スレッド
     */
    private TenpaiPatternThread _tenpaiPatternThread = null;
    
    
    
    /**
     * リセットボタンリスナー
     */
    private final class ClearButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public ClearButtonListener() {
        }
        
        /**
         * クリック時の処理
         * 
         * @param view リセットボタン。
         */
        public void onClick(final View view) {
            synchronized (_HAND_MANAGER_LOCK) {
                if (_handManager.getSize() > 0) {
                    _handManager.clear();
                    updateHandView();
                }
            }
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
         * 
         * @param view 確認ボタン。
         */
        public void onClick(final View view) {
            // 確認ボタンの連打対策
            CheckButtonManager.getInstance().lock();
            
            boolean changeView = false;
            try {
                final List<JanPai> paiList = new ArrayList<JanPai>();
                synchronized (_HAND_MANAGER_LOCK) {
                    paiList.addAll(_handManager.getPaiList());
                }
                
                if (paiList.size() < 14) {
                    alert("少牌です");
                    return;
                }
                
                synchronized (_PATTERN_THREAD_LOCK) {
                    if (_tenpaiPatternThread == null) {
                        // 14牌あるのにスレッドが作動していない
                        throw new IllegalStateException("Pattern thread is null.");
                    }
                    
                    final ProgressThread progress = new JoinProgressThread(_tenpaiPatternThread);
                    progress.show(MainActivity.this);
                    
                    final List<TenpaiPattern> patternList = _tenpaiPatternThread.getPatternList();
                    if (patternList.isEmpty()) {
                        alert("不聴です");
                        return;
                    }
                    
                    changeView = true;
                    if (_tenpaiPatternThread.isCompleted()) {
                        final DialogInterface.OnClickListener onOK = new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int selected) {
                                showResultPattern(paiList, patternList);
                            }
                        };
                        final DialogInterface.OnClickListener onCancel = new CancelButtonListener();
                        confirm("和了済みです。\n判定しますか？", onOK, onCancel);
                    }
                    else {
                        showResultPattern(paiList, patternList);
                    }
                }
            }
            finally {
                if (!changeView) {
                    CheckButtonManager.getInstance().unlock();
                }
            }
        }
        
        /**
         * 警告ダイアログ
         * 
         * @param message 警告メッセージ。
         */
        private void alert(final String message) {
            final AlertFactory factory = new AlertFactory();
            final AlertDialog dialog = factory.createSimpleAlert(MainActivity.this, MTPConst.APP_NAME, message);
            dialog.show();
        }
        
        /**
         * 確認ダイアログ
         * 
         * @param message 確認メッセージ。
         * @param onOK OKボタン押下時の処理。
         */
        private void confirm(final String message, final DialogInterface.OnClickListener onOK, final DialogInterface.OnClickListener onCancel) {
            final AlertFactory factory = new AlertFactory();
            final AlertDialog dialog = factory.createConfirmAlert(MainActivity.this, MTPConst.APP_NAME, message, onOK, onCancel);
            dialog.show();
        }
        
        /**
         * キャンセルボタンリスナー
         */
        private final class CancelButtonListener implements DialogInterface.OnClickListener {
            
            /**
             * コンストラクタ
             */
            public CancelButtonListener() {
            }
            
            /**
             * クリック時の処理
             * 
             * @param dialog 確認ダイアログ。
             * @param selected ボタンID。
             */
            public void onClick(final DialogInterface dialog, final int selected) {
                CheckButtonManager.getInstance().unlock();
            }
        };
        
    }
    
    /**
     * 手牌ボタンリスナー
     */
    private final class HandButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         * 
         * @param handID 手牌ID。
         */
        public HandButtonListener(final int handID) {
            _handID = handID;
        }
        
        /**
         * クリック時の処理
         * 
         * @param view 手牌ボタン。
         */
        public void onClick(final View view) {
            if (!HandView.getInstance().hasJanPai(_handID)) {
                return;
            }
            final JanPai pai = HandView.getInstance().getJanPai(_handID);
            synchronized (_HAND_MANAGER_LOCK) {
                if (_handManager.getSize() > 0) {
                    _handManager.remove(pai);
                    updateHandView();
                }
            }
        }
        
        /**
         * 手牌ID
         */
        private final int _handID;
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
         * 
         * @param view 雀牌ボタン。
         */
        public void onClick(final View view) {
            synchronized (_HAND_MANAGER_LOCK) {
                if (_handManager.getSize() < 14) {
                    _handManager.add(_pai);
                    updateHandView();
                }
            }
        }
        
        /**
         * 牌の種類
         */
        private final JanPai _pai;
    }
    
    /**
     * 同期進捗スレッド
     */
    private static final class JoinProgressThread extends ProgressThread {
        
        /**
         * コンストラクタ
         * 
         * @param thread 同期対象スレッド。
         */
        public JoinProgressThread(final Thread thread) {
            if (thread == null) {
                throw new NullPointerException("Target thread is null.");
            }
            _thread = thread;
        }
        
        /**
         * 進捗ダイアログ表示中の処理
         */
        protected void backgroundProcess() {
            try {
                _thread.join();
            }
            catch (final InterruptedException e) {
                // 何もしない
            }
        }
        
        /**
         * 同期対象スレッド
         */
        private final Thread _thread;
    }
    
}
