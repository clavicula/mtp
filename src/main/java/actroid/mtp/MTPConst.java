/**
 * MTPConst.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import wiz.project.jan.JanPai;



/**
 * 共通定数定義
 */
final class MTPConst {
    
    /**
     * アプリケーション名
     */
    public static final String APP_NAME = "めんたんぴん";
    
    /**
     * 手牌のキー
     */
    public static final String KEY_HAND = "Hand";
    
    /**
     * 聴牌パターンのキー
     */
    public static final String KEY_TENPAI_PATTERN = "Pattern";
    
    /**
     * 手牌ボタンIDリスト
     */
    public static final List<Integer> HAND_BUTTON_ID_LIST =
    Collections.unmodifiableList(Arrays.asList(R.id.image_hand_01,
    R.id.image_hand_02,
    R.id.image_hand_03,
    R.id.image_hand_04,
    R.id.image_hand_05,
    R.id.image_hand_06,
    R.id.image_hand_07,
    R.id.image_hand_08,
    R.id.image_hand_09,
    R.id.image_hand_10,
    R.id.image_hand_11,
    R.id.image_hand_12,
    R.id.image_hand_13,
    R.id.image_hand_14));
    
    /**
     * 手牌ボタンIDリスト
     */
    public static final List<Integer> RESULT_HAND_ID_LIST =
    Collections.unmodifiableList(Arrays.asList(R.id.result_hand_01,
    R.id.result_hand_02,
    R.id.result_hand_03,
    R.id.result_hand_04,
    R.id.result_hand_05,
    R.id.result_hand_06,
    R.id.result_hand_07,
    R.id.result_hand_08,
    R.id.result_hand_09,
    R.id.result_hand_10,
    R.id.result_hand_11,
    R.id.result_hand_12,
    R.id.result_hand_13,
    R.id.result_hand_14));
    
    /**
     * 雀牌ボタンIDマップ
     */
    public static final Map<JanPai, Integer> JAN_PAI_BUTTON_ID_MAP =
    Collections.unmodifiableMap(new LinkedHashMap<JanPai, Integer>() {
        {
            put(JanPai.MAN_1, R.id.image_button_man_1);
            put(JanPai.MAN_2, R.id.image_button_man_2);
            put(JanPai.MAN_3, R.id.image_button_man_3);
            put(JanPai.MAN_4, R.id.image_button_man_4);
            put(JanPai.MAN_5, R.id.image_button_man_5);
            put(JanPai.MAN_6, R.id.image_button_man_6);
            put(JanPai.MAN_7, R.id.image_button_man_7);
            put(JanPai.MAN_8, R.id.image_button_man_8);
            put(JanPai.MAN_9, R.id.image_button_man_9);
            put(JanPai.PIN_1, R.id.image_button_pin_1);
            put(JanPai.PIN_2, R.id.image_button_pin_2);
            put(JanPai.PIN_3, R.id.image_button_pin_3);
            put(JanPai.PIN_4, R.id.image_button_pin_4);
            put(JanPai.PIN_5, R.id.image_button_pin_5);
            put(JanPai.PIN_6, R.id.image_button_pin_6);
            put(JanPai.PIN_7, R.id.image_button_pin_7);
            put(JanPai.PIN_8, R.id.image_button_pin_8);
            put(JanPai.PIN_9, R.id.image_button_pin_9);
            put(JanPai.SOU_1, R.id.image_button_sou_1);
            put(JanPai.SOU_2, R.id.image_button_sou_2);
            put(JanPai.SOU_3, R.id.image_button_sou_3);
            put(JanPai.SOU_4, R.id.image_button_sou_4);
            put(JanPai.SOU_5, R.id.image_button_sou_5);
            put(JanPai.SOU_6, R.id.image_button_sou_6);
            put(JanPai.SOU_7, R.id.image_button_sou_7);
            put(JanPai.SOU_8, R.id.image_button_sou_8);
            put(JanPai.SOU_9, R.id.image_button_sou_9);
            put(JanPai.TON,   R.id.image_button_ton);
            put(JanPai.NAN,   R.id.image_button_nan);
            put(JanPai.SHA,   R.id.image_button_sha);
            put(JanPai.PEI,   R.id.image_button_pei);
            put(JanPai.HAKU,  R.id.image_button_haku);
            put(JanPai.HATU,  R.id.image_button_hatu);
            put(JanPai.CHUN,  R.id.image_button_chun);
        }
        
        private static final long serialVersionUID = 1L;
    });
    
}
