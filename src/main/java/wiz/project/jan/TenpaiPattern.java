/**
 * TenpaiPattern.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * 聴牌パターン (不変オブジェクト)
 */
public final class TenpaiPattern implements Serializable {

    /**
     * コンストラクタ
     * 
     * @param discard 捨て牌。
     * @param completableList 待ち牌リスト。
     * @param expectation 期待枚数。
     */
    public TenpaiPattern(final JanPai discard, final List<JanPai> completableList, final int expectation) {
        setDiscard(discard);
        setCompletableList(completableList);
        setExpectation(expectation);
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
        if (!(target instanceof TenpaiPattern)) {
            return false;
        }
        
        final TenpaiPattern targetPattern = (TenpaiPattern)target;
        return (_discard == targetPattern._discard) &&
               _completableList.equals(targetPattern._completableList) &&
               (_expectation == targetPattern._expectation);
    }
    
    /**
     * 待ち牌リストを取得
     * 
     * @return 待ち牌リスト。
     */
    public List<JanPai> getCompletableList() {
        return deepCopyList(_completableList);
    }
    
    /**
     * 捨て牌を取得
     * 
     * @return 捨て牌。
     */
    public JanPai getDiscard() {
        return _discard;
    }
    
    /**
     * 期待枚数を取得
     * 
     * @return 期待枚数。
     */
    public int getExpectation() {
        return _expectation;
    }
    
    /**
     * ハッシュコードを取得
     * 
     * @return ハッシュコード。
     */
    @Override
    public int hashCode() {
        return _discard.hashCode() + _completableList.hashCode() + _expectation;
    }
    
    /**
     * 文字列に変換
     * 
     * @return 変換結果。
     */
    @Override
    public String toString() {
        return _discard + " -> " + _completableList + " (" + _expectation + ")";
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
     * 待ち牌リストを設定
     * 
     * @param completableList 待ち牌リスト。
     */
    private void setCompletableList(final List<JanPai> completableList) {
        if (completableList != null) {
            _completableList = deepCopyList(completableList);
        }
        else {
            _completableList.clear();
        }
    }
    
    /**
     * 捨て牌を設定
     * 
     * @param pai 捨て牌。
     */
    private void setDiscard(final JanPai pai) {
        if (pai != null) {
            _discard = pai;
        }
        else {
            _discard = JanPai.HAKU;
        }
    }
    
    /**
     * 期待枚数を設定
     * 
     * @param value 期待枚数。
     */
    private void setExpectation(final int value) {
        if (value > 0) {
            _expectation = value;
        }
        else {
            _expectation = 0;
        }
    }
    
    
    
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;
    
    
    
    /**
     * 捨て牌
     */
    private JanPai _discard = JanPai.HAKU;
    
    /**
     * 待ち牌リスト
     */
    private List<JanPai> _completableList = new ArrayList<JanPai>();
    
    /**
     * 期待枚数
     */
    private int _expectation = 0;
    
}

