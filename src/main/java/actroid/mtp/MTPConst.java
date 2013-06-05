/**
 * MTPConst.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import wiz.project.jan.JanPai;



/**
 * 共通定数定義
 */
public final class MTPConst {
    
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
     * 手牌ビューのベースID (手牌入力画面)
     */
    public static final int HAND_VIEW_MAIN_BASE_ID = 0xFF00;
    
    /**
     * 確定面子ビューのベースID (手牌入力画面)
     */
    public static final int FIXED_VIEW_MAIN_BASE_ID = 0xEF00;
    
    /**
     * 手牌ビューのベースID (判定結果画面)
     */
    public static final int HAND_VIEW_RESULT_BASE_ID = 0xDF00;
    
    /**
     * 確定面子ビューのベースID (判定結果画面)
     */
    public static final int FIXED_VIEW_RESULT_BASE_ID = 0xCF00;
    
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
    
    /**
     * チー牌選択ボタンIDマップ
     */
    public static final Map<JanPai, Integer> CHI_JAN_PAI_BUTTON_ID_MAP =
    Collections.unmodifiableMap(new LinkedHashMap<JanPai, Integer>() {
        {
            put(JanPai.MAN_1, R.id.chi_button_man_1);
            put(JanPai.MAN_2, R.id.chi_button_man_2);
            put(JanPai.MAN_3, R.id.chi_button_man_3);
            put(JanPai.MAN_4, R.id.chi_button_man_4);
            put(JanPai.MAN_5, R.id.chi_button_man_5);
            put(JanPai.MAN_6, R.id.chi_button_man_6);
            put(JanPai.MAN_7, R.id.chi_button_man_7);
            put(JanPai.MAN_8, R.id.chi_button_man_8);
            put(JanPai.MAN_9, R.id.chi_button_man_9);
            put(JanPai.PIN_1, R.id.chi_button_pin_1);
            put(JanPai.PIN_2, R.id.chi_button_pin_2);
            put(JanPai.PIN_3, R.id.chi_button_pin_3);
            put(JanPai.PIN_4, R.id.chi_button_pin_4);
            put(JanPai.PIN_5, R.id.chi_button_pin_5);
            put(JanPai.PIN_6, R.id.chi_button_pin_6);
            put(JanPai.PIN_7, R.id.chi_button_pin_7);
            put(JanPai.PIN_8, R.id.chi_button_pin_8);
            put(JanPai.PIN_9, R.id.chi_button_pin_9);
            put(JanPai.SOU_1, R.id.chi_button_sou_1);
            put(JanPai.SOU_2, R.id.chi_button_sou_2);
            put(JanPai.SOU_3, R.id.chi_button_sou_3);
            put(JanPai.SOU_4, R.id.chi_button_sou_4);
            put(JanPai.SOU_5, R.id.chi_button_sou_5);
            put(JanPai.SOU_6, R.id.chi_button_sou_6);
            put(JanPai.SOU_7, R.id.chi_button_sou_7);
            put(JanPai.SOU_8, R.id.chi_button_sou_8);
            put(JanPai.SOU_9, R.id.chi_button_sou_9);
            put(JanPai.TON,   R.id.chi_button_ton);
            put(JanPai.NAN,   R.id.chi_button_nan);
            put(JanPai.SHA,   R.id.chi_button_sha);
            put(JanPai.PEI,   R.id.chi_button_pei);
            put(JanPai.HAKU,  R.id.chi_button_haku);
            put(JanPai.HATU,  R.id.chi_button_hatu);
            put(JanPai.CHUN,  R.id.chi_button_chun);
        }
        
        private static final long serialVersionUID = 1L;
    });
    
    /**
     * ポン牌選択ボタンIDマップ
     */
    public static final Map<JanPai, Integer> PON_JAN_PAI_BUTTON_ID_MAP =
    Collections.unmodifiableMap(new LinkedHashMap<JanPai, Integer>() {
        {
            put(JanPai.MAN_1, R.id.pon_button_man_1);
            put(JanPai.MAN_2, R.id.pon_button_man_2);
            put(JanPai.MAN_3, R.id.pon_button_man_3);
            put(JanPai.MAN_4, R.id.pon_button_man_4);
            put(JanPai.MAN_5, R.id.pon_button_man_5);
            put(JanPai.MAN_6, R.id.pon_button_man_6);
            put(JanPai.MAN_7, R.id.pon_button_man_7);
            put(JanPai.MAN_8, R.id.pon_button_man_8);
            put(JanPai.MAN_9, R.id.pon_button_man_9);
            put(JanPai.PIN_1, R.id.pon_button_pin_1);
            put(JanPai.PIN_2, R.id.pon_button_pin_2);
            put(JanPai.PIN_3, R.id.pon_button_pin_3);
            put(JanPai.PIN_4, R.id.pon_button_pin_4);
            put(JanPai.PIN_5, R.id.pon_button_pin_5);
            put(JanPai.PIN_6, R.id.pon_button_pin_6);
            put(JanPai.PIN_7, R.id.pon_button_pin_7);
            put(JanPai.PIN_8, R.id.pon_button_pin_8);
            put(JanPai.PIN_9, R.id.pon_button_pin_9);
            put(JanPai.SOU_1, R.id.pon_button_sou_1);
            put(JanPai.SOU_2, R.id.pon_button_sou_2);
            put(JanPai.SOU_3, R.id.pon_button_sou_3);
            put(JanPai.SOU_4, R.id.pon_button_sou_4);
            put(JanPai.SOU_5, R.id.pon_button_sou_5);
            put(JanPai.SOU_6, R.id.pon_button_sou_6);
            put(JanPai.SOU_7, R.id.pon_button_sou_7);
            put(JanPai.SOU_8, R.id.pon_button_sou_8);
            put(JanPai.SOU_9, R.id.pon_button_sou_9);
            put(JanPai.TON,   R.id.pon_button_ton);
            put(JanPai.NAN,   R.id.pon_button_nan);
            put(JanPai.SHA,   R.id.pon_button_sha);
            put(JanPai.PEI,   R.id.pon_button_pei);
            put(JanPai.HAKU,  R.id.pon_button_haku);
            put(JanPai.HATU,  R.id.pon_button_hatu);
            put(JanPai.CHUN,  R.id.pon_button_chun);
        }
        
        private static final long serialVersionUID = 1L;
    });
    
    /**
     * 明カン牌選択ボタンIDマップ
     */
    public static final Map<JanPai, Integer> KAN_LIGHT_JAN_PAI_BUTTON_ID_MAP =
    Collections.unmodifiableMap(new LinkedHashMap<JanPai, Integer>() {
        {
            put(JanPai.MAN_1, R.id.kan_light_button_man_1);
            put(JanPai.MAN_2, R.id.kan_light_button_man_2);
            put(JanPai.MAN_3, R.id.kan_light_button_man_3);
            put(JanPai.MAN_4, R.id.kan_light_button_man_4);
            put(JanPai.MAN_5, R.id.kan_light_button_man_5);
            put(JanPai.MAN_6, R.id.kan_light_button_man_6);
            put(JanPai.MAN_7, R.id.kan_light_button_man_7);
            put(JanPai.MAN_8, R.id.kan_light_button_man_8);
            put(JanPai.MAN_9, R.id.kan_light_button_man_9);
            put(JanPai.PIN_1, R.id.kan_light_button_pin_1);
            put(JanPai.PIN_2, R.id.kan_light_button_pin_2);
            put(JanPai.PIN_3, R.id.kan_light_button_pin_3);
            put(JanPai.PIN_4, R.id.kan_light_button_pin_4);
            put(JanPai.PIN_5, R.id.kan_light_button_pin_5);
            put(JanPai.PIN_6, R.id.kan_light_button_pin_6);
            put(JanPai.PIN_7, R.id.kan_light_button_pin_7);
            put(JanPai.PIN_8, R.id.kan_light_button_pin_8);
            put(JanPai.PIN_9, R.id.kan_light_button_pin_9);
            put(JanPai.SOU_1, R.id.kan_light_button_sou_1);
            put(JanPai.SOU_2, R.id.kan_light_button_sou_2);
            put(JanPai.SOU_3, R.id.kan_light_button_sou_3);
            put(JanPai.SOU_4, R.id.kan_light_button_sou_4);
            put(JanPai.SOU_5, R.id.kan_light_button_sou_5);
            put(JanPai.SOU_6, R.id.kan_light_button_sou_6);
            put(JanPai.SOU_7, R.id.kan_light_button_sou_7);
            put(JanPai.SOU_8, R.id.kan_light_button_sou_8);
            put(JanPai.SOU_9, R.id.kan_light_button_sou_9);
            put(JanPai.TON,   R.id.kan_light_button_ton);
            put(JanPai.NAN,   R.id.kan_light_button_nan);
            put(JanPai.SHA,   R.id.kan_light_button_sha);
            put(JanPai.PEI,   R.id.kan_light_button_pei);
            put(JanPai.HAKU,  R.id.kan_light_button_haku);
            put(JanPai.HATU,  R.id.kan_light_button_hatu);
            put(JanPai.CHUN,  R.id.kan_light_button_chun);
        }
        
        private static final long serialVersionUID = 1L;
    });
    
    /**
     * 暗カン牌選択ボタンIDマップ
     */
    public static final Map<JanPai, Integer> KAN_DARK_JAN_PAI_BUTTON_ID_MAP =
    Collections.unmodifiableMap(new LinkedHashMap<JanPai, Integer>() {
        {
            put(JanPai.MAN_1, R.id.kan_dark_button_man_1);
            put(JanPai.MAN_2, R.id.kan_dark_button_man_2);
            put(JanPai.MAN_3, R.id.kan_dark_button_man_3);
            put(JanPai.MAN_4, R.id.kan_dark_button_man_4);
            put(JanPai.MAN_5, R.id.kan_dark_button_man_5);
            put(JanPai.MAN_6, R.id.kan_dark_button_man_6);
            put(JanPai.MAN_7, R.id.kan_dark_button_man_7);
            put(JanPai.MAN_8, R.id.kan_dark_button_man_8);
            put(JanPai.MAN_9, R.id.kan_dark_button_man_9);
            put(JanPai.PIN_1, R.id.kan_dark_button_pin_1);
            put(JanPai.PIN_2, R.id.kan_dark_button_pin_2);
            put(JanPai.PIN_3, R.id.kan_dark_button_pin_3);
            put(JanPai.PIN_4, R.id.kan_dark_button_pin_4);
            put(JanPai.PIN_5, R.id.kan_dark_button_pin_5);
            put(JanPai.PIN_6, R.id.kan_dark_button_pin_6);
            put(JanPai.PIN_7, R.id.kan_dark_button_pin_7);
            put(JanPai.PIN_8, R.id.kan_dark_button_pin_8);
            put(JanPai.PIN_9, R.id.kan_dark_button_pin_9);
            put(JanPai.SOU_1, R.id.kan_dark_button_sou_1);
            put(JanPai.SOU_2, R.id.kan_dark_button_sou_2);
            put(JanPai.SOU_3, R.id.kan_dark_button_sou_3);
            put(JanPai.SOU_4, R.id.kan_dark_button_sou_4);
            put(JanPai.SOU_5, R.id.kan_dark_button_sou_5);
            put(JanPai.SOU_6, R.id.kan_dark_button_sou_6);
            put(JanPai.SOU_7, R.id.kan_dark_button_sou_7);
            put(JanPai.SOU_8, R.id.kan_dark_button_sou_8);
            put(JanPai.SOU_9, R.id.kan_dark_button_sou_9);
            put(JanPai.TON,   R.id.kan_dark_button_ton);
            put(JanPai.NAN,   R.id.kan_dark_button_nan);
            put(JanPai.SHA,   R.id.kan_dark_button_sha);
            put(JanPai.PEI,   R.id.kan_dark_button_pei);
            put(JanPai.HAKU,  R.id.kan_dark_button_haku);
            put(JanPai.HATU,  R.id.kan_dark_button_hatu);
            put(JanPai.CHUN,  R.id.kan_dark_button_chun);
        }
        
        private static final long serialVersionUID = 1L;
    });
    
}
