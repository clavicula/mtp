/**
 * UncaughtExceptionHandlerFactory.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.android.util;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;



/**
 * 未補足例外ハンドラを生成
 */
public class UncaughtExceptionHandlerFactory {
    
    /**
     * コンストラクタ
     */
    public UncaughtExceptionHandlerFactory() {
    }
    
    
    
    /**
     * 未補足例外ハンドラを生成
     * 
     * @param activity 対象画面。
     * @return 未補足例外ハンドラ。
     */
    public UncaughtExceptionHandler create(final Context activity) {
        final UncaughtExceptionHandler defaultUncaughtExceptionHandler =
        Thread.getDefaultUncaughtExceptionHandler();
        return new UncaughtExceptionHandler() {
            /**
             * 例外がキャッチされなかった場合の処理
             */
            public void uncaughtException(final Thread thread, final Throwable e) {
                if (_crashed) {
                    return;
                }
                
                final AlertFactory factory = new AlertFactory();
                final AlertDialog dialog = factory.createSimpleAlert(activity, "Error", e.getMessage());
                dialog.show();
                
                _crashed = true;
                defaultUncaughtExceptionHandler.uncaughtException(thread, e);
            }
            
            /**
             * 例外発生済みフラグ
             */
            private volatile boolean _crashed = false;
        };
    }
    
}
