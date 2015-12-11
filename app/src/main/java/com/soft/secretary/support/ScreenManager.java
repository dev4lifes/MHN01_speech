package com.soft.secretary.support;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DEV4LIFE on 12/11/15.
 */
public class ScreenManager implements IScreen {

    private volatile static ScreenManager uniqueInstance;

    private ScreenManager(){}


    // Create Singleton pattern
    public static ScreenManager getInstance(){
        if(uniqueInstance==null){
            synchronized (ScreenManager.class){
                if (uniqueInstance==null){
                    uniqueInstance = new ScreenManager();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     *
     * @param context take this if in any Activity, if not is context
     * @param message message to show on screen
     * @param lengthTime take 1 if Length, 0 if Short
     */
    @Override
    public void showToast(Context context,String message,int lengthTime) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }


}
