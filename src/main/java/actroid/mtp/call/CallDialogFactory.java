/**
 * CallDialogFactory.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp.call;

import wiz.android.button.ButtonManager;
import actroid.mtp.MTPConst;
import actroid.mtp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;



/**
 * 副露ダイアログ生成
 */
public class CallDialogFactory {
    
    /**
     * コンストラクタ
     */
    public CallDialogFactory() {
    }
    
    
    
    /**
     * 副露ダイアログ生成
     * 
     * @param parent 親画面。
     * @return 副露ダイアログ。
     */
    public AlertDialog create(final Activity parent) {
        final View view = createCallView(parent);
        final AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.setTitle(MTPConst.APP_NAME);
        builder.setView(view);
        
        final AlertDialog dialog = builder.create();
        dialog.setOnKeyListener(createBackKeyListener());
        
        // ダイアログのインスタンスを利用するため、後からボタンを初期化する
        initializeChiButton(parent, view, dialog);
        initializePonButton(parent, view, dialog);
        initializeKanLightButton(parent, view, dialog);
        initializeKanDarkButton(parent, view, dialog);
        initializeCallCancelButton(view, dialog);
        return dialog;
    }
    
    
    
    /**
     * Backキーリスナーを生成
     * 
     * @return Backキーリスナー。
     */
    private DialogInterface.OnKeyListener createBackKeyListener() {
        return new DialogInterface.OnKeyListener() {
            /**
             * キー押下時の処理
             */
            public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
                switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    dialog.dismiss();
                    ButtonManager.getInstance().unlock(R.id.button_call);
                    return true;
                default:
                    return false;
                }
            }
        };
    }
    
    /**
     * 副露ビューを生成
     * 
     * @param parent 親画面。
     * @return 副露ビュー。
     */
    private View createCallView(final Activity parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent);
        final View view = inflater.inflate(R.layout.dialog_call, null);
        return view;
    }
    
    /**
     * 副露キャンセルボタンを初期化
     * 
     * @param view 副露画面。
     * @param callDialog 副露ダイアログ。
     */
    private void initializeCallCancelButton(final View view, final AlertDialog callDialog) {
        final Button button = (Button)view.findViewById(R.id.button_call_cancel);
        button.setOnClickListener(new CallCancelButtonListener(callDialog));
    }
    
    /**
     * チーボタンを初期化
     * 
     * @param view 副露画面。
     * @param callDialog 副露ダイアログ。
     */
    private void initializeChiButton(final Activity parent, final View view, final AlertDialog callDialog) {
        final Button button = (Button)view.findViewById(R.id.button_call_chi);
        button.setOnClickListener(new ChiButtonListener(parent, callDialog));
    }
    
    /**
     * 暗カンボタンを初期化
     * 
     * @param view 副露画面。
     * @param callDialog 副露ダイアログ。
     */
    private void initializeKanDarkButton(final Activity parent, final View view, final AlertDialog callDialog) {
        final Button button = (Button)view.findViewById(R.id.button_call_kan_dark);
        button.setOnClickListener(new KanDarkButtonListener(parent, callDialog));
    }
    
    /**
     * 明カンボタンを初期化
     * 
     * @param view 副露画面。
     * @param callDialog 副露ダイアログ。
     */
    private void initializeKanLightButton(final Activity parent, final View view, final AlertDialog callDialog) {
        final Button button = (Button)view.findViewById(R.id.button_call_kan_light);
        button.setOnClickListener(new KanLightButtonListener(parent, callDialog));
    }
    
    /**
     * ポンボタンを初期化
     * 
     * @param parent 親画面。
     * @param view 副露画面。
     * @param callDialog 副露ダイアログ。
     */
    private void initializePonButton(final Activity parent, final View view, final AlertDialog callDialog) {
        final Button button = (Button)view.findViewById(R.id.button_call_pon);
        button.setOnClickListener(new PonButtonListener(parent, callDialog));
    }
    
    
    
    /**
     * チーボタンリスナー
     */
    private static final class ChiButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         * 
         * @param parent 親画面。
         * @param dialog 親ダイアログ。
         */
        public ChiButtonListener(final Activity parent, final AlertDialog dialog) {
            _parent = parent;
            _dialog = dialog;
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            _dialog.dismiss();
            ButtonManager.getInstance().unlock(R.id.button_call);
            final Intent intent = new Intent(_parent, CallChiActivity.class);
            _parent.startActivity(intent);
        }
        
        /**
         * 親画面
         */
        private final Activity _parent;
        
        /**
         * 親ダイアログ
         */
        private final AlertDialog _dialog;
    }
    
    /**
     * ポンボタンリスナー
     */
    private static final class PonButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         * 
         * @param parent 親画面。
         * @param dialog 親ダイアログ。
         */
        public PonButtonListener(final Activity parent, final AlertDialog dialog) {
            _parent = parent;
            _dialog = dialog;
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            _dialog.dismiss();
            ButtonManager.getInstance().unlock(R.id.button_call);
            final Intent intent = new Intent(_parent, CallPonActivity.class);
            _parent.startActivity(intent);
        }
        
        /**
         * 親画面
         */
        private final Activity _parent;
        
        /**
         * 親ダイアログ
         */
        private final AlertDialog _dialog;
    }
    
    /**
     * 明カンボタンリスナー
     */
    private static final class KanLightButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         * 
         * @param parent 親画面。
         * @param dialog 親ダイアログ。
         */
        public KanLightButtonListener(final Activity parent, final AlertDialog dialog) {
            _parent = parent;
            _dialog = dialog;
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            _dialog.dismiss();
            ButtonManager.getInstance().unlock(R.id.button_call);
            final Intent intent = new Intent(_parent, CallKanLightActivity.class);
            _parent.startActivity(intent);
        }
        
        /**
         * 親画面
         */
        private final Activity _parent;
        
        /**
         * 親ダイアログ
         */
        private final AlertDialog _dialog;
    }
    
    /**
     * 暗カンボタンリスナー
     */
    private static final class KanDarkButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         * 
         * @param parent 親画面。
         * @param dialog 親ダイアログ。
         */
        public KanDarkButtonListener(final Activity parent, final AlertDialog dialog) {
            _parent = parent;
            _dialog = dialog;
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            _dialog.dismiss();
            ButtonManager.getInstance().unlock(R.id.button_call);
            final Intent intent = new Intent(_parent, CallKanDarkActivity.class);
            _parent.startActivity(intent);
        }
        
        /**
         * 親画面
         */
        private final Activity _parent;
        
        /**
         * 親ダイアログ
         */
        private final AlertDialog _dialog;
    }
    
    /**
     * キャンセルボタンリスナー
     */
    private static final class CallCancelButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public CallCancelButtonListener(final AlertDialog dialog) {
            _dialog = dialog;
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            _dialog.dismiss();
            ButtonManager.getInstance().unlock(R.id.button_call);
        }
        
        /**
         * 親ダイアログ
         */
        private final AlertDialog _dialog;
    }
    
}
