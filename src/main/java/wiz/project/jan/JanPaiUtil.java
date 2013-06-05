/**
 * JanPaiUtil.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**
 * 雀牌ユーティリティ
 */
public final class JanPaiUtil {
    
    /**
     * コンストラクタ利用禁止
     */
    private JanPaiUtil() {}
    
    
    
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
    public static boolean canTenpai(final Hand hand) {
        return !getTenpaiPatternList(hand).isEmpty();
    }
    
    /**
     * 牌リストを変換
     * 
     * @param sourceList 変換元リスト。
     * @return 変換結果。
     */
    public static Map<JanPai, Integer> convertJanPaiList(final List<JanPai> sourceList) {
        final Map<JanPai, Integer> result = new TreeMap<JanPai, Integer>();
        for (final JanPai pai : sourceList) {
            addJanPai(result, pai, 1);
        }
        return result;
    }
    
    /**
     * 牌マップを変換
     * 
     * @param source 変換元。
     * @return 変換結果。
     */
    public static List<JanPai> convertJanPaiMap(final Map<JanPai, Integer> source) {
        final List<JanPai> resultList = new ArrayList<JanPai>();
        for (final Map.Entry<JanPai, Integer> entry : source.entrySet()) {
            final JanPai pai = entry.getKey();
            final int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                resultList.add(pai);
            }
        }
        return resultList;
    }
    
    /**
     * 全牌リストを生成
     * 
     * @return 全牌リスト。
     */
    public static List<JanPai> createAllJanPaiList() {
        final List<JanPai> resultList = new ArrayList<JanPai>();
        for (final JanPai pai : JanPai.values()) {
            for (int i = 0; i < 4; i++) {
                resultList.add(pai);
            }
        }
        return resultList;
    }
    
    /**
     * 全牌マップを生成
     * 
     * @return 全牌マップ。
     */
    public static Map<JanPai, Integer> createAllJanPaiMap() {
        final Map<JanPai, Integer> result = new TreeMap<JanPai, Integer>();
        for (final JanPai pai : JanPai.values()) {
            result.put(pai, 4);
        }
        return result;
    }
    
    /**
     * 山牌を生成
     * 
     * @return 山牌マップ。
     */
    public static Map<Wind, List<JanPai>> createDeck() {
        final List<JanPai> paiList = createAllJanPaiList();
        Collections.shuffle(paiList);
        
        final int deckSize = paiList.size() / 4;
        final Map<Wind, List<JanPai>> result = new TreeMap<Wind, List<JanPai>>();
        int deckCount = 0;
        for (final Wind wind : Wind.values()) {
            final List<JanPai> deck = new ArrayList<JanPai>();
            for (int j = 0; j < deckSize; j++) {
                final int index = (deckSize * deckCount) + j;
                deck.add(paiList.get(index));
            }
            result.put(wind, deck);
            deckCount++;
        }
        return result;
    }
    
    /**
     * 待ち牌を取得
     * 
     * @param hand 手牌。
     * @return 待ち牌リスト。
     */
    public static List<JanPai> getCompletable(final Hand hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        
        final List<JanPai> resultList = new ArrayList<JanPai>();
        final Map<JanPai, Integer> source = hand.getMenZenMap();
        removeEmptyJanPai(source);
        for (final JanPai pai : JanPai.values()) {
            if (hand.getJanPaiCount(pai) >= 4) {
                // 既に4牌持っている
                continue;
            }
            
            final Map<JanPai, Integer> pattern = deepCopyMap(source);
            addJanPai(pattern, pai, 1);
            if (isComplete(pattern)) {
                resultList.add(pai);
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
    public static Map<JanPai, Integer> getExpectation(final Hand hand, final List<JanPai> completableList) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        if (completableList == null) {
            throw new NullPointerException("Completable list is null.");
        }
        
        final Map<JanPai, Integer> source = hand.getAllJanPaiMap();
        final Map<JanPai, Integer> expectation = new TreeMap<JanPai, Integer>();
        for (final JanPai pai : completableList) {
            final int count = source.get(pai);
            expectation.put(pai, 4 - count);
        }
        return expectation;
    }
    
    /**
     * 雀頭候補リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 雀頭候補リスト。
     */
    public static List<List<JanPai>> getHeadList(final Map<JanPai, Integer> source) {
        final List<List<JanPai>> resultList = new ArrayList<List<JanPai>>();
        for (final JanPai pai : JanPai.values()) {
            if (!source.containsKey(pai)) {
                continue;
            }
            final int count = source.get(pai);
            if (count >= 2) {
                removeJanPai(source, pai, 2);
                final List<JanPai> head = new ArrayList<JanPai>();
                for (int i = 0; i < 2; i++) {
                    head.add(pai);
                }
                resultList.add(head);
            }
        }
        return resultList;
    }
    
    /**
     * 総枚数をカウント
     * 
     * @param source カウント元。
     * @return 総枚数。
     */
    public static int getJanPaiCount(final Map<JanPai, Integer> source) {
        int total = 0;
        for (final int count : source.values()) {
            total += count;
        }
        return total;
    }
    
    /**
     * 字牌刻子リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 字牌刻子リスト。
     */
    public static List<MenTsu> getJiKouTsuList(final Map<JanPai, Integer> source) {
        final List<MenTsu> resultList = new ArrayList<MenTsu>();
        for (final JanPai pai : JanPaiUtil.JI_LIST) {
            if (!source.containsKey(pai)) {
                continue;
            }
            final int count = source.get(pai);
            if (count == 3) {
                source.remove(pai);
                final List<JanPai> kouTsu = new ArrayList<JanPai>();
                for (int i = 0; i < 3; i++) {
                    kouTsu.add(pai);
                }
                resultList.add(new MenTsu(kouTsu));
            }
        }
        return resultList;
    }
    
    /**
     * 字牌四枚刻子リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 字牌四枚刻子リスト。
     */
    public static List<MenTsu> getJiKouTsuExList(final Map<JanPai, Integer> source) {
        final List<MenTsu> resultList = new ArrayList<MenTsu>();
        for (final JanPai pai : JanPaiUtil.JI_LIST) {
            if (!source.containsKey(pai)) {
                continue;
            }
            final int count = source.get(pai);
            if (count >= 4) {
                source.remove(pai);
                final List<JanPai> kouTsuEx = new ArrayList<JanPai>();
                for (int i = 0; i < count; i++) {
                    kouTsuEx.add(pai);
                }
                resultList.add(new MenTsu(kouTsuEx));
            }
        }
        return resultList;
    }
    
    /**
     * 一枚だけの字牌リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 一枚だけの字牌リスト。
     */
    public static List<JanPai> getJiTankiList(final Map<JanPai, Integer> source) {
        final List<JanPai> resultList = new ArrayList<JanPai>();
        for (final JanPai pai : JanPaiUtil.JI_LIST) {
            if (!source.containsKey(pai)) {
                continue;
            }
            final int count = source.get(pai);
            if (count == 1) {
                source.remove(pai);
                resultList.add(pai);
            }
        }
        return resultList;
    }
    
    /**
     * 刻子リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 刻子リスト。
     */
    public static List<MenTsu> getKouTsuList(final Map<JanPai, Integer> source) {
        final List<MenTsu> resultList = new ArrayList<MenTsu>();
        for (final JanPai pai : JanPai.values()) {
            if (!source.containsKey(pai)) {
                continue;
            }
            final int count = source.get(pai);
            if (count == 3) {
                source.remove(pai);
                final List<JanPai> kouTsu = new ArrayList<JanPai>();
                for (int i = 0; i < 3; i++) {
                    kouTsu.add(pai);
                }
                resultList.add(new MenTsu(kouTsu));
            }
        }
        return resultList;
    }
    
    /**
     * 四枚刻子リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 四枚刻子リスト。
     */
    public static List<MenTsu> getKouTsuExList(final Map<JanPai, Integer> source) {
        final List<MenTsu> resultList = new ArrayList<MenTsu>();
        for (final JanPai pai : JanPai.values()) {
            if (!source.containsKey(pai)) {
                continue;
            }
            final int count = source.get(pai);
            if (count >= 4) {
                source.remove(pai);
                final List<JanPai> kouTsuEx = new ArrayList<JanPai>();
                for (int i = 0; i < count; i++) {
                    kouTsuEx.add(pai);
                }
                resultList.add(new MenTsu(kouTsuEx));
            }
        }
        return resultList;
    }
    
    /**
     * 両面待ち可能な牌リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 両面待ち可能な牌リスト。
     */
    public static List<List<JanPai>> getRyanMenList(final Map<JanPai, Integer> source) {
        final List<List<JanPai>> resultList = new ArrayList<List<JanPai>>();
        final List<JanPai> keyList = new ArrayList<JanPai>(source.keySet());
        for (int i = 1; i < keyList.size(); i++) {
            final JanPai x = keyList.get(i - 1);
            final JanPai y = keyList.get(i);
            if (x.getNext() == y) {
                final List<JanPai> ryanMen = Arrays.asList(x, y);
                for (final JanPai pai : ryanMen) {
                    removeJanPai(source, pai, 1);
                }
                resultList.add(ryanMen);
            }
        }
        return resultList;
    }
    
    /**
     * 順子リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @return 順子リスト。
     */
    public static List<MenTsu> getShunTsuList(final Map<JanPai, Integer> source) {
        final List<MenTsu> resultList = new ArrayList<MenTsu>();
        boolean foundShunTsu;
        do {
            foundShunTsu = false;
            final List<JanPai> keyList = new ArrayList<JanPai>(source.keySet());
            for (int i = 2; i < keyList.size(); i++) {
                final JanPai x = keyList.get(i - 2);
                final JanPai y = keyList.get(i - 1);
                final JanPai z = keyList.get(i);
                if (isShunTsu(x, y, z)) {
                    final List<JanPai> shunTsu = Arrays.asList(x, y, z);
                    for (final JanPai pai : shunTsu) {
                        removeJanPai(source, pai, 1);
                    }
                    resultList.add(new MenTsu(shunTsu));
                    
                    // 順子が見つかった場合、リスト再構築 (一盃口対策)
                    foundShunTsu = true;
                    break;
                }
            }
        }
        while (foundShunTsu && !source.isEmpty());
        return resultList;
    }
    
    /**
     * 指定牌を含む順子リストを取得
     * 
     * @param source 取得元。取得分の牌が削除される。
     * @param target 順子に含めたい牌。
     * @return 順子リスト。
     */
    public static List<MenTsu> getShunTsuListWith(final Map<JanPai, Integer> source, final JanPai target) {
        final List<MenTsu> resultList = new ArrayList<MenTsu>();
        boolean foundShunTsu;
        do {
            foundShunTsu = false;
            final List<JanPai> keyList = new ArrayList<JanPai>(source.keySet());
            for (int i = 2; i < keyList.size(); i++) {
                final JanPai x = keyList.get(i - 2);
                final JanPai y = keyList.get(i - 1);
                final JanPai z = keyList.get(i);
                if (x != target && y != target && z != target) {
                    continue;
                }
                if (isShunTsu(x, y, z)) {
                    final List<JanPai> shunTsu = Arrays.asList(x, y, z);
                    for (final JanPai pai : shunTsu) {
                        removeJanPai(source, pai, 1);
                    }
                    resultList.add(new MenTsu(shunTsu));
                    
                    // 順子が見つかった場合、リスト再構築 (一盃口対策)
                    foundShunTsu = true;
                    break;
                }
            }
        }
        while (foundShunTsu && !source.isEmpty());
        return resultList;
    }
    
    /**
     * 聴牌パターンリストを取得
     * 
     * @param hand 手牌。
     * @return 聴牌パターンリスト。
     */
    public static List<TenpaiPattern> getTenpaiPatternList(final Hand hand) {
        if (hand == null) {
            throw new NullPointerException("Hand is null.");
        }
        
        final List<TenpaiPattern> resultList = new ArrayList<TenpaiPattern>();
        final Map<JanPai, Integer> menZen = hand.getMenZenMap();
        removeEmptyJanPai(menZen);
        for (final JanPai pai : menZen.keySet()) {
            final Hand pattern = hand.clone();
            pattern.removeJanPai(pai);
            final List<JanPai> completableList = getCompletable(pattern);
            if (!completableList.isEmpty()) {
                final Map<JanPai, Integer> expectation = getExpectation(hand, completableList);
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
        if (excludeHeadPattern.isEmpty()) {
            // 雀頭候補が存在しない
            return false;
        }
        
        for (final Map<JanPai, Integer> pattern : excludeHeadPattern) {
            if (pattern.isEmpty()) {
            // 裸単騎状態で和了
                return true;
            }
            
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
    public static boolean isTenpai(final Hand hand) {
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
    
    
    
    /**
     * ヤオ九牌リスト
     */
    public static final List<JanPai> YAO_LIST =
        Collections.unmodifiableList(Arrays.asList(JanPai.MAN_1,
                                                   JanPai.MAN_9,
                                                   JanPai.PIN_1,
                                                   JanPai.PIN_9,
                                                   JanPai.SOU_1,
                                                   JanPai.SOU_9,
                                                   JanPai.TON,
                                                   JanPai.NAN,
                                                   JanPai.SHA,
                                                   JanPai.PEI,
                                                   JanPai.HAKU,
                                                   JanPai.HATU,
                                                   JanPai.CHUN));
    
    /**
     * 字牌リスト
     */
    public static final List<JanPai> JI_LIST =
        Collections.unmodifiableList(Arrays.asList(JanPai.TON,
                                                   JanPai.NAN,
                                                   JanPai.SHA,
                                                   JanPai.PEI,
                                                   JanPai.HAKU,
                                                   JanPai.HATU,
                                                   JanPai.CHUN));
    
}

