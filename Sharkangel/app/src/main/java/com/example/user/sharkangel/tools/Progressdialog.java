package com.example.user.sharkangel.tools;

import android.app.ProgressDialog;
import android.content.Context;


public class Progressdialog {
    private Context _context;
    private ProgressDialog pDialog;

    public Progressdialog(Context context)
    {
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        this._context=context;
    }

    public void msg(String msg)
    {
        pDialog.setMessage(msg);
    }



    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}
