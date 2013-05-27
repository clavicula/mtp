/**
 * HandManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.List;
import java.util.Map;

import wiz.project.jan.JanPai;



/**
 * 手牌管理
 */
interface HandManager {
    
    /**
     * 手牌を追加
     * 
     * @param pai 追加対象の牌。
     */
    public void add(final JanPai pai);
    
    /**
     * 手牌を全消去
     */
    public void clear();
    
    /**
     * 手牌リストを取得
     * 
     * @return 理牌済みの手牌リスト。
     */
    public List<JanPai> getPaiList();
    
    /**
     * 手牌マップを取得
     * 
     * @return 各牌が何枚あるかを示す手牌マップ。
     */
    public Map<JanPai, Integer> getPaiMap();
    
    /**
     * 手牌の数を取得
     * 
     * @return 手牌の数。
     */
    public int getSize();
    
    /**
     * 手牌を削除
     * 
     * @param pai 削除対象の牌。
     */
    public void remove(final JanPai pai);
    
}
