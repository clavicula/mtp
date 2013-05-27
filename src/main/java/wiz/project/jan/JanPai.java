/**
 * JanPai.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;



/**
 * 雀牌
 */
public enum JanPai {
    // 萬子
    MAN_1,
    MAN_2,
    MAN_3,
    MAN_4,
    MAN_5,
    MAN_6,
    MAN_7,
    MAN_8,
    MAN_9,
    
    // 筒子
    PIN_1,
    PIN_2,
    PIN_3,
    PIN_4,
    PIN_5,
    PIN_6,
    PIN_7,
    PIN_8,
    PIN_9,
    
    // 索子
    SOU_1,
    SOU_2,
    SOU_3,
    SOU_4,
    SOU_5,
    SOU_6,
    SOU_7,
    SOU_8,
    SOU_9,
    
    // 風牌
    TON,
    NAN,
    SHA,
    PEI,
    
    // 三元牌
    HAKU,
    HATU,
    CHUN;
    
    
    
    /**
     * 次の牌を取得
     * 
     * @return 次の牌。存在しなければnullを返す。
     */
    public JanPai getNext() {
        switch (this) {
        case MAN_1:
            return MAN_2;
        case MAN_2:
            return MAN_3;
        case MAN_3:
            return MAN_4;
        case MAN_4:
            return MAN_5;
        case MAN_5:
            return MAN_6;
        case MAN_6:
            return MAN_7;
        case MAN_7:
            return MAN_8;
        case MAN_8:
            return MAN_9;
        case PIN_1:
            return PIN_2;
        case PIN_2:
            return PIN_3;
        case PIN_3:
            return PIN_4;
        case PIN_4:
            return PIN_5;
        case PIN_5:
            return PIN_6;
        case PIN_6:
            return PIN_7;
        case PIN_7:
            return PIN_8;
        case PIN_8:
            return PIN_9;
        case SOU_1:
            return SOU_2;
        case SOU_2:
            return SOU_3;
        case SOU_3:
            return SOU_4;
        case SOU_4:
            return SOU_5;
        case SOU_5:
            return SOU_6;
        case SOU_6:
            return SOU_7;
        case SOU_7:
            return SOU_8;
        case SOU_8:
            return SOU_9;
        default:
            return null;
        }
    }
    
    /**
     * ヤオ九牌か
     * 
     * @return 判定結果。
     */
    public boolean isYao() {
        switch (this) {
        case MAN_1:
        case MAN_9:
        case PIN_1:
        case PIN_9:
        case SOU_1:
        case SOU_9:
        case TON:
        case NAN:
        case SHA:
        case PEI:
        case HAKU:
        case HATU:
        case CHUN:
            return true;
        default:
            return false;
        }
    }
    
    /**
     * 文字列に変換
     * 
     * @return 変換結果。
     */
    @Override
    public String toString() {
        switch (this) {
        case MAN_1:
            return "[1m]";
        case MAN_2:
            return "[2m]";
        case MAN_3:
            return "[3m]";
        case MAN_4:
            return "[4m]";
        case MAN_5:
            return "[5m]";
        case MAN_6:
            return "[6m]";
        case MAN_7:
            return "[7m]";
        case MAN_8:
            return "[8m]";
        case MAN_9:
            return "[9m]";
        case PIN_1:
            return "[1p]";
        case PIN_2:
            return "[2p]";
        case PIN_3:
            return "[3p]";
        case PIN_4:
            return "[4p]";
        case PIN_5:
            return "[5p]";
        case PIN_6:
            return "[6p]";
        case PIN_7:
            return "[7p]";
        case PIN_8:
            return "[8p]";
        case PIN_9:
            return "[9p]";
        case SOU_1:
            return "[1s]";
        case SOU_2:
            return "[2s]";
        case SOU_3:
            return "[3s]";
        case SOU_4:
            return "[4s]";
        case SOU_5:
            return "[5s]";
        case SOU_6:
            return "[6s]";
        case SOU_7:
            return "[7s]";
        case SOU_8:
            return "[8s]";
        case SOU_9:
            return "[9s]";
        case TON:
            return "[東]";
        case NAN:
            return "[南]";
        case SHA:
            return "[西]";
        case PEI:
            return "[北]";
        case HAKU:
            return "[白]";
        case HATU:
            return "[發]";
        case CHUN:
            return "[中]";
        default:
            throw new InternalError();
        }
    }
    
}

