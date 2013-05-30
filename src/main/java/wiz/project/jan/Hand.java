/**
 * Hand.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * 手牌
 */
public final class Hand implements Cloneable {
    
    /**
     * コンストラクタ
     */
    public Hand() {
    }
    
    /**
     * コンストラクタ
     * 
     * @param head 雀頭。
     * @param menTsuList 面子リスト。
     * @param extraList 浮き牌リスト。
     */
    public Hand(final JanPai head, final List<MenTsu> menTsuList, final List<JanPai> extraList) {
        setHead(head);
        setMenTsuList(menTsuList);
        setExtraList(extraList);
    }
    
    /**
     * コンストラクタ
     * 
     * @param head 雀頭。
     * @param menTsuList 面子リスト。
     * @param extraList 浮き牌リスト。
     */
    public Hand(final List<JanPai> head, final List<MenTsu> menTsuList, final List<JanPai> extraList) {
        setHead(head);
        setMenTsuList(menTsuList);
        setExtraList(extraList);
    }
    
    /**
     * コピーコンストラクタ
     * 
     * @param source 複製元オブジェクト。
     */
    public Hand(final Hand source) {
        if (source != null) {
            setHead(source._head);
            setMenTsuList(source._menTsuList);
            setExtraList(source._extraList);
        }
    }
    
    
    
    /**
     * 自分自身を複製 (ディープコピー)
     * 
     * @return 複製結果。
     */
    @Override
    public Hand clone() {
        return new Hand(this);
    }
    
    /**
     * 等価なオブジェクトか
     * 
     * @param target 比較対象。
     * @return 比較結果。
     */
    @Override
    public boolean equals(final Object target) {
        if (this == target) {
            return true;
        }
        if (target == null) {
            return false;
        }
        if (!(target instanceof Hand)) {
            return false;
        }
        
        final Hand targetHand = (Hand)target;
        return _head.equals(targetHand._head) &&
               _menTsuList.equals(targetHand._menTsuList) &&
               _extraList.equals(targetHand._extraList);
    }
    
    /**
     * 浮き牌を追加
     * 
     * @param pai 浮き牌。
     */
    public void addExtra(final JanPai pai) {
        if (pai != null) {
            _extraList.add(pai);
        }
    }
    
    /**
     * 面子を追加
     * 
     * @param menTsu 面子。
     */
    public void addMenTsu(final MenTsu menTsu) {
        if (menTsu != null) {
            _menTsuList.add(menTsu);
        }
    }
    
    /**
     * フィールドを全消去
     */
    public void clear() {
        _head.clear();
        _menTsuList.clear();
        _extraList.clear();
    }
    
    /**
     * 浮き牌を全消去
     */
    public void clearExtraList() {
        _extraList.clear();
    }
    
    /**
     * 面子を全消去
     */
    public void clearMenTsuList() {
        _menTsuList.clear();
    }
    
    /**
     * 浮き牌リストを取得
     * 
     * @return 浮き牌リスト。
     */
    public List<JanPai> getExtraList() {
        return deepCopyList(_extraList);
    }
    
    /**
     * 雀頭を取得
     * 
     * @return 雀頭。
     */
    public List<JanPai> getHead() {
        return deepCopyList(_head);
    }
    
    /**
     * 面子リストを取得
     * 
     * @return 面子リスト。
     */
    public List<MenTsu> getMenTsuList() {
        return deepCopyList(_menTsuList);
    }
    
    /**
     * 面前手牌枚数を取得
     * 
     * @return 面前手牌枚数。
     */
    public int getMenZenCount() {
        // TODO 鳴き面子の考慮
        return _head.size() + (_menTsuList.size() * 3) + _extraList.size();
    }
    
    /**
     * ハッシュコードを取得
     * 
     * @return ハッシュコード。
     */
    @Override
    public int hashCode() {
        return _head.hashCode() + _menTsuList.hashCode() + _extraList.hashCode();
    }
    
    /**
     * 指定牌を持っているか
     * 
     * @param target 検索対象。
     * @return 判定結果。
     */
    public boolean hasJanPai(final JanPai target) {
        if (_head.contains(target)) {
            return true;
        }
        for (final MenTsu menTsu : _menTsuList) {
            if (menTsu.hasJanPai(target)) {
                return true;
            }
        }
        return _extraList.contains(target);
    }
    
    /**
     * 浮き牌リストを設定
     * 
     * @param extraList 浮き牌リスト。
     */
    public void setExtraList(final List<JanPai> extraList) {
        if (extraList != null) {
            _extraList = deepCopyList(extraList);
        }
        else {
            _extraList.clear();
        }
    }
    
    /**
     * 雀頭を設定
     * 
     * @param head 雀頭牌。
     */
    public void setHead(final JanPai head) {
        if (head != null) {
            for (int i = 0; i < 2; i++) {
                _head.add(head);
            }
        }
        else {
            _head.clear();
        }
    }
    
    /**
     * 雀頭を設定
     * 
     * @param head 雀頭。
     */
    public void setHead(final List<JanPai> head) {
        if (head != null) {
            _head = deepCopyList(head);
        }
        else {
            _head.clear();
        }
    }
    
    /**
     * 面子リストを設定
     * 
     * @param menTsuList 面子リスト。
     */
    public void setMenTsuList(final List<MenTsu> menTsuList) {
        if (menTsuList != null) {
            _menTsuList = deepCopyList(menTsuList);
        }
        else {
            _menTsuList.clear();
        }
    }
    
    /**
     * 雀牌リストに変換
     * 
     * @return 変換結果。
     */
    public List<JanPai> toList() {
        final List<JanPai> resultList = new ArrayList<JanPai>();
        resultList.addAll(_head);
        for (final MenTsu menTsu : _menTsuList) {
            resultList.addAll(menTsu.getSource());
        }
        resultList.addAll(_extraList);
        Collections.sort(resultList);
        return resultList;
    }
    
    /**
     * 文字列に変換
     * 
     * @return 変換結果。
     */
    @Override
    public String toString() {
        return toList().toString();
    }
    
    
    
    /**
     * リストをディープコピー
     * 
     * @param sourceList 複製元リスト。
     * @return 複製結果。
     */
    private <E> List<E> deepCopyList(final List<E> sourceList) {
        return new ArrayList<E>(sourceList);
    }
    
    
    
    /**
     * 雀頭
     */
    private List<JanPai> _head = new ArrayList<JanPai>();
    
    /**
     * 面子リスト
     */
    private List<MenTsu> _menTsuList = new ArrayList<MenTsu>();
    
    /**
     * 浮き牌リスト
     */
    private List<JanPai> _extraList = new ArrayList<JanPai>();
    
}

