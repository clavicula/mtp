/**
 * JoinProgressThread.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.android.util;



/**
 * スレッドの処理待ち進捗スレッド
 */
public class JoinProgressThread extends ProgressThread {
    
    /**
     * コンストラクタ
     * 
     * @param thread 同期対象スレッド。
     */
    public JoinProgressThread(final Thread thread) {
        synchronized (_THREAD_LOCK) {
            setThread(thread);
        }
    }
    
    
    
    /**
     * 進捗ダイアログ表示中の処理
     */
    protected void backgroundProcess() {
        try {
            synchronized (_THREAD_LOCK) {
                if (_thread != null) {
                    _thread.join();
                }
            }
        }
        catch (final InterruptedException e) {
            // 何もしない
        }
    }
    
    
    
    /**
     * 同期対象スレッドを設定
     * 
     * @param thread 同期対象スレッド。
     */
    private void setThread(final Thread thread) {
        _thread = thread;
    }
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _THREAD_LOCK = new Object();
    
    
    
    /**
     * 同期対象スレッド
     */
    private Thread _thread = null;
    
}
