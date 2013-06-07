/**
 * TenpaiPatternThread.java
 * 
 * @Author
 *   Yuki Kawata
 */
package actroid.mtp;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import wiz.project.jan.Hand;
import wiz.project.jan.JanPai;
import wiz.project.jan.TenpaiPattern;
import wiz.project.jan.util.HandCheckUtil;
import wiz.project.jan.util.JanPaiUtil;




/**
 * 聴牌パターン取得スレッド
 * 
 *  ※高速化のため、オブジェクトはシャローコピーで扱う。
 */
public final class TenpaiPatternThread extends Thread {
    
    /**
     * コンストラクタ
     * 
     * @param hand 手牌。
     */
    public TenpaiPatternThread(final Hand hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        _hand = hand;
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
     * 判定済みか
     * 
     * @return 判定結果。
     */
    public boolean isFinished() {
        return _finished;
    }
    
    /**
     * 処理を実行
     */
    @Override
    public void run() {
        final Map<JanPai, Integer> menZen = _hand.getMenZenMap();
        JanPaiUtil.cleanJanPaiMap(menZen);
        _completed = HandCheckUtil.isComplete(menZen);
        
        for (final JanPai pai : menZen.keySet()) {
            // 中断可能にしておく
            if (interrupted()) {
                return;
            }
            
            final Map<JanPai, Integer> pattern = deepCopyMap(menZen);
            JanPaiUtil.removeJanPai(pattern, pai, 1);
            final List<JanPai> completableList = HandCheckUtil.getCompletableJanPaiList(pattern);
            if (!completableList.isEmpty()) {
                final Map<JanPai, Integer> expectation = getExpectation(_hand, completableList);
                _patternList.add(new TenpaiPattern(pai, completableList, expectation));
            }
        }
        
        _finished = true;
    }
    
    
    
    /**
     * マップをディープコピー
     * 
     * @param source 複製元。
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
    private Map<JanPai, Integer> getExpectation(final Hand hand, final List<JanPai> completableList) {
        final Map<JanPai, Integer> source = hand.getAllJanPaiMap();
        final Map<JanPai, Integer> expectation = new TreeMap<JanPai, Integer>();
        for (final JanPai pai : completableList) {
            final int count = source.get(pai);
            expectation.put(pai, 4 - count);
        }
        return expectation;
    }
    
    
    
    /**
     * 手牌
     */
    private final Hand _hand;
    
    /**
     * 和了済みか
     */
    private volatile boolean _completed = false;
    
    /**
     * 判定済みか
     */
    private volatile boolean _finished = false;
    
    /**
     * 聴牌パターンリスト
     */
    private final List<TenpaiPattern> _patternList = new CopyOnWriteArrayList<TenpaiPattern>();
    
}
