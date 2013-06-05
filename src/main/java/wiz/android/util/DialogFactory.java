/**
 * DialogFactory.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;



/**
 * ダイアログ生成
 */
public class DialogFactory {
    
    /**
     * コンストラクタ
     */
    public DialogFactory() {
    }
    
    
    
    /**
     * OK/キャンセルボタンを持つアラートを生成
     * 
     * @param activity 親画面。
     * @param title ダイアログのタイトル。
     * @param message 表示メッセージ。
     * @param onCancel Cancelボタン押下時の処理リスナー。nullを許可する。
     * @param onOK OKボタン押下時の処理リスナー。nullを許可する。
     * @return アラートダイアログ。
     */
    public AlertDialog createConfirmAlert(final Context activity,
                                          final String title,
                                          final String message,
                                          final CancelListener onCancel,
                                          final DialogInterface.OnClickListener onOK) {
        if (activity == null) {
            throw new NullPointerException("Parent activity is null.");
        }
        if (title == null) {
            throw new NullPointerException("Dialog title is null.");
        }
        if (message == null) {
            throw new NullPointerException("Dialog message is null.");
        }
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        
        final String cancelButtonName = (onCancel == null) ? "Cancel" : onCancel.getName();
        builder.setPositiveButton(cancelButtonName, createCancelButtonListener(onCancel));
        builder.setNeutralButton("OK", onOK);
        
        final AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        if (onCancel != null) {
            dialog.setOnKeyListener(createBackKeyListener(onCancel));
        }
        return dialog;
    }
    
    /**
     * OKボタンのみのアラートを生成
     * 
     * @param activity 親画面。
     * @param title ダイアログのタイトル。
     * @param message 表示メッセージ。
     * @return アラートダイアログ。
     */
    public AlertDialog createSimpleAlert(final Context activity,
                                         final String title,
                                         final String message) {
        return createSimpleAlert(activity, title, message, null);
    }
    
    /**
     * OKボタンのみのアラートを生成
     * 
     * @param activity 親画面。
     * @param title ダイアログのタイトル。
     * @param message 表示メッセージ。
     * @param onOK OKボタン押下時の処理リスナー。nullを許可する。
     * @return アラートダイアログ。
     */
    public AlertDialog createSimpleAlert(final Context activity,
                                         final String title,
                                         final String message,
                                         final DialogInterface.OnClickListener onOK) {
        if (activity == null) {
            throw new NullPointerException("Parent activity is null.");
        }
        if (title == null) {
            throw new NullPointerException("Dialog title is null.");
        }
        if (message == null) {
            throw new NullPointerException("Dialog message is null.");
        }
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("OK", onOK);
        
        final AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        return dialog;
    }
    
    
    
    /**
     * Backキーリスナーを生成
     * 
     * @param onCancel キャンセル時の処理。
     * @return Backキーリスナー。
     */
    private DialogInterface.OnKeyListener createBackKeyListener(final CancelListener onCancel) {
        if (onCancel == null) {
            return null;
        }
        return new DialogInterface.OnKeyListener() {
            /**
             * キー押下時の処理
             */
            public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
                switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    dialog.dismiss();
                    onCancel.onCancel(dialog);
                    return true;
                default:
                    return false;
                }
            }
        };
    }
    
    /**
     * キャンセルボタンリスナーを生成
     * 
     * @param onCancel キャンセル時の処理。
     * @return キャンセルボタンリスナー。
     */
    private DialogInterface.OnClickListener createCancelButtonListener(final CancelListener onCancel) {
        if (onCancel == null) {
            return null;
        }
        return new DialogInterface.OnClickListener() {
            /**
             * クリック時の処理
             */
            public void onClick(final DialogInterface dialog, final int which) {
                onCancel.onCancel(dialog);
            }
        };
    }
    
    
    
    /**
     * キャンセル処理リスナー
     */
    public static interface CancelListener {
        
        /**
         * リスナー名を取得
         * 
         * @return リスナー名。
         */
        public String getName();
        
        /**
         * キャンセル時の処理
         * 
         * @param dialog 親ダイアログ。
         */
        public void onCancel(final DialogInterface dialog);
        
    }
    
}
