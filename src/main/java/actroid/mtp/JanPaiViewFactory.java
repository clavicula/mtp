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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;



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
    public ImageView createBlankJanPaiView(final int viewID, final RelativeLayout.LayoutParams layoutParam) {
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
    public ImageView createInvisibleJanPaiView(final RelativeLayout.LayoutParams layoutParam) {
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
    public LinearLayout createInvisibleMenTsuView() {
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
    public LinearLayout createInvisibleMenTsuView(final RelativeLayout.LayoutParams layoutParam) {
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
    public ImageView createJanPaiView(final int viewID, final Bitmap image, final RelativeLayout.LayoutParams layoutParam) {
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
     * 面子ビューの最低限の高さを持つダミービューを取得
     * 
     * @param viewID ダミービューのID。
     * @param layoutParam レイアウトパラメータ。
     * @return 面子ビューの最低限の高さを持つダミービュー。
     */
    public LinearLayout createMenTsuMinHeightDummyView(final int viewID, final RelativeLayout.LayoutParams layoutParam) {
        if (layoutParam == null) {
            throw new NullPointerException("Layout parameter is null.");
        }
        
        final Bitmap image = ImageResourceManager.getInstance().getStackImageHeightDummy();
        synchronized (_PARENT_LOCK) {
            final ImageView view = createImageView(image);
            view.setVisibility(View.INVISIBLE);
            
            final LinearLayout layout = new LinearLayout(_parent);
            layout.setId(viewID);
            layout.setLayoutParams(layoutParam);
            layout.addView(view);
            return layout;
        }
    }
    
    /**
     * 面子ビューを生成
     * 
     * @param menTsu 面子。
     * @param menTsuID 面子ID。
     * @return 面子ビュー。
     */
    public LinearLayout createMenTsuView(final MenTsu menTsu, final int menTsuID) {
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
    public LinearLayout createMenTsuView(final MenTsu menTsu, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
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
     * 
     * @param source 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子構成牌ビュー。
     */
    private LinearLayout createChiView(final List<JanPai> source, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
        final List<ImageView> resultList = new ArrayList<ImageView>();
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
            final ImageView view = createImageView(image);
            resultList.add(view);
        }
        return createMenTsuLayout(resultList, menTsuID, layoutParam);
    }
    
    /**
     * イメージビューを生成
     * 
     * @param image 元画像。
     * @return イメージビュー。
     */
    private ImageView createImageView(final Bitmap image) {
        return createImageView(image, null);
    }
    
    /**
     * イメージビューを生成
     * 
     * @param image 元画像。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return イメージビュー。
     */
    private ImageView createImageView(final Bitmap image, final RelativeLayout.LayoutParams layoutParam) {
        final ImageView view = new ImageView(_parent);
        if (layoutParam != null) {
            view.setLayoutParams(layoutParam);
        }
        else {
            final LinearLayout.LayoutParams param =
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(param);
        }
        
        view.setImageBitmap(image);
        view.setAdjustViewBounds(true);  // 縦横比を維持
        return view;
    }
    
    /**
     * 不可視牌ビュー生成の中核処理
     * 
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 不可視牌ビュー。
     */
    private ImageView createInvisibleJanPaiViewCore(final RelativeLayout.LayoutParams layoutParam) {
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
    private LinearLayout createInvisibleMenTsuViewCore(final RelativeLayout.LayoutParams layoutParam) {
        final List<ImageView> resultList = new ArrayList<ImageView>();
        final Bitmap paiImage = ImageResourceManager.getInstance().getOpenBlankImage();
        final Bitmap paiStackImage = ImageResourceManager.getInstance().getStackBlankImage();
        resultList.add(createImageView(paiImage));
        resultList.add(createImageView(paiStackImage));
        resultList.add(createImageView(paiImage));
        
        for (final ImageView view : resultList) {
            view.setVisibility(View.INVISIBLE);
        }
        return createMenTsuLayout(resultList, layoutParam);
    }
    
    /**
     * 牌ビュー生成の中核処理
     * 
     * @param viewID ビューID。
     * @param image 手牌画像。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子構成牌ビュー。
     */
    private ImageView createJanPaiViewCore(final int viewID, final Bitmap image, final RelativeLayout.LayoutParams layoutParam) {
        final ImageView view = createImageView(image, layoutParam);
        view.setId(viewID);
        return view;
    }
    
    /**
     * 面子ビューを生成 (暗カン)
     * 
     * @param source 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子構成牌ビュー。
     */
    private LinearLayout createKanDarkView(final List<JanPai> source, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
        final List<ImageView> resultList = new ArrayList<ImageView>();
        final JanPai pai = source.get(0);
        final Bitmap blankImage = ImageResourceManager.getInstance().getOpenBlankImage();
        final Bitmap paiStackImage = ImageResourceManager.getInstance().getStackImage(pai);
        resultList.add(createImageView(blankImage));
        resultList.add(createImageView(paiStackImage));
        resultList.add(createImageView(blankImage));
        return createMenTsuLayout(resultList, menTsuID, layoutParam);
    }
    
    /**
     * 面子ビューを生成 (明カン)
     * 
     * @param source 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子構成牌ビュー。
     */
    private LinearLayout createKanLightView(final List<JanPai> source, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
        final List<ImageView> resultList = new ArrayList<ImageView>();
        final JanPai pai = source.get(0);
        final Bitmap paiImage = ImageResourceManager.getInstance().getOpenImage(pai);
        final Bitmap paiStackImage = ImageResourceManager.getInstance().getStackImage(pai);
        resultList.add(createImageView(paiImage));
        resultList.add(createImageView(paiStackImage));
        resultList.add(createImageView(paiImage));
        return createMenTsuLayout(resultList, menTsuID, layoutParam);
    }
    
    /**
     * 面子レイアウトを生成
     * 
     * @param viewList 牌ビューのリスト。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     */
    private LinearLayout createMenTsuLayout(final List<ImageView> viewList, final RelativeLayout.LayoutParams layoutParam) {
        final LinearLayout layout = new LinearLayout(_parent);
        if (layoutParam != null) {
            layout.setLayoutParams(layoutParam);
        }
        layout.setGravity(Gravity.BOTTOM);
        
        for (final ImageView view : viewList) {
            layout.addView(view);
        }
        return layout;
    }
    
    /**
     * 面子レイアウトを生成
     * 
     * @param viewList 牌ビューのリスト。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     */
    private LinearLayout createMenTsuLayout(final List<ImageView> viewList, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
        final LinearLayout layout = createMenTsuLayout(viewList, layoutParam);
        layout.setId(menTsuID);
        layout.setGravity(Gravity.BOTTOM);
        return layout;
    }
    
    /**
     * 面子ビュー生成の中核処理
     * 
     * @param menTsu 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子ビュー。
     */
    private LinearLayout createMenTsuViewCore(final MenTsu menTsu, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
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
     * 
     * @param source 面子。
     * @param menTsuID 面子ID。
     * @param layoutParam レイアウトパラメータ。nullを許可する。
     * @return 面子構成牌ビュー。
     */
    private LinearLayout createPonView(final List<JanPai> source, final int menTsuID, final RelativeLayout.LayoutParams layoutParam) {
        final List<ImageView> resultList = new ArrayList<ImageView>();
        final JanPai pai = source.get(0);
        final Bitmap paiImage = ImageResourceManager.getInstance().getOpenImage(pai);
        final Bitmap paiRotateImage = ImageResourceManager.getInstance().getRotateImage(pai);
        resultList.add(createImageView(paiRotateImage));
        resultList.add(createImageView(paiImage));
        resultList.add(createImageView(paiImage));
        return createMenTsuLayout(resultList, menTsuID, layoutParam);
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

