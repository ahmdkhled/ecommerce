package com.ahmdkhled.ecommerce.utils;

import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

public class SnackBarUtil  {

    public static Snackbar showSnackBar(ViewGroup layout){
        Snackbar snackbar=Snackbar.make(layout,"there is no connection",Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }
}
