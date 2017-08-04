package com.wt.androidstring.data;

import android.util.ArrayMap;
import android.util.SparseArray;

/**
 * Created by ting.wong on 2017/8/4.
 */

public class  KY{
    public static final String TAG = "KY";

    public static ArrayMap<String,KYVALUE> m_aKEY=new ArrayMap<String,KYVALUE>();

    public static KYVALUE getKEY(String strKEY)
    {
        if ( m_aKEY.containsKey(strKEY) )
        {
            return m_aKEY.get(strKEY);
        }
        KYVALUE theKYVALUE=new KYVALUE();
        theKYVALUE.m_strKEY=strKEY;
        m_aKEY.put(strKEY,theKYVALUE);
        return theKYVALUE;
    }
}
