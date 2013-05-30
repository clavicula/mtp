/**
 * TenpaiPatternThread.java
 * 
 * @Author
 *   Yuki Kawata
 */
package wiz.project.jan;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;




/**
 * 聴牌パターン取得スレッド
 * 
 *  ※高速化のため、オブジェクトはシャローコピーで扱う。
 */
public final class TenpaiPatternThread extends Thread {
    
    /**
     * コンストラクタ
     */
    public TenpaiPatternThread(final Map<JanPai, Integer> hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        _hand.putAll(hand);
    }
    
    
    
    /**
     * 聴牌パターンリストを取得 (スレッド終了後に呼び出すこと)
     * 
     * @return 聴牌パターンリスト。
     */
    public List<TenpaiPattern> getPatternList() {
        return _patternList;
    }
    
    /**
     * 和了済みか (スレッド終了後に呼び出すこと)
     * 
     * @return 判定結果。
     */
    public boolean isCompleted() {
        return _completed;
    }
    
    /**
     * 処理を実行
     */
    @Override
    public void run() {
        JanPaiUtil.removeEmptyJanPai(_hand);
        _completed = JanPaiUtil.isComplete(_hand);
        
        for (final JanPai pai : _hand.keySet()) {
            // 中断可能にしておく
            if (interrupted()) {
                return;
            }
            
            final Map<JanPai, Integer> pattern = deepCopyMap(_hand);
            JanPaiUtil.removeJanPai(pattern, pai, 1);
            final List<JanPai> completableList = JanPaiUtil.getCompletable(pattern);
            if (!completableList.isEmpty()) {
                final Map<JanPai, Integer> expectation = getExpectation(_hand, completableList);
                _patternList.add(new TenpaiPattern(pai, completableList, expectation));
            }
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
     * 期待枚数を取得
     * 
     * @param hand 手牌。
     * @param completableList 待ち牌リスト。
     * @return 期待枚数。
     */
    private Map<JanPai, Integer> getExpectation(final Map<JanPai, Integer> hand, final List<JanPai> completableList) {
        final Map<JanPai, Integer> expectation = new TreeMap<JanPai, Integer>();
        for (final JanPai pai : completableList) {
            if (!hand.containsKey(pai)) {
                expectation.put(pai, 4);
            }
            else {
                final int count = hand.get(pai);
                expectation.put(pai, 4 - count);
            }
        }
        return expectation;
    }
    
    
    
    /**
     * 手牌
     */
    private final Map<JanPai, Integer> _hand = new TreeMap<JanPai, Integer>();
    
    /**
     * 和了済みか
     */
    private volatile boolean _completed = false;
    
    /**
     * 聴牌パターンリスト
     */
    private final List<TenpaiPattern> _patternList = new CopyOnWriteArrayList<TenpaiPattern>();
    
}
