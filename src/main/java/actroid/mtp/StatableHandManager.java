/**
 * StatableHandManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.List;
import java.util.Map;

import wiz.project.jan.JanPai;



/**
 * 状態保持可能な手牌管理
 */
final class StatableHandManager implements HandManager {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private StatableHandManager() {
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static StatableHandManager getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * 手牌を追加
     */
    public void add(final JanPai pai) {
        synchronized (_CORE_LOCK) {
            _core.add(pai);
        }
    }
    
    /**
     * 手牌を全消去
     */
    public void clear() {
        synchronized (_CORE_LOCK) {
            _core.clear();
        }
    }
    
    /**
     * 手牌リストを取得
     */
    public List<JanPai> getJanPaiList() {
        synchronized (_CORE_LOCK) {
            return _core.getJanPaiList();
        }
    }
    
    /**
     * 手牌マップを取得
     */
    public Map<JanPai, Integer> getJanPaiMap() {
        synchronized (_CORE_LOCK) {
            return _core.getJanPaiMap();
        }
    }
    
    /**
     * 手牌の数を取得
     */
    public int getSize() {
        synchronized (_CORE_LOCK) {
            return _core.getSize();
        }
    }
    
    /**
     * 手牌を削除
     */
    public void remove(final JanPai pai) {
        synchronized (_CORE_LOCK) {
            _core.remove(pai);
        }
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final StatableHandManager INSTANCE = new StatableHandManager();
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _CORE_LOCK = new Object();
    
    
    
    /**
     * 中核オブジェクト
     */
    private final HandManager _core = new SerializableHandManager();
    
}

