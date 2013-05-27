/**
 * AlertFactory.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;



/**
 * アラート生成
 */
public class AlertFactory {
    
    /**
     * コンストラクタ
     */
    public AlertFactory() {
    }
    
    
    
    /**
     * OK/キャンセルボタンを持つアラートを生成
     * 
     * @param activity 親画面。
     * @param title ダイアログのタイトル。
     * @param message 表示メッセージ。
     * @param onOK OKボタン押下時の処理リスナー。nullを許可する。
     * @param onCancel Cancelボタン押下時の処理リスナー。nullを許可する。
     * @return アラートダイアログ。
     */
    public AlertDialog createConfirmAlert(final Context activity,
    final String title,
    final String message,
    final DialogInterface.OnClickListener onOK,
    final DialogInterface.OnClickListener onCancel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setPositiveButton("Cancel", onCancel);
        builder.setNeutralButton("OK", onOK);
        
        final AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.setMessage(message);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("OK", onOK);
        
        final AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        return dialog;
    }
    
}
