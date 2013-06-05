/**
 * ProgressDialogStyle.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.android.util;

import android.app.ProgressDialog;



/**
 * 進捗ダイアログスタイル
 */
public enum ProgressDialogStyle {
    
    SPINNER,
    BAR;
    
    
    
    /**
     * スタイルコードに変換
     * 
     * @return 変換結果。
     */
    public int toCode() {
        switch (this) {
        case SPINNER:
            return ProgressDialog.STYLE_SPINNER;
        case BAR:
            return ProgressDialog.STYLE_HORIZONTAL;
        default:
            throw new InternalError();
        }
    }
    
}

