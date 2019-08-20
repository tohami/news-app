package com.tohami.newsapi.utilities;

import android.content.Context;
import android.widget.Toast;

public class ViewHelpers {

    public static void showToast(Context context , String content , boolean isLong){
        Toast.makeText(
                context,
                content,
                isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
                .show();
    }


    public static void showToast(Context context , String content){
        showToast(context , content , false);
    }
}
