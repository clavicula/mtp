/**
 * ImageResourceManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import wiz.project.jan.JanPai;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



/**
 * 画像リソース管理
 */
final class ImageResourceManager {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private ImageResourceManager() {
        initializeResourceID();
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static ImageResourceManager getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * 牌裏画像を取得
     * 
     * @return 牌裏画像。
     */
    public Bitmap getBlankImage() {
        synchronized (_BLANK_RESOURCE_LOCK) {
            return _blankResource;
        }
    }
    
    /**
     * 牌画像を取得
     * 
     * @param pai 取得対象の牌。
     * @return 牌画像。
     */
    public Bitmap getImage(final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Source pai is null.");
        }
        
        if (!_resourceMap.containsKey(pai)) {
            throw new IllegalStateException("Image resource manager is not initialized.");
        }
        return _resourceMap.get(pai);
    }
    
    /**
     * 初期化処理
     * 
     * @param activity メイン画面。
     */
    public void initialize(final Activity activity) {
        if (activity == null) {
            throw new NullPointerException("Source activity is null.");
        }
        
        initializeResourceID();
        
        final Resources core = activity.getResources();
        for (final Map.Entry<JanPai, Integer> entry : _resourceIDMap.entrySet()) {
            final int resourceID = entry.getValue();
            final Bitmap resource = BitmapFactory.decodeResource(core, resourceID);
            _resourceMap.put(entry.getKey(), resource);
        }
        
        synchronized (_BLANK_RESOURCE_LOCK) {
            _blankResource = BitmapFactory.decodeResource(core, R.drawable.ura);
        }
    }
    
    
    
    /**
     * リソースIDマッピングを初期化
     */
    private void initializeResourceID() {
        _resourceIDMap.put(JanPai.MAN_1, R.drawable.m1);
        _resourceIDMap.put(JanPai.MAN_2, R.drawable.m2);
        _resourceIDMap.put(JanPai.MAN_3, R.drawable.m3);
        _resourceIDMap.put(JanPai.MAN_4, R.drawable.m4);
        _resourceIDMap.put(JanPai.MAN_5, R.drawable.m5);
        _resourceIDMap.put(JanPai.MAN_6, R.drawable.m6);
        _resourceIDMap.put(JanPai.MAN_7, R.drawable.m7);
        _resourceIDMap.put(JanPai.MAN_8, R.drawable.m8);
        _resourceIDMap.put(JanPai.MAN_9, R.drawable.m9);
        _resourceIDMap.put(JanPai.PIN_1, R.drawable.p1);
        _resourceIDMap.put(JanPai.PIN_2, R.drawable.p2);
        _resourceIDMap.put(JanPai.PIN_3, R.drawable.p3);
        _resourceIDMap.put(JanPai.PIN_4, R.drawable.p4);
        _resourceIDMap.put(JanPai.PIN_5, R.drawable.p5);
        _resourceIDMap.put(JanPai.PIN_6, R.drawable.p6);
        _resourceIDMap.put(JanPai.PIN_7, R.drawable.p7);
        _resourceIDMap.put(JanPai.PIN_8, R.drawable.p8);
        _resourceIDMap.put(JanPai.PIN_9, R.drawable.p9);
        _resourceIDMap.put(JanPai.SOU_1, R.drawable.s1);
        _resourceIDMap.put(JanPai.SOU_2, R.drawable.s2);
        _resourceIDMap.put(JanPai.SOU_3, R.drawable.s3);
        _resourceIDMap.put(JanPai.SOU_4, R.drawable.s4);
        _resourceIDMap.put(JanPai.SOU_5, R.drawable.s5);
        _resourceIDMap.put(JanPai.SOU_6, R.drawable.s6);
        _resourceIDMap.put(JanPai.SOU_7, R.drawable.s7);
        _resourceIDMap.put(JanPai.SOU_8, R.drawable.s8);
        _resourceIDMap.put(JanPai.SOU_9, R.drawable.s9);
        _resourceIDMap.put(JanPai.TON,   R.drawable.j1);
        _resourceIDMap.put(JanPai.NAN,   R.drawable.j2);
        _resourceIDMap.put(JanPai.SHA,   R.drawable.j3);
        _resourceIDMap.put(JanPai.PEI,   R.drawable.j4);
        _resourceIDMap.put(JanPai.HAKU,  R.drawable.j5);
        _resourceIDMap.put(JanPai.HATU,  R.drawable.j6);
        _resourceIDMap.put(JanPai.CHUN,  R.drawable.j7);
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final ImageResourceManager INSTANCE = new ImageResourceManager();
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _BLANK_RESOURCE_LOCK = new Object();
    
    
    
    /**
     * リソースIDマッピング
     */
    private final Map<JanPai, Integer> _resourceIDMap = new ConcurrentHashMap<JanPai, Integer>();
    
    /**
     * リソースマッピング
     */
    private final Map<JanPai, Bitmap> _resourceMap = new ConcurrentHashMap<JanPai, Bitmap>();
    
    /**
     * 牌裏
     */
    private Bitmap _blankResource = null;
    
}
