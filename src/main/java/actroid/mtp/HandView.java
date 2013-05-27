/**
 * HandView.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import wiz.project.jan.JanPai;
import android.graphics.Bitmap;
import android.widget.ImageButton;



/**
 * 手牌ビュー
 */
final class HandView {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private HandView() {
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static HandView getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * ボタンの牌画像を初期化
     * 
     * @param index ボタンのインデックス。
     */
    public void clearButtonImage(final int index) {
        if (index > 13) {
            throw new IllegalArgumentException("Invalid index - " + index);
        }
        
        final ImageButton button = _handButtonList.get(index);
        final Bitmap image = ImageResourceManager.getInstance().getBlankImage();
        button.setImageBitmap(image);
        _handPaiMap.remove(button.getId());
    }
    
    /**
     * 雀牌を取得
     * 
     * @param handID 手牌ID。
     * @return 対応する雀牌。
     */
    public JanPai getJanPai(final int handID) {
        if (!_handPaiMap.containsKey(handID)) {
            throw new IllegalArgumentException("Invalid hand ID - " + handID);
        }
        return _handPaiMap.get(handID);
    }
    
    /**
     * 雀牌を持っているか
     * 
     * @param handID 手牌ID。
     * @return 判定結果。
     */
    public boolean hasJanPai(final int handID) {
        return _handPaiMap.containsKey(handID);
    }
    
    /**
     * 初期化処理
     * 
     * @param handButtonList ボタン管理オブジェクトのリスト。
     */
    public void initialize(final List<ImageButton> handButtonList) {
        if (handButtonList != null) {
            _handButtonList = handButtonList;
        }
    }
    
    /**
     * ボタンの牌画像を設定
     * 
     * @param index ボタンのインデックス。
     * @param pai 表示する牌。
     */
    public void setButtonImage(final int index, final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Source pai is null.");
        }
        if (index > 13) {
            throw new IllegalArgumentException("Invalid index - " + index);
        }
        
        final ImageButton button = _handButtonList.get(index);
        final Bitmap image = ImageResourceManager.getInstance().getImage(pai);
        button.setImageBitmap(image);
        _handPaiMap.put(button.getId(), pai);
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final HandView INSTANCE = new HandView();
    
    
    
    /**
     * 手牌ボタン
     */
    private List<ImageButton> _handButtonList = new CopyOnWriteArrayList<ImageButton>();
    
    /**
     * 手牌IDと雀牌のMap
     */
    private final Map<Integer, JanPai> _handPaiMap = new ConcurrentHashMap<Integer, JanPai>();
    
}
