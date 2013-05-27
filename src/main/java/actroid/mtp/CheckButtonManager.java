/**
 * CheckButtonManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import android.app.Activity;
import android.view.View;
import android.widget.Button;



/**
 * 確認ボタン管理
 */
final class CheckButtonManager {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private CheckButtonManager() {
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static CheckButtonManager getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * 初期化処理
     */
    public void initialize(final Activity activity) {
        synchronized (_BUTTON_LOCK) {
            _button = (Button)activity.findViewById(R.id.button_check);
        }
    }
    
    /**
     * ボタンをロック
     */
    public void lock() {
        synchronized (_BUTTON_LOCK) {
            _button.setClickable(false);
        }
    }
    
    /**
     * リスナーを設定
     */
    public void setOnClickListener(final View.OnClickListener listener) {
        synchronized (_BUTTON_LOCK) {
            _button.setOnClickListener(listener);
        }
    }
    
    /**
     * ボタンをアンロック
     */
    public void unlock() {
        synchronized (_BUTTON_LOCK) {
            _button.setClickable(true);
        }
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final CheckButtonManager INSTANCE = new CheckButtonManager();
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _BUTTON_LOCK = new Object();
    
    
    
    /**
     * ボタン
     */
    private Button _button = null;
    
}
