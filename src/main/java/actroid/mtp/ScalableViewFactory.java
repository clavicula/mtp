/**
 * ScalableViewFactory.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.List;

import wiz.project.jan.MenTsu;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.LinearLayout;



/**
 * サイズ変更可能なビュー生成
 */
public class ScalableViewFactory extends JanPaiViewFactory {
    
    /**
     * コンストラクタ
     * 
     * @param activity 親画面。
     * @param scale 倍率。
     */
    public ScalableViewFactory(final Activity activity, final double scale) {
        super(activity);
        _scale = (float)scale;
    }
    
    
    
    /**
     * 牌裏ビューを生成
     */
    @Override
    public ImageView createBlankJanPaiView(final int viewID) {
        final ImageView view = super.createBlankJanPaiView(viewID);
        synchronized (_SCALE_LOCK) {
            convertSize(view, _scale);
            return view;
        }
    }
    
    /**
     * 牌裏ビューを生成
     */
    @Override
    public ImageView createBlankJanPaiView(final int viewID, final LinearLayout.LayoutParams layoutParam) {
        final ImageView view = super.createBlankJanPaiView(viewID, layoutParam);
        synchronized (_SCALE_LOCK) {
            convertSize(view, _scale);
            return view;
        }
    }
    
    /**
     * 不可視牌ビューを生成
     */
    @Override
    public ImageView createInvisibleJanPaiView() {
        final ImageView view = super.createInvisibleJanPaiView();
        synchronized (_SCALE_LOCK) {
            convertSize(view, _scale);
            return view;
        }
    }
    
    /**
     * 不可視牌ビューを生成
     */
    @Override
    public ImageView createInvisibleJanPaiView(final LinearLayout.LayoutParams layoutParam) {
        final ImageView view = super.createInvisibleJanPaiView(layoutParam);
        synchronized (_SCALE_LOCK) {
            convertSize(view, _scale);
            return view;
        }
    }
    
    /**
     * 不可視面子ビューを生成
     */
    @Override
    public List<ImageView> createInvisibleMenTsuView() {
        final List<ImageView> viewList = super.createInvisibleMenTsuView();
        synchronized (_SCALE_LOCK) {
            for (final ImageView view : viewList) {
                convertSize(view, _scale);
            }
        }
        return viewList;
    }
    
    /**
     * 不可視面子ビューを生成
     */
    @Override
    public List<ImageView> createInvisibleMenTsuView(final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> viewList = super.createInvisibleMenTsuView(layoutParam);
        synchronized (_SCALE_LOCK) {
            for (final ImageView view : viewList) {
                convertSize(view, _scale);
            }
        }
        return viewList;
    }
    
    /**
     * 牌ビューを生成
     */
    @Override
    public ImageView createJanPaiView(final int viewID, final Bitmap image) {
        final ImageView view = super.createJanPaiView(viewID, image);
        synchronized (_SCALE_LOCK) {
            convertSize(view, _scale);
            return view;
        }
    }
    
    
    /**
     * 牌ビューを生成
     */
    @Override
    public ImageView createJanPaiView(final int viewID, final Bitmap image, final LinearLayout.LayoutParams layoutParam) {
        final ImageView view = super.createJanPaiView(viewID, image, layoutParam);
        synchronized (_SCALE_LOCK) {
            convertSize(view, _scale);
            return view;
        }
    }
    
    /**
     * 面子ビューを生成
     */
    @Override
    public List<ImageView> createMenTsuView(final MenTsu menTsu, final int menTsuID) {
        final List<ImageView> viewList = super.createMenTsuView(menTsu, menTsuID);
        synchronized (_SCALE_LOCK) {
            for (final ImageView view : viewList) {
                convertSize(view, _scale);
            }
        }
        return viewList;
    }
    
    /**
     * 面子ビューを生成
     */
    @Override
    public List<ImageView> createMenTsuView(final MenTsu menTsu, final int menTsuID, final LinearLayout.LayoutParams layoutParam) {
        final List<ImageView> viewList = super.createMenTsuView(menTsu, menTsuID, layoutParam);
        synchronized (_SCALE_LOCK) {
        for (final ImageView view : viewList) {
                convertSize(view, _scale);
            }
        }
        return viewList;
    }
    
    
    
    /**
     * サイズ変更
     * 
     * @param target 変更対象。
     */
    private void convertSize(final ImageView target, final double scale) {
        final float trimmedScale = (float)scale;
        final Matrix matrix = new Matrix();
        matrix.setScale(trimmedScale, trimmedScale);
        target.setImageMatrix(matrix);
    }
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _SCALE_LOCK = new Object();
    
    
    
    /**
     * サイズ変更倍率
     */
    private final double _scale;
    
}

