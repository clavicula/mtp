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
        _thread = thread;
    }
    
    
    
    /**
     * 進捗ダイアログ表示中の処理
     */
    @Override
    protected void backgroundProcess() {
        try {
            if (_thread != null) {
                _thread.join();
            }
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
