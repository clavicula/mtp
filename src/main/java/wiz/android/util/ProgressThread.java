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
public abstract class ProgressThread extends Thread {
    
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
    public ProgressThread(final int style) {
        setStyle(style);
    }
    
    
    
    /**
     * 進捗ダイアログを表示
     * 
     * @param activity 親画面。
     * @throws RuntimeException 進捗表示中の処理が中断された。
     */
    public void show(final Activity activity) throws RuntimeException {
        if (activity == null) {
            throw new NullPointerException("Activity is null.");
        }
        
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(_style);
        dialog.show();
        final Thread thread = new Thread() {
            @Override
            public void run() {
                backgroundProcess();
                dialog.dismiss();
            }
        };
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
     * 表示スタイルを設定
     * 
     * @param style 表示スタイル。
     */
    private void setStyle(final int style) {
        switch (style) {
        case ProgressDialog.STYLE_SPINNER:
        case ProgressDialog.STYLE_HORIZONTAL:
            _style = style;
            break;
        default:
            throw new IllegalArgumentException("Invalid style.");
        }
    }
    
    
    
    /**
     * 表示スタイル
     */
    private volatile int _style = ProgressDialog.STYLE_SPINNER;
    
}
