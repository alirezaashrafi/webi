package com.ashrafi.webi.interfaces;


import android.provider.DocumentsContract;

import org.w3c.dom.Document;

/**
 * Created by AlirezaAshrafi on 1/1/2018.
 */

public interface OnXmlReceive {

    void xml(Document xml,String where);
}
