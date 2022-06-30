package com.onurkol.app.browser.pro.interfaces.developer;

import android.content.Context;

public interface ContextMenuActionsInterface {
    void getCheckItemCode(Context context);
    void setEditOnTouchedItem(String newCode);
    void setFlexboxOnTouchedItem();
    void setBypassOnTouchedItem();
}
