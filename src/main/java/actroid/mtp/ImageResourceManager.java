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
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;



/**
 * 画像リソース管理
 */
public final class ImageResourceManager {
    
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
     * 牌裏画像を取得 (オープン)
     * 
     * @return 牌裏画像。
     */
    public Bitmap getOpenBlankImage() {
        synchronized (_BLANK_OPEN_RESOURCE_LOCK) {
            return _blankOpenResource;
        }
    }
    
    /**
     * 牌画像を取得 (オープン)
     * 
     * @param pai 取得対象の牌。
     * @return 牌画像。
     */
    public Bitmap getOpenImage(final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Source pai is null.");
        }
        
        if (!_openMap.containsKey(pai)) {
            throw new IllegalStateException("Image resource manager is not initialized.");
        }
        return _openMap.get(pai);
    }
    
    /**
     * 牌裏画像を取得 (横方向)
     * 
     * @return 牌裏画像。
     */
    public Bitmap getRotateBlankImage() {
        synchronized (_BLANK_ROTATE_RESOURCE_LOCK) {
            return _blankRotateResource;
        }
    }
    
    /**
     * 牌画像を取得 (横方向)
     * 
     * @param pai 取得対象の牌。
     * @return 牌画像。
     */
    public Bitmap getRotateImage(final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Source pai is null.");
        }
        
        if (!_rotateMap.containsKey(pai)) {
            throw new IllegalStateException("Image resource manager is not initialized.");
        }
        return _rotateMap.get(pai);
    }
    
    /**
     * 牌裏画像を取得 (カン表示用積み牌)
     * 
     * @return 牌裏画像。
     */
    public Bitmap getStackBlankImage() {
        synchronized (_BLANK_STACK_RESOURCE_LOCK) {
            return _blankStackResource;
        }
    }
    
    /**
     * 牌画像を取得 (カン表示用積み牌)
     * 
     * @param pai 取得対象の牌。
     * @return 牌画像。
     */
    public Bitmap getStackImage(final JanPai pai) {
        if (pai == null) {
            throw new NullPointerException("Source pai is null.");
        }
        
        if (!_stackMap.containsKey(pai)) {
            throw new IllegalStateException("Image resource manager is not initialized.");
        }
        return _stackMap.get(pai);
    }
    
    /**
     * カン表示用積み牌の高さを持つダミー画像を取得
     * 
     * @return ダミー画像。
     */
    public Bitmap getStackImageHeightDummy() {
        synchronized (_STACK_HEIGHT_DUMMY_LOCK) {
            return _stackHeightDummy;
        }
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
        
        synchronized (_INITIALIZE_LOCK) {
            if (_initialized) {
                return;
            }
            
            initializeResourceID();
            initializeOpenResourceID();
            initializeRotateResourceID();
            initializeStackResourceID();
            
            final Resources core = activity.getResources();
            final double targetWidth = getDisplayShortEdge(activity) / 15.0;
            final double scale = targetWidth / JAN_PAI_WIDTH;
            
            for (final Map.Entry<JanPai, Integer> entry : _resourceIDMap.entrySet()) {
                final int resourceID = entry.getValue();
                final Bitmap resource = readImage(core, resourceID, scale, false);
                _resourceMap.put(entry.getKey(), resource);
            }
            for (final Map.Entry<JanPai, Integer> entry : _openIDMap.entrySet()) {
                final int resourceID = entry.getValue();
                final Bitmap resource = readImage(core, resourceID, scale, false);
                _openMap.put(entry.getKey(), resource);
            }
            for (final Map.Entry<JanPai, Integer> entry : _rotateIDMap.entrySet()) {
                final int resourceID = entry.getValue();
                final Bitmap resource = readImage(core, resourceID, scale, true);
                _rotateMap.put(entry.getKey(), resource);
            }
            for (final Map.Entry<JanPai, Integer> entry : _stackIDMap.entrySet()) {
                final int resourceID = entry.getValue();
                final Bitmap resource = readImage(core, resourceID, scale, true);
                _stackMap.put(entry.getKey(), resource);
            }
            
            synchronized (_BLANK_RESOURCE_LOCK) {
                _blankResource = readImage(core, R.drawable.ura, scale, false);
            }
            synchronized (_BLANK_OPEN_RESOURCE_LOCK) {
                _blankOpenResource = readImage(core, R.drawable.ura_open, scale, false);
            }
            synchronized (_BLANK_ROTATE_RESOURCE_LOCK) {
                _blankRotateResource = readImage(core, R.drawable.ura_yoko, scale, true);
            }
            synchronized (_BLANK_STACK_RESOURCE_LOCK) {
                _blankStackResource = readImage(core, R.drawable.ura_kan, scale, true);
                final int height = _blankStackResource.getHeight();
                _stackHeightDummy =
                    Bitmap.createScaledBitmap(_blankStackResource, 1, height, true);
            }
            
            _initialized = true;
        }
    }
    
    
    
    /**
     * ディスプレイ短辺を取得 [px]
     * 
     * @param activity 画面。
     * @return ディスプレイ短辺。
     */
    private double getDisplayShortEdge(final Activity activity) {
        final WindowManager window =
            (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
        final Display display = window.getDefaultDisplay();
        final double base = Math.min(display.getWidth(), display.getHeight());
        final double margin = activity.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) * 2;
        return base - margin;
    }
    
    /**
     * オープンリソースIDマッピングを初期化
     */
    private void initializeOpenResourceID() {
        _openIDMap.put(JanPai.MAN_1, R.drawable.m1_open);
        _openIDMap.put(JanPai.MAN_2, R.drawable.m2_open);
        _openIDMap.put(JanPai.MAN_3, R.drawable.m3_open);
        _openIDMap.put(JanPai.MAN_4, R.drawable.m4_open);
        _openIDMap.put(JanPai.MAN_5, R.drawable.m5_open);
        _openIDMap.put(JanPai.MAN_6, R.drawable.m6_open);
        _openIDMap.put(JanPai.MAN_7, R.drawable.m7_open);
        _openIDMap.put(JanPai.MAN_8, R.drawable.m8_open);
        _openIDMap.put(JanPai.MAN_9, R.drawable.m9_open);
        _openIDMap.put(JanPai.PIN_1, R.drawable.p1_open);
        _openIDMap.put(JanPai.PIN_2, R.drawable.p2_open);
        _openIDMap.put(JanPai.PIN_3, R.drawable.p3_open);
        _openIDMap.put(JanPai.PIN_4, R.drawable.p4_open);
        _openIDMap.put(JanPai.PIN_5, R.drawable.p5_open);
        _openIDMap.put(JanPai.PIN_6, R.drawable.p6_open);
        _openIDMap.put(JanPai.PIN_7, R.drawable.p7_open);
        _openIDMap.put(JanPai.PIN_8, R.drawable.p8_open);
        _openIDMap.put(JanPai.PIN_9, R.drawable.p9_open);
        _openIDMap.put(JanPai.SOU_1, R.drawable.s1_open);
        _openIDMap.put(JanPai.SOU_2, R.drawable.s2_open);
        _openIDMap.put(JanPai.SOU_3, R.drawable.s3_open);
        _openIDMap.put(JanPai.SOU_4, R.drawable.s4_open);
        _openIDMap.put(JanPai.SOU_5, R.drawable.s5_open);
        _openIDMap.put(JanPai.SOU_6, R.drawable.s6_open);
        _openIDMap.put(JanPai.SOU_7, R.drawable.s7_open);
        _openIDMap.put(JanPai.SOU_8, R.drawable.s8_open);
        _openIDMap.put(JanPai.SOU_9, R.drawable.s9_open);
        _openIDMap.put(JanPai.TON,   R.drawable.j1_open);
        _openIDMap.put(JanPai.NAN,   R.drawable.j2_open);
        _openIDMap.put(JanPai.SHA,   R.drawable.j3_open);
        _openIDMap.put(JanPai.PEI,   R.drawable.j4_open);
        _openIDMap.put(JanPai.HAKU,  R.drawable.j5_open);
        _openIDMap.put(JanPai.HATU,  R.drawable.j6_open);
        _openIDMap.put(JanPai.CHUN,  R.drawable.j7_open);
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
     * 横方向リソースIDマッピングを初期化
     */
    private void initializeRotateResourceID() {
        _rotateIDMap.put(JanPai.MAN_1, R.drawable.m1_yoko);
        _rotateIDMap.put(JanPai.MAN_2, R.drawable.m2_yoko);
        _rotateIDMap.put(JanPai.MAN_3, R.drawable.m3_yoko);
        _rotateIDMap.put(JanPai.MAN_4, R.drawable.m4_yoko);
        _rotateIDMap.put(JanPai.MAN_5, R.drawable.m5_yoko);
        _rotateIDMap.put(JanPai.MAN_6, R.drawable.m6_yoko);
        _rotateIDMap.put(JanPai.MAN_7, R.drawable.m7_yoko);
        _rotateIDMap.put(JanPai.MAN_8, R.drawable.m8_yoko);
        _rotateIDMap.put(JanPai.MAN_9, R.drawable.m9_yoko);
        _rotateIDMap.put(JanPai.PIN_1, R.drawable.p1_yoko);
        _rotateIDMap.put(JanPai.PIN_2, R.drawable.p2_yoko);
        _rotateIDMap.put(JanPai.PIN_3, R.drawable.p3_yoko);
        _rotateIDMap.put(JanPai.PIN_4, R.drawable.p4_yoko);
        _rotateIDMap.put(JanPai.PIN_5, R.drawable.p5_yoko);
        _rotateIDMap.put(JanPai.PIN_6, R.drawable.p6_yoko);
        _rotateIDMap.put(JanPai.PIN_7, R.drawable.p7_yoko);
        _rotateIDMap.put(JanPai.PIN_8, R.drawable.p8_yoko);
        _rotateIDMap.put(JanPai.PIN_9, R.drawable.p9_yoko);
        _rotateIDMap.put(JanPai.SOU_1, R.drawable.s1_yoko);
        _rotateIDMap.put(JanPai.SOU_2, R.drawable.s2_yoko);
        _rotateIDMap.put(JanPai.SOU_3, R.drawable.s3_yoko);
        _rotateIDMap.put(JanPai.SOU_4, R.drawable.s4_yoko);
        _rotateIDMap.put(JanPai.SOU_5, R.drawable.s5_yoko);
        _rotateIDMap.put(JanPai.SOU_6, R.drawable.s6_yoko);
        _rotateIDMap.put(JanPai.SOU_7, R.drawable.s7_yoko);
        _rotateIDMap.put(JanPai.SOU_8, R.drawable.s8_yoko);
        _rotateIDMap.put(JanPai.SOU_9, R.drawable.s9_yoko);
        _rotateIDMap.put(JanPai.TON,   R.drawable.j1_yoko);
        _rotateIDMap.put(JanPai.NAN,   R.drawable.j2_yoko);
        _rotateIDMap.put(JanPai.SHA,   R.drawable.j3_yoko);
        _rotateIDMap.put(JanPai.PEI,   R.drawable.j4_yoko);
        _rotateIDMap.put(JanPai.HAKU,  R.drawable.j5_yoko);
        _rotateIDMap.put(JanPai.HATU,  R.drawable.j6_yoko);
        _rotateIDMap.put(JanPai.CHUN,  R.drawable.j7_yoko);
    }
    
    /**
     * カン表示用積み牌リソースIDマッピングを初期化
     */
    private void initializeStackResourceID() {
        _stackIDMap.put(JanPai.MAN_1, R.drawable.m1_kan);
        _stackIDMap.put(JanPai.MAN_2, R.drawable.m2_kan);
        _stackIDMap.put(JanPai.MAN_3, R.drawable.m3_kan);
        _stackIDMap.put(JanPai.MAN_4, R.drawable.m4_kan);
        _stackIDMap.put(JanPai.MAN_5, R.drawable.m5_kan);
        _stackIDMap.put(JanPai.MAN_6, R.drawable.m6_kan);
        _stackIDMap.put(JanPai.MAN_7, R.drawable.m7_kan);
        _stackIDMap.put(JanPai.MAN_8, R.drawable.m8_kan);
        _stackIDMap.put(JanPai.MAN_9, R.drawable.m9_kan);
        _stackIDMap.put(JanPai.PIN_1, R.drawable.p1_kan);
        _stackIDMap.put(JanPai.PIN_2, R.drawable.p2_kan);
        _stackIDMap.put(JanPai.PIN_3, R.drawable.p3_kan);
        _stackIDMap.put(JanPai.PIN_4, R.drawable.p4_kan);
        _stackIDMap.put(JanPai.PIN_5, R.drawable.p5_kan);
        _stackIDMap.put(JanPai.PIN_6, R.drawable.p6_kan);
        _stackIDMap.put(JanPai.PIN_7, R.drawable.p7_kan);
        _stackIDMap.put(JanPai.PIN_8, R.drawable.p8_kan);
        _stackIDMap.put(JanPai.PIN_9, R.drawable.p9_kan);
        _stackIDMap.put(JanPai.SOU_1, R.drawable.s1_kan);
        _stackIDMap.put(JanPai.SOU_2, R.drawable.s2_kan);
        _stackIDMap.put(JanPai.SOU_3, R.drawable.s3_kan);
        _stackIDMap.put(JanPai.SOU_4, R.drawable.s4_kan);
        _stackIDMap.put(JanPai.SOU_5, R.drawable.s5_kan);
        _stackIDMap.put(JanPai.SOU_6, R.drawable.s6_kan);
        _stackIDMap.put(JanPai.SOU_7, R.drawable.s7_kan);
        _stackIDMap.put(JanPai.SOU_8, R.drawable.s8_kan);
        _stackIDMap.put(JanPai.SOU_9, R.drawable.s9_kan);
        _stackIDMap.put(JanPai.TON,   R.drawable.j1_kan);
        _stackIDMap.put(JanPai.NAN,   R.drawable.j2_kan);
        _stackIDMap.put(JanPai.SHA,   R.drawable.j3_kan);
        _stackIDMap.put(JanPai.PEI,   R.drawable.j4_kan);
        _stackIDMap.put(JanPai.HAKU,  R.drawable.j5_kan);
        _stackIDMap.put(JanPai.HATU,  R.drawable.j6_kan);
        _stackIDMap.put(JanPai.CHUN,  R.drawable.j7_kan);
    }
    
    /**
     * 画像を読み込む
     * 
     * @param core リソースコア。
     * @param resourceID リソースID。
     * @param scale サイズ変更倍率。
     * @param rotated 横回転しているか。
     * @return 画像オブジェクト。
     */
    private Bitmap readImage(final Resources core,
                              final int resourceID,
                              final double scale,
                              final boolean rotated) {
        final Bitmap image = BitmapFactory.decodeResource(core, resourceID);
        final boolean bigger = Double.compare(scale, 1.0) >= 0;
        double trimmedScale = bigger ? 1.0 : scale;
        if (rotated) {
            trimmedScale *= 0.925;
        }
        else {
            if (bigger) {
                // 回転無し＆縮小不要
                return image;
            }
        }
        
        // inSampleSizeで縮小するとブレが大きく、目的のサイズよりかなり小さくなるので採用しない
        final double targetWidth = image.getWidth() * trimmedScale;
        final double targetHeight = image.getHeight() * trimmedScale;
        final Bitmap result = Bitmap.createScaledBitmap(image, (int)targetWidth, (int)targetHeight, true);
        image.recycle();
        return result;
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final ImageResourceManager INSTANCE = new ImageResourceManager();
    
    /**
     * 雀牌の横幅 [px]
     */
    private static final int JAN_PAI_WIDTH = 48;
    
    
    
    /**
     * ロックオブジェクト (初期化)
     */
    private final Object _INITIALIZE_LOCK = new Object();
    
    /**
     * ロックオブジェクト (牌裏)
     */
    private final Object _BLANK_RESOURCE_LOCK = new Object();
    
    /**
     * ロックオブジェクト (牌裏 - オープン)
     */
    private final Object _BLANK_OPEN_RESOURCE_LOCK = new Object();
    
    /**
     * ロックオブジェクト (牌裏 - 横方向)
     */
    private final Object _BLANK_ROTATE_RESOURCE_LOCK = new Object();
    
    /**
     * ロックオブジェクト (牌裏 - カン表示用積み牌)
     */
    private final Object _BLANK_STACK_RESOURCE_LOCK = new Object();
    
    /**
     * ロックオブジェクト (ダミー - カン表示用積み牌の高さ)
     */
    private final Object _STACK_HEIGHT_DUMMY_LOCK = new Object();
    
    
    
    /**
     * 初期化済みフラグ
     */
    private volatile boolean _initialized = false;
    
    /**
     * リソースIDマッピング
     */
    private final Map<JanPai, Integer> _resourceIDMap = new ConcurrentHashMap<JanPai, Integer>();
    
    /**
     * リソースマッピング
     */
    private final Map<JanPai, Bitmap> _resourceMap = new ConcurrentHashMap<JanPai, Bitmap>();
    
    /**
     * リソースIDマッピング (オープン)
     */
    private final Map<JanPai, Integer> _openIDMap = new ConcurrentHashMap<JanPai, Integer>();
    
    /**
     * リソースマッピング (オープン)
     */
    private final Map<JanPai, Bitmap> _openMap = new ConcurrentHashMap<JanPai, Bitmap>();
    
    /**
     * リソースIDマッピング (横方向)
     */
    private final Map<JanPai, Integer> _rotateIDMap = new ConcurrentHashMap<JanPai, Integer>();
    
    /**
     * リソースマッピング (横方向)
     */
    private final Map<JanPai, Bitmap> _rotateMap = new ConcurrentHashMap<JanPai, Bitmap>();
    
    /**
     * リソースIDマッピング (カン表示用積み牌)
     */
    private final Map<JanPai, Integer> _stackIDMap = new ConcurrentHashMap<JanPai, Integer>();
    
    /**
     * リソースマッピング (カン表示用積み牌)
     */
    private final Map<JanPai, Bitmap> _stackMap = new ConcurrentHashMap<JanPai, Bitmap>();
    
    /**
     * 牌裏
     */
    private Bitmap _blankResource = null;
    
    /**
     * 牌裏 (オープン)
     */
    private Bitmap _blankOpenResource = null;
    
    /**
     * 牌裏 (横方向)
     */
    private Bitmap _blankRotateResource = null;
    
    /**
     * 牌裏 (カン表示用積み牌)
     */
    private Bitmap _blankStackResource = null;
    
    /**
     * ダミー (カン表示用積み牌の高さ)
     */
    private Bitmap _stackHeightDummy = null;
    
}
