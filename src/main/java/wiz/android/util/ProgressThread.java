/**
 * ProgressThread.java
 * 
 * @Author
 *   Yuki Kawata
 */
package wiz.android.util;

import android.app.Activity;
import android.app.ProgressDialog;



/**
 * 進捗スレッド
 */
public abstract class ProgressThread {
    
    /**
     * コンストラクタ
     */
    public ProgressThread() {
    }
    
    /**
     * コンストラクタ
     * 
     * @param style 表示スタイル。
     */
    public ProgressThread(final ProgressDialogStyle style) {
        setStyle(style);
    }
    
    
    
    /**
     * 進捗ダイアログを表示
     * 
     * @param activity 親画面。
     * @throws RuntimeException 進捗表示中の処理が中断された。
     */
    public final void show(final Activity activity) throws RuntimeException {
        if (activity == null) {
            throw new NullPointerException("Activity is null.");
        }
        
        final ProgressDialog dialog = createProgressDialog(activity);
        final Thread thread = createCoreThread(dialog);
        thread.start();
        
        try {
            thread.join();
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    /**
     * 進捗ダイアログ表示中の処理
     */
    protected abstract void backgroundProcess();
    
    
    
    /**
     * 中核処理スレッドを生成
     * 
     * @param dialog 中核処理中に表示する進捗ダイアログ。
     * @return 中核処理スレッド。
     */
    private Thread createCoreThread(final ProgressDialog dialog) {
        return new Thread() {
            /**
             * 処理を実行
             */
            @Override
            public void run() {
                backgroundProcess();
                dialog.dismiss();
            }
        };
    }
    
    /**
     * 進捗ダイアログを生成
     * 
     * @param activity 親画面。
     * @return 進捗ダイアログ。
     */
    private ProgressDialog createProgressDialog(final Activity activity) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(_style);
        dialog.show();
        return dialog;
    }
    
    /**
     * 表示スタイルを設定
     * 
     * @param style 表示スタイル。
     */
    private void setStyle(final ProgressDialogStyle style) {
        if (style != null) {
            _style = style.toCode();
        }
        else {
            _style = ProgressDialog.STYLE_SPINNER;
        }
    }
    
    
    
    /**
     * 表示スタイル
     */
    private volatile int _style = ProgressDialog.STYLE_SPINNER;
    
}
