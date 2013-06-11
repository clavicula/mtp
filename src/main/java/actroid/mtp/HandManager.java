/**
 * HandManager.java
 * 
 * @Author
 *   Yuki Kawata
 */

package actroid.mtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import wiz.project.jan.Hand;
import wiz.project.jan.JanPai;
import wiz.project.jan.MenTsu;



/**
 * 手牌管理
 */
public final class HandManager extends Observable {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private HandManager() {
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static HandManager getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * 確定面子を追加
     * 
     * @param menTsu 確定面子。
     */
    public void addFixedMenTsu(final MenTsu menTsu) {
        synchronized (_HAND_LOCK) {
            _hand.addFixedMenTsu(menTsu);
        }
    }
    
    /**
     * 指定牌を追加
     * 
     * @param pai 追加対象。
     */
    public void addJanPai(final JanPai pai) {
        synchronized (_HAND_LOCK) {
            _hand.addJanPai(pai);
        }
    }
    
    /**
     * 確定面子を全消去
     */
    public void clearFixedMenTzu() {
        synchronized (_HAND_LOCK) {
            _hand.clearFixedMenTsuList();
        }
    }
    
    /**
     * 面前手牌を全消去
     */
    public void clearMenZenHand() {
        synchronized (_HAND_LOCK) {
            _hand.clearMenZenHand();
        }
    }
    
    /**
     * 全ての牌マップを取得
     * 
     * @return 全ての牌マップ。
     */
    public Map<JanPai, Integer> getAllJanPaiMap() {
        synchronized (_HAND_LOCK) {
            return _hand.getAllJanPaiMap();
        }
    }
    
    /**
     * 確定面子数を取得
     * 
     * @return 確定面子数。
     */
    public int getFixedMenTsuCount() {
        synchronized (_HAND_LOCK) {
            return _hand.getFixedMenTsuCount();
        }
    }
    
    /**
     * 確定面子リストを取得
     * 
     * @return 確定面子リスト。
     */
    public List<MenTsu> getFixedMenTsuList() {
        synchronized (_HAND_LOCK) {
            return _hand.getFixedMenTsuList();
        }
    }
    
    /**
     * 指定牌の所持数を取得
     * 
     * @param pai 検索対象。
     * @return 所持数。
     */
    public int getJanPaiCount(final JanPai pai) {
        synchronized (_HAND_LOCK) {
            return _hand.getJanPaiCount(pai);
        }
    }
    
    /**
     * 面前手牌の上限枚数を取得
     * 
     * @return 面前手牌の上限枚数。
     */
    public int getLimitSize() {
        synchronized (_HAND_LOCK) {
            return _hand.getLimitSize();
        }
    }
    
    /**
     * 面前手牌リストを取得
     * 
     * @return 面前手牌リスト。
     */
    public List<JanPai> getMenZenList() {
        synchronized (_HAND_LOCK) {
            return _hand.getMenZenList();
        }
    }
    
    /**
     * 面前手牌数を取得
     * 
     * @return 面前手牌数。
     */
    public int getMenZenSize() {
        synchronized (_HAND_LOCK) {
            return _hand.getMenZenSize();
        }
    }
    
    /**
     * 初期化処理
     * 
     * @param activity 親画面。
     */
    public void initialize(final Activity activity) {
        if (activity == null) {
            throw new NullPointerException("Parent activity is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            _parent = activity;
        }
    }
    
    /**
     * 牌裏か
     * 
     * @param viewID ビューID。
     * @return 判定結果。
     */
    public boolean isBlankJanPai(final int viewID) {
        return !_handViewIDMap.containsKey(viewID);
    }
    
    /**
     * 面前手牌の枚数上限に達しているか
     * 
     * @return 判定結果。
     */
    public boolean isLimitSize() {
        synchronized (_HAND_LOCK) {
            return _hand.isLimitSize();
        }
    }
    
    /**
     * 指定インデックスの確定面子を削除
     * 
     * @param index インデックス。
     */
    public void removeFixedMenTsu(final int index) {
        synchronized (_HAND_LOCK) {
            _hand.removeFixedMenTsu(index);
        }
    }
    
    /**
     * 指定ビューの牌を削除
     * 
     * @param viewID ビューID。
     */
    public void removeJanPai(final int viewID) {
        if (!_handViewIDMap.containsKey(viewID)) {
            throw new IllegalArgumentException("Invalid view ID - " + viewID);
        }
        
        synchronized (_HAND_LOCK) {
            _hand.removeJanPai(_handViewIDMap.get(viewID));
        }
    }
    
    /**
     * 手牌ビューを更新
     */
    public void updateView() {
        synchronized (_HAND_LOCK) {
            synchronized (_PARENT_LOCK) {
                final RelativeLayout handView =
                    (RelativeLayout)_parent.findViewById(R.id.main_hand_layout);
                updateHandView(handView);
                
                final RelativeLayout fixedMenTsuView =
                    (RelativeLayout)_parent.findViewById(R.id.main_fixed_layout);
                updateFixedMenTsuView(fixedMenTsuView);
            }
            
            setChanged();
            notifyObservers(_hand.clone());
        }
        clearChanged();
    }
    
    
    
    /**
     * 確定面子ビューのパラメータを生成
     * 
     * @param baseID 起点ID。
     * @return 確定面子ビューのパラメータ。
     */
    private RelativeLayout.LayoutParams createFixedMenTsuViewParam(final int baseID) {
        final RelativeLayout.LayoutParams param =
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                            LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.LEFT_OF, baseID);
        param.addRule(RelativeLayout.ALIGN_BOTTOM, baseID);
        return param;
    }
    
    /**
     * 手牌ビューのパラメータを生成
     * 
     * @param handID 手牌ID。
     * @return 手牌ビューのパラメータ。
     */
    private RelativeLayout.LayoutParams createHandViewParam(final int handID) {
        final int index = handID - MTPConst.HAND_VIEW_MAIN_BASE_ID;
        if (index < 0) {
            throw new IllegalArgumentException("Invalid hand ID - " + handID);
        }
        
        final RelativeLayout.LayoutParams param =
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                            LayoutParams.WRAP_CONTENT);
        if (index != 0) {
            param.addRule(RelativeLayout.RIGHT_OF, handID - 1);
        }
        return param;
    }
    
    /**
     * 雀牌ビュー生成オブジェクトを生成
     * 
     * @return ビュー生成オブジェクト。
     */
    private JanPaiViewFactory createJanPaiViewFactory() {
        return new JanPaiViewFactory(_parent);
    }
    
    /**
     * 面子ビューの基点を生成
     * 
     * @return 面子ビューの基点。
     */
    private LinearLayout createMenTsuViewBase() {
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final RelativeLayout.LayoutParams layoutParam =
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                            LayoutParams.WRAP_CONTENT);
        layoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        final int viewID = MTPConst.FIXED_VIEW_MAIN_BASE_ID - 1;
        return factory.createMenTsuMinHeightDummyView(viewID, layoutParam);
    }
    
    /**
     * 確定面子ビュー更新の中核処理
     * 
     * @param fixedMenTsuView 確定面子ビュー。
     */
    private void updateFixedMenTsuView(final RelativeLayout fixedMenTsuView) {
        fixedMenTsuView.removeAllViews();
        
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final List<LinearLayout> viewList = new ArrayList<LinearLayout>();
        
        // 常にカン牌と同じ高さのビュー領域を確保するためのダミー
        final LinearLayout baseView = createMenTsuViewBase();
        viewList.add(baseView);
        
        int count = 0;
        for (final MenTsu menTsu : _hand.getFixedMenTsuList()) {
            final int menTsuID = MTPConst.FIXED_VIEW_MAIN_BASE_ID + count;
            final int baseID = (count == 0) ? baseView.getId() : (menTsuID - 1);
            final RelativeLayout.LayoutParams layoutParam = createFixedMenTsuViewParam(baseID);
            final LinearLayout view = factory.createMenTsuView(menTsu, menTsuID, layoutParam);
            view.setPadding(10, 0, 0, 0);
            view.setClickable(true);
            view.setOnClickListener(new FixedMenTsuButtonListener());
            viewList.add(view);
            count++;
        }
        
        for (final LinearLayout view : viewList) {
            fixedMenTsuView.addView(view);
        }
    }
    
    /**
     * 手牌ビュー更新の中核処理
     * 
     * @param handView 手牌ビュー。
     */
    private void updateHandView(final RelativeLayout handView) {
        handView.removeAllViews();
        
        final JanPaiViewFactory factory = createJanPaiViewFactory();
        final List<ImageView> viewList = new ArrayList<ImageView>();
        int count = 0;
        for (final JanPai pai : _hand.getMenZenList()) {
            final int handID = MTPConst.HAND_VIEW_MAIN_BASE_ID + count;
            final RelativeLayout.LayoutParams layoutParam = createHandViewParam(handID);
            final Bitmap image = ImageResourceManager.getInstance().getImage(pai);
            final ImageView view = factory.createJanPaiView(handID, image, layoutParam);
            view.setClickable(true);
            view.setOnClickListener(new HandButtonListener());
            viewList.add(view);
            _handViewIDMap.put(view.getId(), pai);
            count++;
        }
        
        final int limitSize = _hand.getLimitSize();
        for (; count < limitSize; count++) {
            final int handID = MTPConst.HAND_VIEW_MAIN_BASE_ID + count;
            final RelativeLayout.LayoutParams layoutParam = createHandViewParam(handID);
            final ImageView view = factory.createBlankJanPaiView(handID, layoutParam);
            viewList.add(view);
        }
        
        for (final ImageView view : viewList) {
            handView.addView(view);
        }
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final HandManager INSTANCE = new HandManager();
    
    
    
    /**
     * ロックオブジェクト (親画面)
     */
    private final Object _PARENT_LOCK = new Object();
    
    /**
     * ロックオブジェクト (手牌)
     */
    private final Object _HAND_LOCK = new Object();
    
    
    
    /**
     * 親画面
     */
    private Activity _parent = null;
    
    /**
     * 手牌
     */
    private Hand _hand = new Hand();
    
    /**
     * 手牌ビューIDマップ
     */
    private Map<Integer, JanPai> _handViewIDMap =
        Collections.synchronizedSortedMap(new TreeMap<Integer, JanPai>());
    
    
    
    /**
     * 手牌ボタンリスナー
     */
    private final class HandButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public HandButtonListener() {
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            final int viewID = view.getId();
            if (HandManager.getInstance().isBlankJanPai(viewID)) {
                return;
            }
            HandManager.getInstance().removeJanPai(viewID);
            HandManager.getInstance().updateView();
        }
    }
    
    /**
     * 確定面子ボタンリスナー
     */
    private final class FixedMenTsuButtonListener implements View.OnClickListener {
        
        /**
         * コンストラクタ
         */
        public FixedMenTsuButtonListener() {
        }
        
        /**
         * クリック時の処理
         */
        public void onClick(final View view) {
            final int viewID = view.getId();
            final int index = viewID - MTPConst.FIXED_VIEW_MAIN_BASE_ID;
            HandManager.getInstance().removeFixedMenTsu(index);
            HandManager.getInstance().updateView();
        }
    }
    
}
