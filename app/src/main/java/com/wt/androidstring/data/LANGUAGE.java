package com.wt.androidstring.data;

import android.util.SparseArray;

/**
 * Created by ting.wong on 2017/8/4.
 */

public class LANGUAGE {
    public static final String TAG = "LANGUAGE";

    private static int m_iMaxKey=0;
    public static SparseArray m_saLanguage=new SparseArray();

    public static boolean add(String strLanguage)
    {
        if ( m_saLanguage.indexOfValue(strLanguage)<0 )
        {
            m_saLanguage.put(m_iMaxKey,strLanguage);
            m_iMaxKey++;
            return true;
        }
        return false;
    }

    public static String toStringCSV()
    {
        String strLine="KEY";
        int i=0;
        int iCountLanguage=m_saLanguage.size();
        for ( i=0;i<iCountLanguage;i++ )
        {
            strLine=strLine+","+LANGUAGE.m_saLanguage.valueAt(i);
        }
        return strLine;
    }
    public static String toStringTXTTAB()
    {
        String strLine="KEY";
        int i=0;
        int iCountLanguage=m_saLanguage.size();
        for ( i=0;i<iCountLanguage;i++ )
        {
            strLine=strLine+"XXXZYYY"+LANGUAGE.m_saLanguage.valueAt(i);
        }
        return strLine;
    }
}
