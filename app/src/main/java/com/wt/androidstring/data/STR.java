package com.wt.androidstring.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by WT on 2017/8/3.
 */

public class STR {
    public static final String TAG = "STR";

    public static void init()
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
}
