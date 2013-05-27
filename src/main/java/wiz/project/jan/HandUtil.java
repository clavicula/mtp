/**
 * HandUtil.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**
 * 手牌ユーティリティ
 */
public final class HandUtil {
    
    /**
     * コンストラクタ利用禁止
     */
    private HandUtil() {}
    
    
    
    /**
     * 雀牌を追加
     * 
     * @param source 追加元の牌Map。
     * @param key 追加する牌。
     * @param value 追加枚数。
     */
    public static void addJanPai(final Map<JanPai, Integer> source, final JanPai key, final int value) {
        if (source == null) {
            throw new NullPointerException("Source map is null.");
        }
        if (key == null) {
            throw new NullPointerException("Target pai is null.");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Invalid value - " + value);
        }
        
        int count = 0;
        if (source.containsKey(key)) {
            count = source.get(key);
        }
        
        final int target = count + value;
        if (target > 4) {
            throw new IllegalArgumentException("Failure - " + count + " : " + value);
        }
        source.put(key, target);
    }
    
    /**
     * リーチ可能判定
     * 
     * @param hand 手牌。
     * @return 判定結果。
     */
    public static boolean canTenpai(final Map<JanPai, Integer> hand) {
        return !getTenpaiPatternList(hand).isEmpty();
    }
    
    /**
     * 待ち牌を取得
     * 
     * @param hand 手牌。
     * @return 待ち牌リスト。
     */
    public static List<JanPai> getCompletable(final Map<JanPai, Integer> hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        
        final List<JanPai> resultList = new ArrayList<JanPai>();
        for (final JanPai pai : JanPai.values()) {
            if (hand.containsKey(pai) && hand.get(pai) >= 4) {
                // 既に4牌持っている
                continue;
            }
            
            final Map<JanPai, Integer> pattern = deepCopyMap(hand);
            addJanPai(pattern, pai, 1);
            if (isComplete(pattern)) {
                resultList.add(pai);
            }
        }
        return resultList;
    }
    
    /**
     * 聴牌パターンリストを取得
     * 
     * @param hand 手牌。
     * @return 聴牌パターンリスト。
     */
    public static List<TenpaiPattern> getTenpaiPatternList(final Map<JanPai, Integer> hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        
        final List<TenpaiPattern> resultList = new ArrayList<TenpaiPattern>();
        for (final JanPai pai : hand.keySet()) {
            final Map<JanPai, Integer> pattern = deepCopyMap(hand);
            removeJanPai(pattern, pai, 1);
            final List<JanPai> completableList = getCompletable(pattern);
            if (!completableList.isEmpty()) {
                final int expectation = getExpectation(hand, completableList);
                resultList.add(new TenpaiPattern(pai, completableList, expectation));
            }
        }
        return resultList;
    }
    
    /**
     * 和了判定
     * 
     * @param hand 手牌。
     * @return 判定結果。
     */
    public static boolean isComplete(final Map<JanPai, Integer> hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        
        final List<Map<JanPai, Integer>> excludeHeadPattern = getExcludeHeadPattern(hand);
        for (final Map<JanPai, Integer> pattern : excludeHeadPattern) {
            final Map<JanPai, Integer> copy = deepCopyMap(pattern);
            
            // 順子優先パターン
            removeShunTsu(pattern);
            removeKohTsu(pattern);
            if (pattern.isEmpty()) {
                return true;
            }
            
            // 刻子優先パターン
            removeKohTsu(copy);
            removeShunTsu(copy);
            if (copy.isEmpty()) {
                return true;
            }
        }
        
        // 七対子は後で判定 (二盃口対策)
        if (isCompleteChiToi(hand)) {
            return true;
        }
        if (isCompleteKokushi(hand)) {
            return true;
        }
        return false;
    }
    
    /**
     * 聴牌判定
     * 
     * @param hand 手牌。
     * @return 判定結果。
     */
    public static boolean isTenpai(final Map<JanPai, Integer> hand) {
        return !getCompletable(hand).isEmpty();
    }
    
    /**
     * 存在しない牌を削除
     * 
     * @param source 削除元の牌Map。
     */
    public static void removeEmptyJanPai(final Map<JanPai, Integer> source) {
        if (source == null) {
            throw new NullPointerException("Source map is null.");
        }
        
        final List<JanPai> targetList = new ArrayList<JanPai>();
        for (final Map.Entry<JanPai, Integer> entry : source.entrySet()) {
            if (entry.getValue() <= 0) {
                targetList.add(entry.getKey());
            }
        }
        for (final JanPai target : targetList) {
            source.remove(target);
        }
    }
    
    /**
     * 雀牌を削除
     * 
     * @param source 削除元の牌Map。
     * @param key 削除する牌。
     * @param value 削除枚数。
     */
    public static void removeJanPai(final Map<JanPai, Integer> source, final JanPai key, final int value) {
        if (source == null) {
            throw new NullPointerException("Source map is null.");
        }
        if (key == null) {
            throw new NullPointerException("Target pai is null.");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Invalid value - " + value);
        }
        
        final int count = source.get(key);
        if (count < value) {
            throw new IllegalArgumentException("Failure - " + count + " : " + value);
        }
        
        if (count == value) {
            // キーごと削除
            source.remove(key);
        }
        else {
            source.put(key, count - value);
        }
    }
    
    
    
    /**
     * マップをディープコピー
     * 
     * @param source 複製元。
     * @return 複製結果。
     */
    private static <S, T> Map<S, T> deepCopyMap(final Map<S, T> source) {
        return new TreeMap<S, T>(source);
    }
    
    /**
     * 雀頭除外パターンを取得
     * 
     * @param paiMap 牌マップ。
     * @return 雀頭候補の除外パターン。
     */
    private static List<Map<JanPai, Integer>> getExcludeHeadPattern(final Map<JanPai, Integer> paiMap) {
        final List<Map<JanPai, Integer>> resultList = new ArrayList<Map<JanPai, Integer>>();
        for (final Map.Entry<JanPai, Integer> entry : paiMap.entrySet()) {
            final int count = entry.getValue();
            if (count >= 2) {
                final Map<JanPai, Integer> pattern = deepCopyMap(paiMap);
                removeJanPai(pattern, entry.getKey(), 2);
                resultList.add(pattern);
            }
        }
        return resultList;
    }
    
    /**
     * 期待枚数を取得
     * 
     * @param hand 手牌。
     * @param completableList 待ち牌リスト。
     * @return 期待枚数。
     */
    private static int getExpectation(final Map<JanPai, Integer> hand, final List<JanPai> completableList) {
        int result = 0;
        for (final JanPai pai : completableList) {
            if (!hand.containsKey(pai)) {
                result += 4;
            }
            else {
                final int count = hand.get(pai);
                result += (4 - count);
            }
        }
        return result;
    }
    
    /**
     * 七対子和了か
     * 
     * @param hand 手牌。
     * @return 判定結果。
     */
    private static boolean isCompleteChiToi(final Map<JanPai, Integer> hand) {
        int typeCount = 0;
        for (final Map.Entry<JanPai, Integer> entry : hand.entrySet()) {
            if (entry.getValue() != 2) {
                return false;
            }
            typeCount++;
        }
        return typeCount == 7;
    }
    
    /**
     * 國士無双和了か
     * 
     * @param hand 手牌。
     * @return 判定結果。
     */
    private static boolean isCompleteKokushi(final Map<JanPai, Integer> hand) {
        int headCount = 0;
        int typeCount = 0;
        for (final Map.Entry<JanPai, Integer> entry : hand.entrySet()) {
            if (!entry.getKey().isYao()) {
                return false;
            }
            switch (entry.getValue()) {
            case 1:
                break;
            case 2:
                headCount++;
                break;
            default:
                return false;
            }
            typeCount++;
        }
        return headCount == 1 && typeCount == 13;
    }
    
    /**
     * 順子か
     * 
     * @param x 先頭の牌。
     * @param y 中央の牌。
     * @param z 末尾の牌。
     * @return 判定結果。
     */
    private static boolean isShunTsu(final JanPai x, final JanPai y, final JanPai z) {
        return x.getNext() == y && y.getNext() == z;
    }
    
    /**
     * 刻子を削除
     * 
     * @param paiMap 削除元の牌マップ。
     */
    private static void removeKohTsu(final Map<JanPai, Integer> paiMap) {
        final List<JanPai> kohTsu = new ArrayList<JanPai>();
        for (final Map.Entry<JanPai, Integer> entry : paiMap.entrySet()) {
            if (entry.getValue() >= 3) {
                kohTsu.add(entry.getKey());
            }
        }
        for (final JanPai target : kohTsu) {
            removeJanPai(paiMap, target, 3);
        }
    }
    
    /**
     * 順子を削除
     * 
     * @param paiMap 削除元の牌マップ。
     */
    private static void removeShunTsu(final Map<JanPai, Integer> paiMap) {
        boolean foundShunTsu;
        do {
            foundShunTsu = false;
            final List<JanPai> source = new ArrayList<JanPai>(paiMap.keySet());
            for (int i = 2; i < source.size(); i++) {
                final JanPai x = source.get(i - 2);
                final JanPai y = source.get(i - 1);
                final JanPai z = source.get(i);
                if (isShunTsu(x, y, z)) {
                    removeJanPai(paiMap, x, 1);
                    removeJanPai(paiMap, y, 1);
                    removeJanPai(paiMap, z, 1);
                    
                    // 順子が見つかった場合、リスト再構築 (一盃口対策)
                    foundShunTsu = true;
                    break;
                }
            }
        }
        while (foundShunTsu && !paiMap.isEmpty());
    }
    
}

