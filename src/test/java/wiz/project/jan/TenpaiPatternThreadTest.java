/**
 * TenpaiPatternThreadTest.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;



/**
 * TenpaiPatternThreadのテスト
 */
public class TenpaiPatternThreadTest extends TestCase {
    
    /**
     * コンストラクタ
     * 
     * @param name テストクラス名。
     */
    public TenpaiPatternThreadTest(final String name) {
        super(name);
    }
    
    
    
    /**
     * getPatternList() のテスト
     */
    public void testGetPatternList() {
        {
            // 正常 (単騎)
            final Map<JanPai, Integer> hand = createHand14(1, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            final List<TenpaiPattern> resultList = thread.getPatternList();
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_1, Arrays.asList(JanPai.MAN_9), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_9, Arrays.asList(JanPai.MAN_1), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (両面)
            final Map<JanPai, Integer> hand = createHand14(2, 3, 6, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            final List<TenpaiPattern> resultList = thread.getPatternList();
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_1, JanPai.MAN_4), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (嵌張)
            final Map<JanPai, Integer> hand = createHand14(2, 4, 6, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            final List<TenpaiPattern> resultList = thread.getPatternList();
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_2, Arrays.asList(JanPai.MAN_5), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_3), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (辺張)
            final Map<JanPai, Integer> hand = createHand14(1, 2, 6, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            final List<TenpaiPattern> resultList = thread.getPatternList();
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_3), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (シャボ)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 6, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            final List<TenpaiPattern> resultList = thread.getPatternList();
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_1, JanPai.MAN_9), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (フリテン九蓮宝塔)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            final List<TenpaiPattern> resultList = thread.getPatternList();
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_1, Arrays.asList(JanPai.MAN_1, JanPai.MAN_5), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_2, Arrays.asList(JanPai.MAN_2, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_7, JanPai.MAN_8), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_3, Arrays.asList(JanPai.MAN_3), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_4, Arrays.asList(JanPai.MAN_1, JanPai.MAN_4, JanPai.MAN_5), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_5, Arrays.asList(JanPai.MAN_1, JanPai.MAN_2, JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_6, JanPai.MAN_7, JanPai.MAN_8, JanPai.MAN_9), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_5, JanPai.MAN_6, JanPai.MAN_9), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_7, Arrays.asList(JanPai.MAN_7), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_8, Arrays.asList(JanPai.MAN_2, JanPai.MAN_3, JanPai.MAN_5, JanPai.MAN_6, JanPai.MAN_8), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_9, Arrays.asList(JanPai.MAN_5, JanPai.MAN_9), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 性能
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            
            final long startTime = System.currentTimeMillis();
            callThread(thread);
            thread.getPatternList();
            final long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) + "[ms]");
        }
    }
    
    /**
     * isCompleted() のテスト
     */
    public void testIsCompleted() {
        {
            // 正常 (和了済み)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            
            assertTrue(thread.isCompleted());
        }
        {
            // 正常 (未和了)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 6, 7, 7, 8, 9, 9, 9, 9);
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            callThread(thread);
            
            assertFalse(thread.isCompleted());
        }
    }
    
    /**
     * スレッド実行のテスト
     */
    public void testRun() throws InterruptedException {
        {
            // 正常 (処理の前後でシャローコピーした手牌が変化しない)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 6, 7, 7, 8, 9, 9, 9, 9);
            final Map<JanPai, Integer> buf = deepCopyMap(hand);
            
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            thread.start();
            thread.join();
            
            assertEquals(hand, buf);
        }
        {
            // 正常 (中断可能)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 6, 7, 7, 8, 9, 9, 9, 9);
            
            final TenpaiPatternThread thread = createTenpaiPatternThread(hand);
            thread.start();
            thread.interrupt();
            
            assertTrue(thread.getPatternList().size() < 9);
        }
    }
    
    
    
    /**
     * スレッドを同期呼び出し
     * 
     * @param thread 呼び出し対象スレッド。
     */
    private void callThread(final Thread thread) {
        thread.start();
        
        try {
            thread.join();
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 手牌を生成
     * 
     * @param valueList 数牌リスト。
     * @return 手牌。14枚固定。
     */
    private Map<JanPai, Integer> createHand14(final int... valueList) {
        return createHandCore(14, valueList);
    }
    
    /**
     * 手牌を生成
     * 
     * @param base 枚数。
     * @param valueList 数牌リスト。
     * @return 手牌。
     */
    private Map<JanPai, Integer> createHandCore(final int base, final int[] valueList) {
        final Map<JanPai, Integer> result = new TreeMap<JanPai, Integer>();
        int count = 0;
        for (final int value : valueList) {
            switch (value) {
            case 1:
                HandUtil.addJanPai(result, JanPai.MAN_1, 1);
                break;
            case 2:
                HandUtil.addJanPai(result, JanPai.MAN_2, 1);
                break;
            case 3:
                HandUtil.addJanPai(result, JanPai.MAN_3, 1);
                break;
            case 4:
                HandUtil.addJanPai(result, JanPai.MAN_4, 1);
                break;
            case 5:
                HandUtil.addJanPai(result, JanPai.MAN_5, 1);
                break;
            case 6:
                HandUtil.addJanPai(result, JanPai.MAN_6, 1);
                break;
            case 7:
                HandUtil.addJanPai(result, JanPai.MAN_7, 1);
                break;
            case 8:
                HandUtil.addJanPai(result, JanPai.MAN_8, 1);
                break;
            case 9:
                HandUtil.addJanPai(result, JanPai.MAN_9, 1);
                break;
            default:
                throw new InternalError();
            }
            count++;
        }
        
        // 残りを適当に字牌で埋める
        final List<JanPai> jiList = Arrays.asList(JanPai.TON, JanPai.NAN, JanPai.SHA, JanPai.PEI);
        for (int i = 0; (base - count) >= 3; i++) {
            HandUtil.addJanPai(result, jiList.get(i), 3);
            count += 3;
        }
        final int blank = base - count;
        if (blank > 0) {
            HandUtil.addJanPai(result, JanPai.HAKU, blank);
        }
        return result;
    }
    
    /**
     * 聴牌パターンスレッドを生成
     * 
     * @param hand 手牌。
     * @return 聴牌パターンスレッド。
     */
    private TenpaiPatternThread createTenpaiPatternThread(final Map<JanPai, Integer> hand) {
        return new TenpaiPatternThread(hand);
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
    
}

