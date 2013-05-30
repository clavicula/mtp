/**
 * SerializableHandManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import wiz.project.jan.JanPai;
import wiz.project.jan.MenTsu;



/**
 * シリアライズ可能な手牌管理
 */
class SerializableHandManager implements HandManager, Serializable {
    
    /**
     * コンストラクタ
     */
    public SerializableHandManager() {
        clear();
    }
    
    
    
    /**
     * 手牌を追加
     * 
     * @param pai 追加対象の牌。
     */
    public void add(final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Source pai is null.");
        }
        
        if (getSize() >= 14) {
            // 既に14牌持っている場合は何もしない
            return;
        }
        
        final int count = _hand.get(pai);
        switch (count) {
        case 0:
        case 1:
        case 2:
        case 3:
            _hand.put(pai, count + 1);
            break;
        case 4:
            // 既に同じ牌を4牌持っている場合は何もしない
            return;
        default:
            throw new InternalError();
        }
    }
    
    /**
     * 固定面子を追加
     */
    public void addFixedMenTsu(final MenTsu menTsu) {
        _fixedMenTsuList.add(menTsu);
    }
    
    /**
     * 手牌を全消去
     */
    public void clear() {
        for (final JanPai pai : JanPai.values()) {
            _hand.put(pai, 0);
        }
    }
    
    /**
     * 手牌リストを取得
     * 
     * @return 手牌リスト。
     */
    public List<JanPai> getJanPaiList() {
        final List<JanPai> resultList = new ArrayList<JanPai>();
        for (final Map.Entry<JanPai, Integer> entry : _hand.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                resultList.add(entry.getKey());
            }
        }
        return resultList;
    }
    
    /**
     * 手牌マップを取得
     * 
     * @return 手牌マップ。どの牌が何枚あるかを示す。
     */
    public Map<JanPai, Integer> getJanPaiMap() {
        return deepCopyMap(_hand);
    }
    
    /**
     * 手牌の数を取得
     * 
     * @return 手牌の数。
     */
    public int getSize() {
        int total = 0;
        for (final int value : _hand.values()) {
            total += value;
        }
        return total;
    }
    
    /**
     * 手牌を削除
     * 
     * @param pai 削除対象の牌。
     */
    public void remove(final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Target pai is null.");
        }
        
        final int count = _hand.get(pai);
        switch (count) {
        case 0:
            // 何もしない
            return;
        case 1:
        case 2:
        case 3:
        case 4:
            _hand.put(pai, count - 1);
            break;
        default:
            throw new InternalError();
        }
    }
    
    
    
    /**
     * マップをディープコピー
     * 
     * @param source 複製元マップ。
     * @return 複製結果。
     */
    private <S, T> Map<S, T> deepCopyMap(final Map<S, T> source) {
        return new TreeMap<S, T>(source);
    }
    
    
    
    /**
     * シリアルID
     */
    private static final long serialVersionUID = 1L;
    
    
    
    /**
     * 手牌
     */
    private final Map<JanPai, Integer> _hand = Collections.synchronizedMap(new TreeMap<JanPai, Integer>());
    
    /**
     * 固定面子リスト
     */
    private final List<MenTsu> _fixedMenTsuList = new CopyOnWriteArrayList<MenTsu>();
    
}
