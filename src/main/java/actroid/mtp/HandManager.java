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
import java.util.Observer;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
     * 空き枚数を取得
     * 
     * @return 空き枚数。
     */
    public int getUsableSize() {
        synchronized (_HAND_LOCK) {
            return _hand.getUsableSize();
        }
    }
    
    /**
     * 初期化処理
     * 
     * @param activity 親画面。
     * @param observer 監視者。手牌の飽和を通知する。
     */
    public void initialize(final Activity activity, final Observer observer) {
        if (activity == null) {
            throw new NullPointerException("Parent activity is null.");
        }
        
        synchronized (_PARENT_LOCK) {
            _parent = activity;
        }
        addObserver(observer);
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
                final LinearLayout handView = getHandView();
                updateHandView(handView);
                
                final LinearLayout fixedMenTsuView = getFixedMenTsuView();
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
     * @return 確定面子ビューのパラメータ。
     */
    private LinearLayout.LayoutParams createFixedMenTsuViewParam() {
        final LinearLayout.LayoutParams param =
            new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);
        param.weight = 1.0f;
        param.gravity = Gravity.BOTTOM;
        return param;
    }
    
    /**
     * 手牌ビューのパラメータを生成
     * 
     * @return 手牌ビューのパラメータ。
     */
    private LinearLayout.LayoutParams createHandViewParam() {
        final LinearLayout.LayoutParams param =
            new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);
        param.weight = 1.0f;
        return param;
    }
    
    /**
     * ビュー生成オブジェクトを生成
     * 
     * @return ビュー生成オブジェクト。
     */
    private JanPaiViewFactory createViewFactory() {
        return new JanPaiViewFactory(_parent);
    }
    
    /**
     * 確定面子ビューを取得
     * 
     * @return 確定面子ビュー。
     */
    private LinearLayout getFixedMenTsuView() {
        return (LinearLayout)_parent.findViewById(R.id.main_fixed_layout);
    }
    
    /**
     * 手牌ビューを取得
     * 
     * @return 手牌ビュー。
     */
    private LinearLayout getHandView() {
        return (LinearLayout)_parent.findViewById(R.id.main_hand_layout);
    }
    
    /**
     * 確定面子ビュー更新の中核処理
     * 
     * @param fixedMenTsuView 確定面子ビュー。
     */
    private void updateFixedMenTsuView(final LinearLayout fixedMenTsuView) {
        fixedMenTsuView.removeAllViews();
        
        int count = 0;
        final JanPaiViewFactory factory = createViewFactory();
        final LinearLayout.LayoutParams layoutParam = createFixedMenTsuViewParam();
        final List<List<ImageView>> viewList = new ArrayList<List<ImageView>>();
        for (final MenTsu menTsu : _hand.getFixedMenTsuList()) {
            final int menTsuID = MTPConst.FIXED_VIEW_MAIN_BASE_ID + count;
            final List<ImageView> view = factory.createMenTsuView(menTsu, menTsuID, layoutParam);
            view.get(0).setPadding(10, 0, 0, 0);
            
            for (final ImageView pai : view) {
                pai.setClickable(true);
                pai.setOnClickListener(new FixedMenTsuButtonListener());
            }
            viewList.add(view);
            count++;
        }
        
        for (; count < 4; count++) {
            // 常に四副露時のサイズで画像を表示するため、不可視のビューを追加
            final List<ImageView> view = factory.createInvisibleMenTsuView(layoutParam);
            view.get(0).setPadding(10, 0, 0, 0);
            viewList.add(view);
        }
        
        // 最初に副露した面子が右に来るように逆順ソート
        Collections.reverse(viewList);
        for (final List<ImageView> view : viewList) {
            for (final ImageView pai : view) {
                fixedMenTsuView.addView(pai);
            }
        }
    }
    
    /**
     * 手牌ビュー更新の中核処理
     * 
     * @param handView 手牌ビュー。
     */
    private void updateHandView(final LinearLayout handView) {
        handView.removeAllViews();
        
        final JanPaiViewFactory factory = createViewFactory();
        final LinearLayout.LayoutParams layoutParam = createHandViewParam();
        final List<ImageView> viewList = new ArrayList<ImageView>();
        int count = 0;
        for (final JanPai pai : _hand.getMenZenList()) {
            final int handID = MTPConst.HAND_VIEW_MAIN_BASE_ID + count;
            final Bitmap image = ImageResourceManager.getInstance().getImage(pai);
            final ImageView view = factory.createJanPaiView(handID, image, layoutParam);
            viewList.add(view);
            _handViewIDMap.put(view.getId(), pai);
            count++;
        }
        
        final int limitSize = _hand.getLimitSize();
        for (; count < limitSize; count++) {
            final int handID = MTPConst.HAND_VIEW_MAIN_BASE_ID + count;
            final ImageView view = factory.createBlankJanPaiView(handID, layoutParam);
            viewList.add(view);
        }
        
        for (final ImageView view : viewList) {
            view.setClickable(true);
            view.setOnClickListener(new HandButtonListener());
        }
        
        for (; count < 14; count++) {
            // 常に14牌時のサイズで画像を表示するため、不可視のビューを追加
            viewList.add(factory.createInvisibleJanPaiView(layoutParam));
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
