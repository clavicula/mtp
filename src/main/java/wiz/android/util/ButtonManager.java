/**
 * ButtonManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.android.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.widget.Button;



/**
 * ボタン管理
 */
public final class ButtonManager {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private ButtonManager() {
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static ButtonManager getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * ボタンを追加
     * 
     * @param button 追加対象。
     */
    public void addButton(final Button button) {
        if (button != null) {
            _button.put(button.getId(), button);
        }
    }
    
    /**
     * ボタンを無効化
     * 
     * @param buttonID ボタンID。
     */
    public void disable(final int buttonID) {
        if (!_button.containsKey(buttonID)) {
            throw new IllegalArgumentException("Invalid button ID - " + buttonID);
        }
        _button.get(buttonID).setEnabled(false);
    }
    
    /**
     * ボタンを有効化
     * 
     * @param buttonID ボタンID。
     */
    public void enable(final int buttonID) {
        if (!_button.containsKey(buttonID)) {
            throw new IllegalArgumentException("Invalid button ID - " + buttonID);
        }
        _button.get(buttonID).setEnabled(true);
    }
    
    /**
     * ボタンをロック
     * 
     * @param buttonID ボタンID。
     */
    public void lock(final int buttonID) {
        if (!_button.containsKey(buttonID)) {
            throw new IllegalArgumentException("Invalid button ID - " + buttonID);
        }
        _button.get(buttonID).setClickable(false);
    }
    
    /**
     * ボタンをアンロック
     * 
     * @param buttonID ボタンID。
     */
    public void unlock(final int buttonID) {
        if (!_button.containsKey(buttonID)) {
            throw new IllegalArgumentException("Invalid button ID - " + buttonID);
        }
        _button.get(buttonID).setClickable(true);
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final ButtonManager INSTANCE = new ButtonManager();
    
    
    
    /**
     * ボタン
     */
    private final Map<Integer, Button> _button = new ConcurrentHashMap<Integer, Button>();
    
}
