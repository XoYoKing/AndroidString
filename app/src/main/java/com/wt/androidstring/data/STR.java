package com.wt.androidstring.data;

import android.util.SparseArray;
import android.util.Xml;

import com.wt.androidstring.common.ConstVariable;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WT on 2017/8/3.
 */


//adb push D:\GitHub\WT\Android\APP\AndroidString /sdcard
//adb pull /storage/emulated/0/AndroidString/O/export d:\_temp
public class STR {
    public static final String TAG = "STR";

    public static final String KeyImportType = "ImportType";
    public static final String KeyImportValue = "ImportValue";
    public static final String KeyImportStringFileName = "ImportStringFileName";

    public static int m_iImportType=0;
    public static String m_strImportStringFileName="strings.xml";

    public static void doInit()
    {
        try {
            File f = new File("E:est.txt");
            InputStream is = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String strLine="";
            long lTimeBase=-1;
            while ((strLine = bufferedReader.readLine()) != null) {

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void doImport()
    {
        String strMSG="doImport-";
        try {
            String strFileConfig= ConstVariable.getPathAppData()+"config.ini";
            if ( !ConstVariable.isExistFile(strFileConfig) )
            {
                ConstVariable.ShowLog(0,TAG,strMSG+"No File = "+strFileConfig);
                return;
            }
            File f = new File(strFileConfig);
            InputStream is = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String strLine="";
            long lTimeBase=-1;
            int i=0;
            while ((strLine = bufferedReader.readLine()) != null) {
                ConstVariable.ShowLog(0,TAG,strMSG+"strLine = "+strLine);
                String [] aConfigKey = null;
                aConfigKey = strLine.split(",");
                if ( aConfigKey.length>1 )
                {
                    if ( KeyImportType.equalsIgnoreCase(aConfigKey[0]) )
                    {
                        m_iImportType=Integer.parseInt(aConfigKey[1]);
                    }
                    else if ( KeyImportValue.equalsIgnoreCase(aConfigKey[0]) )
                    {
                        for ( i=1;i<aConfigKey.length;i++ )
                        {
                            LANGUAGE.add(aConfigKey[i]);
                        }
                    }
                    else if ( KeyImportStringFileName.equalsIgnoreCase(aConfigKey[0]) )
                    {
                        m_strImportStringFileName=aConfigKey[1];
                    }
                }
            }

            for ( i=0;i<LANGUAGE.m_saLanguage.size();i++ )
            {
                String strLanguage=(String)LANGUAGE.m_saLanguage.valueAt(i);
                addLanguage(strLanguage);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addLanguage(String strLanguage)
    {
        String strMSG="addLanguage-";
        try {
            ConstVariable.ShowLog(0,TAG,strMSG+"strLanguage = "+strLanguage);

            String strFileString= ConstVariable.getPathAppData()+"I/import/"+strLanguage+"/"+m_strImportStringFileName;
            if ( !ConstVariable.isExistFile(strFileString) )
            {
                ConstVariable.ShowLog(0,TAG,strMSG+"No File = "+strFileString);
                return;
            }
            File f = new File(strFileString);
            InputStream is = new FileInputStream(f);
            //使用PULL解析
            XmlPullParser xmlPullParser= Xml.newPullParser();
            xmlPullParser.setInput(is,"UTF-8");
            //获取解析的标签的类型
            int type=xmlPullParser.getEventType();
            while(type!=XmlPullParser.END_DOCUMENT){
                switch (type) {
                    case XmlPullParser.START_TAG:
                        //获取开始标签的名字
                        String starttgname = xmlPullParser.getName();
                        if ("string".equals(starttgname)) {
                            //获取id的值
                            String strKey = xmlPullParser.getAttributeValue(0);
                            String strValue = xmlPullParser.nextText();
                            ConstVariable.ShowLog(0,TAG,strMSG+strKey+" ： "+strValue);
                            KYVALUE theKYVALUE=KY.getKEY(strKey);
                            theKYVALUE.putValue(strLanguage,strValue);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }//细节：
                type=xmlPullParser.next();
            }

//            InputStreamReader reader = new InputStreamReader(is);
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            String strLine="";
//            long lTimeBase=-1;
//            int i=0;
//            while ((strLine = bufferedReader.readLine()) != null) {
//                ConstVariable.ShowLog(0,TAG,strMSG+"strLine = "+strLine);
//                //<string name="app_name">图库</string>
//                String [] aConfigKey = null;
//                aConfigKey = strLine.split(",");
//                if ( aConfigKey.length>1 )
//                {
//                    DocumentBuilderFactory
//                }
//            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void doExport()
    {
        doExportCSV();
        doExportTXTTAB();
    }
    public static void doExportCSV()
    {
        String strMSG="addLanguage-";
        int iCountLanguage=LANGUAGE.m_saLanguage.size();
        ConstVariable.ShowLog(0,TAG,strMSG+"iCountLanguage = "+iCountLanguage);
        if ( iCountLanguage<1 )
        {
            return;
        }
        int iCountKey=KY.m_aKEY.size();
        ConstVariable.ShowLog(0,TAG,strMSG+"iCountKey = "+iCountKey);
        if ( iCountKey<1 )
        {
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String strTM  =  formatter.format(curDate);

        //String strFileCSV= ConstVariable.getPathAppData()+"O/export/"+strTM+".csv";
        String strFileCSV= ConstVariable.getPath("O/export")+strTM+".csv";

        try {
            File file = new File(strFileCSV);
            if (!file.exists()) {
                ConstVariable.ShowLog(0,TAG,strMSG+"Create the file : "+strFileCSV);
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());

            //每次写入时，都换行写
            String strContent=LANGUAGE.toStringCSV()+"\n";
            raf.write(strContent.getBytes());
            int i=0;
            for ( i=0;i<iCountKey;i++ )
            {
                KYVALUE theKYVALUE=KY.m_aKEY.valueAt(i);
                //每次写入时，都换行写
                strContent=theKYVALUE.toStringCSV()+"\n";
                raf.write(strContent.getBytes());
            }

            raf.close();
        } catch (Exception e) {
            ConstVariable.ShowLog(0,TAG,strMSG+"Error on write File.");
        }
    }
    public static void doExportTXTTAB()
    {
        String strMSG="addLanguage-";
        int iCountLanguage=LANGUAGE.m_saLanguage.size();
        ConstVariable.ShowLog(0,TAG,strMSG+"iCountLanguage = "+iCountLanguage);
        if ( iCountLanguage<1 )
        {
            return;
        }
        int iCountKey=KY.m_aKEY.size();
        ConstVariable.ShowLog(0,TAG,strMSG+"iCountKey = "+iCountKey);
        if ( iCountKey<1 )
        {
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String strTM  =  formatter.format(curDate);

        //String strFileCSV= ConstVariable.getPathAppData()+"O/export/"+strTM+".csv";
        String strFileCSV= ConstVariable.getPath("O/export")+strTM+".txt";

        try {
            File file = new File(strFileCSV);
            if (!file.exists()) {
                ConstVariable.ShowLog(0,TAG,strMSG+"Create the file : "+strFileCSV);
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());

            //每次写入时，都换行写
            String strContent=LANGUAGE.toStringTXTTAB()+"\n";
            raf.write(strContent.getBytes());
            int i=0;
            for ( i=0;i<iCountKey;i++ )
            {
                KYVALUE theKYVALUE=KY.m_aKEY.valueAt(i);
                //每次写入时，都换行写
                strContent=theKYVALUE.toStringTXTTAB()+"\n";
                raf.write(strContent.getBytes());
            }

            raf.close();
        } catch (Exception e) {
            ConstVariable.ShowLog(0,TAG,strMSG+"Error on write File.");
        }
    }
}
