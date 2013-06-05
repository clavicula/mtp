/**
 * JanPaiViewFactory.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.ArrayList;
import java.util.List;

import wiz.project.jan.JanPai;
import wiz.project.jan.MenTsu;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;



/**
 * ビュー生成
 */
public class JanPaiViewFactory {
    
    /**
     * コンストラクタ
     * 
     * @param activity 親画面。
     */
    public JanPaiViewFactory(final Activity activity) {
        if (activity == null) {
            throw new NullPointerException("Parent activity is null.");
        }
        _parent = activity;
    }
    
    
    
    /**
     * 牌裏ビューを生成
     * 
     * @param viewID ビューID。
     * @return 面子構成牌ビュー。
     */
    public ImageView createBlankJanPaiView(final int viewID) {
        final Bitmap image = ImageResourceManager.getInstance().getBlankImage();
        synchronized (_PARENT_LOCK) {
            return createJanPaiViewCore(viewID, image, null);
        }
    }
    
    /**
     * 牌裏ビューを生成
     * 
     * @param viewID ビューID。
     * @param layoutParam レイアウトパラメータ。
     * @return 面子構成牌ビュー。
     */
    public ImageView createBlankJanPaiView(final int viewID, final LinearLayout.LayoutParams layoutParam) {
        if (layoutParam == null) {
            throw new NullPointerException("Layout parameter is null.");
        }
        
        final Bitmap image = ImageResourceManager.getInstance().getBlankImage();
        synchronized (_PARENT_LOCK) {
            return createJanPaiViewCore(viewID, image, layoutParam);
        }
    }
    
    /**
     * 不可視牌ビューを生成
     * 
     * @return 不可視牌ビュー。
     */
    public ImageView createInvisibleJanPaiView() {
        synchronized (_PARENT_LOCK) {
            return createInvisibleJanPaiViewCore(null);
        }
    }
    
    /**
     * 不可視牌ビューを生成
     * 
     * @param layoutParam レイアウトパラメータ。
     * @return 不可視牌ビュー。
     */
    public ImageView createInvisibleJanPaiView(final LinearLayout.LayoutParams layoutParam) {
        if (layoutParam == null) {
            throw new NullPointerException("Layout parameter is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            return createInvisibleJanPaiViewCore(layoutParam);
        }
    }
    
    /**
     * 不可視面子ビューを生成
     * 
     * @return 不可視面子ビュー。
     */
    public List<ImageView> createInvisibleMenTsuView() {
        synchronized (_PARENT_LOCK) {
            return createInvisibleMenTsuViewCore(null);
        }
    }
    
    /**
     * 不可視面子ビューを生成
     * 
     * @param layoutParam レイアウトパラメータ。
     * @return 不可視面子ビュー。
     */
    public List<ImageView> createInvisibleMenTsuView(final LinearLayout.LayoutParams layoutParam) {
        if (layoutParam == null) {
            throw new NullPointerException("Layout parameter is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            return createInvisibleMenTsuViewCore(layoutParam);
        }
    }
    
    /**
     * 牌ビューを生成
     * 
     * @param viewID ビューID。
     * @param image 手牌画像。
     * @return 面子構成牌ビュー。
     */
    public ImageView createJanPaiView(final int viewID, final Bitmap image) {
        if (image == null) {
            throw new NullPointerException("Source image is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            return createJanPaiViewCore(viewID, image, null);
        }
    }
    
    /**
     * 牌ビューを生成
     * 
     * @param viewID ビューID。
     * @param image 手牌画像。
     * @param layoutParam レイアウトパラメータ。
     * @return 面子構成牌ビュー。
     */
    public ImageView createJanPaiView(final int viewID, final Bitmap image, final LinearLayout.LayoutParams layoutParam) {
        if (image == null) {
            throw new NullPointerException("Source image is null.");
        }
        if (layoutParam == null) {
            throw new NullPointerException("Layout parameter is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            return createJanPaiViewCore(viewID, image, layoutParam);
        }
    }
    
    /**
     * 面子ビューを生成
     * 
     * @param menTsu 面子。
     * @param menTsuID 面子ID。
     * @return 面子ビュー。
     */
    public List<ImageView> createMenTsuView(final MenTsu menTsu, final int menTsuID) {
        if (menTsu == null) {
            throw new NullPointerException("Source men tsu is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            return createMenTsuViewCore(menTsu, menTsuID, null);
        }
    }
    
    /**
     * 面子ビューを生成
     * 
     * @param menTsu 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。
     * @return 面子ビュー。
     */
    public List<ImageView> createMenTsuView(final MenTsu menTsu, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        if (menTsu == null) {
            throw new NullPointerException("Source men tsu is null.");
        }
        if (layoutParam == null) {
            throw new NullPointerException("Layout parameter is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            return createMenTsuViewCore(menTsu, menTsuID, layoutParam);
        }
    }
    
    
    
    /**
     * 面子ビューを生成 (チー)
     */
    private List<ImageView> createChiView(final List<JanPai> source, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> result = new ArrayList<ImageView>();
        final int sourceSize = source.size();
        for (int i = 0; i < sourceSize; i++) {
            final JanPai pai = source.get(i);
            Bitmap image;
            switch (i) {
            case 0:
                image = ImageResourceManager.getInstance().getRotateImage(pai);
                break;
            default:
                image = ImageResourceManager.getInstance().getOpenImage(pai);
                break;
            }
            final ImageView view = createJanPaiView(menTsuID, image, layoutParam);
            result.add(view);
        }
        return result;
    }
    
    /**
     * イメージビューを生成
     * 
     * @param image 元画像。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return イメージビュー。
     */
    private ImageView createImageView(final Bitmap image, final LinearLayout.LayoutParams layoutParam) {
        final ImageView view = new ImageView(_parent);
        if (layoutParam != null) {
            view.setLayoutParams(layoutParam);
        }
        view.setImageBitmap(image);
        view.setAdjustViewBounds(true);  // 縦横比を維持
        view.setScaleType(ScaleType.FIT_END);
        return view;
    }
    
    /**
     * 不可視牌ビュー生成の中核処理
     * 
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 不可視牌ビュー。
     */
    private ImageView createInvisibleJanPaiViewCore(final LinearLayout.LayoutParams layoutParam) {
        final Bitmap image = ImageResourceManager.getInstance().getBlankImage();
        final ImageView view = createImageView(image, layoutParam);
        view.setVisibility(View.INVISIBLE);
        return view;
    }
    
    /**
     * 不可視面子ビュー生成の中核処理
     * 
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 不可視面子ビュー。
     */
    private List<ImageView> createInvisibleMenTsuViewCore(final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> result = new ArrayList<ImageView>();
        final Bitmap paiImage = ImageResourceManager.getInstance().getOpenBlankImage();
        final Bitmap paiStackImage = ImageResourceManager.getInstance().getStackBlankImage();
        result.add(createImageView(paiImage, layoutParam));
        result.add(createImageView(paiStackImage, layoutParam));
        result.add(createImageView(paiImage, layoutParam));
        
        for (final ImageView view : result) {
            view.setVisibility(View.INVISIBLE);
        }
        return result;
    }
    
    /**
     * 牌ビュー生成の中核処理
     * 
     * @param viewID ビューID。
     * @param image 手牌画像。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子構成牌ビュー。
     */
    private ImageView createJanPaiViewCore(final int viewID, final Bitmap image, final LinearLayout.LayoutParams layoutParam) {
        final ImageView view = createImageView(image, layoutParam);
        view.setId(viewID);
        return view;
    }
    
    /**
     * 面子ビューを生成 (暗カン)
     */
    private List<ImageView> createKanDarkView(final List<JanPai> source, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> result = new ArrayList<ImageView>();
        final JanPai pai = source.get(0);
        final Bitmap blankImage = ImageResourceManager.getInstance().getOpenBlankImage();
        final Bitmap paiStackImage = ImageResourceManager.getInstance().getStackImage(pai);
        result.add(createJanPaiViewCore(menTsuID, blankImage, layoutParam));
        result.add(createJanPaiView(menTsuID, paiStackImage, layoutParam));
        result.add(createJanPaiViewCore(menTsuID, blankImage, layoutParam));
        return result;
    }
    
    /**
     * 面子ビューを生成 (明カン)
     */
    private List<ImageView> createKanLightView(final List<JanPai> source, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> result = new ArrayList<ImageView>();
        final JanPai pai = source.get(0);
        final Bitmap paiImage = ImageResourceManager.getInstance().getOpenImage(pai);
        final Bitmap paiStackImage = ImageResourceManager.getInstance().getStackImage(pai);
        result.add(createJanPaiView(menTsuID, paiImage, layoutParam));
        result.add(createJanPaiView(menTsuID, paiStackImage, layoutParam));
        result.add(createJanPaiView(menTsuID, paiImage, layoutParam));
        return result;
    }
    
    /**
     * 面子ビュー生成の中核処理
     * 
     * @param menTsu 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子ビュー。
     */
    private List<ImageView> createMenTsuViewCore(final MenTsu menTsu, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        final List<JanPai> source = menTsu.getSource();
        switch (menTsu.getMenTsuType()) {
        case CHI:
            return createChiView(source, menTsuID, layoutParam);
        case PON:
            return createPonView(source, menTsuID, layoutParam);
        case KAN_LIGHT:
            return createKanLightView(source, menTsuID, layoutParam);
        case KAN_DARK:
            return createKanDarkView(source, menTsuID, layoutParam);
        default:
            throw new UnsupportedOperationException("Unsupported - Men tsu is not fixed.");
        }
    }
    
    /**
     * 面子ビューを生成 (ポン)
     */
    private List<ImageView> createPonView(final List<JanPai> source, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> result = new ArrayList<ImageView>();
        final JanPai pai = source.get(0);
        final Bitmap paiImage = ImageResourceManager.getInstance().getOpenImage(pai);
        final Bitmap paiRotateImage = ImageResourceManager.getInstance().getRotateImage(pai);
        result.add(createJanPaiView(menTsuID, paiRotateImage, layoutParam));
        result.add(createJanPaiView(menTsuID, paiImage, layoutParam));
        result.add(createJanPaiView(menTsuID, paiImage, layoutParam));
        return result;
    }
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _PARENT_LOCK = new Object();
    
    
    
    /**
     * 親画面
     */
    private final Activity _parent;
    
}

