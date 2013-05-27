/**
 * HandUtilTest.java
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
 * HandUtilのテスト
 */
public final class HandUtilTest extends TestCase {
    
    /**
     * コンストラクタ
     * 
     * @param name テストクラス名。
     */
    public HandUtilTest(final String name) {
        super(name);
    }
    
    
    
    /**
     * canTenpai() のテスト
     */
    public void testCanTenpai() {
        fail("まだ実装されていません");
    }
    
    /**
     * getCompletable() のテスト
     */
    public void testGetCompletable() {
        {
            // 正常 (単騎)
            final Map<JanPai, Integer> hand = createHand13(1);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (両面)
            final Map<JanPai, Integer> hand = createHand13(2, 3, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1, JanPai.MAN_4);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (嵌張)
            final Map<JanPai, Integer> hand = createHand13(2, 4, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_3);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (辺張)
            final Map<JanPai, Integer> hand = createHand13(1, 2, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_3);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (シャボ)
            final Map<JanPai, Integer> hand = createHand13(1, 1, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1, JanPai.MAN_9);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン01)
            final Map<JanPai, Integer> hand = createHand13(2, 3, 4, 5, 6, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1, JanPai.MAN_4, JanPai.MAN_7);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン02)
            final Map<JanPai, Integer> hand = createHand13(2, 3, 4, 5, 6, 7, 8);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_5, JanPai.MAN_8);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン03)
            final Map<JanPai, Integer> hand = createHand13(2, 3, 4, 5, 5, 6, 7);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_5, JanPai.MAN_8);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン04)
            final Map<JanPai, Integer> hand = createHand13(2, 3, 4, 4, 4, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1, JanPai.MAN_4, JanPai.MAN_9);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン05)
            final Map<JanPai, Integer> hand = createHand13(2, 3, 3, 3, 4, 5, 6);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1, JanPai.MAN_2, JanPai.MAN_4, JanPai.MAN_7);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン06)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 4, 5, 6);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_7);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン07)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 5, 5, 6);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_7);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン08)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 5, 6, 7);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_7, JanPai.MAN_8);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン09)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 5, 6, 8);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_7, JanPai.MAN_8);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン10)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 5, 5, 6, 7);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_8);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン11)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 5, 6, 7, 8);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_8);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン12)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 4, 5, 5);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン13)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 4, 4, 4, 5, 5);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_5);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン14)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 5, 5, 5);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン15)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 4, 4, 4, 5);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン16)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 3, 5, 7, 7, 7);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン17)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 4, 5, 5, 5, 5);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン18)
            final Map<JanPai, Integer> hand = createHand13(3, 3, 4, 4, 4, 4, 5);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_3, JanPai.MAN_5, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (7枚多面待ち - パターン19)
            final Map<JanPai, Integer> hand = createHand13(3, 4, 4, 4, 4, 5, 6);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_2, JanPai.MAN_3, JanPai.MAN_5, JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (純正九蓮宝塔)
            final Map<JanPai, Integer> hand = createHand13(1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1, JanPai.MAN_2, JanPai.MAN_3, JanPai.MAN_4, JanPai.MAN_5, JanPai.MAN_6, JanPai.MAN_7, JanPai.MAN_8, JanPai.MAN_9);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (七対子)
            final Map<JanPai, Integer> hand = createHand13(1, 1, 3, 3, 4, 4, 5, 5, 6, 7, 7, 9, 9);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_6);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (国士無双)
            final Map<JanPai, Integer> hand = createKokushi13();
            HandUtil.removeJanPai(hand, JanPai.MAN_1, 1);
            HandUtil.addJanPai(hand, JanPai.MAN_9, 1);
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = Arrays.asList(JanPai.MAN_1);
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (純正国士無双)
            final Map<JanPai, Integer> hand = createKokushi13();
            final List<JanPai> resultList = HandUtil.getCompletable(hand);
            
            final List<JanPai> targetList = new ArrayList<JanPai>();
            for (final JanPai pai : JanPai.values()) {
                if (pai.isYao()) {
                    targetList.add(pai);
                }
            }
            assertEquals(targetList, resultList);
        }
        {
            // 性能
            final Map<JanPai, Integer> hand = createHand13(1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9);
            
            final long startTime = System.currentTimeMillis();
            HandUtil.getTenpaiPatternList(hand);
            final long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) + "[ms]");
        }
    }
    
    /**
     * getTenpaiPatternList() のテスト
     */
    public void testGetTenpaiPatternList() {
        {
            // 正常 (単騎)
            final Map<JanPai, Integer> hand = createHand14(1, 9);
            final List<TenpaiPattern> resultList = HandUtil.getTenpaiPatternList(hand);
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_1, Arrays.asList(JanPai.MAN_9), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_9, Arrays.asList(JanPai.MAN_1), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (両面)
            final Map<JanPai, Integer> hand = createHand14(2, 3, 6, 9, 9);
            final List<TenpaiPattern> resultList = HandUtil.getTenpaiPatternList(hand);
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_1, JanPai.MAN_4), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (嵌張)
            final Map<JanPai, Integer> hand = createHand14(2, 4, 6, 9, 9);
            final List<TenpaiPattern> resultList = HandUtil.getTenpaiPatternList(hand);
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_2, Arrays.asList(JanPai.MAN_5), 0));
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_3), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (辺張)
            final Map<JanPai, Integer> hand = createHand14(1, 2, 6, 9, 9);
            final List<TenpaiPattern> resultList = HandUtil.getTenpaiPatternList(hand);
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_3), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (シャボ)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 6, 9, 9);
            final List<TenpaiPattern> resultList = HandUtil.getTenpaiPatternList(hand);
            
            final List<TenpaiPattern> targetList = new ArrayList<TenpaiPattern>();
            targetList.add(new TenpaiPattern(JanPai.MAN_6, Arrays.asList(JanPai.MAN_1, JanPai.MAN_9), 0));
            assertEquals(targetList, resultList);
        }
        {
            // 正常 (フリテン九蓮宝塔)
            final Map<JanPai, Integer> hand = createHand14(1, 1, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9, 9);
            final List<TenpaiPattern> resultList = HandUtil.getTenpaiPatternList(hand);
            
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
            
            final long startTime = System.currentTimeMillis();
            HandUtil.getTenpaiPatternList(hand);
            final long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) + "[ms]");
        }
    }
    
    /**
     * isComplete() のテスト
     */
    public void testIsComplete() {
        fail("まだ実装されていません");
    }
    
    /**
     * isTenpai() のテスト
     */
    public void testIsTenpai() {
        fail("まだ実装されていません");
    }
    
    /**
     * removeEmptyJanPai() のテスト
     */
    public void testRemoveEmptyJanPai() {
        fail("まだ実装されていません");
    }
    
    
    
    /**
     * 手牌を生成
     * 
     * @param valueList 数牌リスト。
     * @return 手牌。13枚固定。
     */
    private Map<JanPai, Integer> createHand13(final int... valueList) {
        return createHandCore(13, valueList);
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
     * 国士無双13面待ちの手牌を生成
     */
    private Map<JanPai, Integer> createKokushi13() {
        final Map<JanPai, Integer> result = new TreeMap<JanPai, Integer>();
        for (final JanPai pai : JanPai.values()) {
            if (pai.isYao()) {
                HandUtil.addJanPai(result, pai, 1);
            }
        }
        return result;
    }
    
}
