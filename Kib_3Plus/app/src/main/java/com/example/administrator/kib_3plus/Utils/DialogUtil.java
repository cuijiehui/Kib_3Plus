package com.example.administrator.kib_3plus.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kib_3plus.R;

/**
 * Created by cui on 2017/6/29.
 */

public enum DialogUtil {
    INSTANCE;

    private  AlertDialog alertDialog;
    private  ProgressDialog dialog;
    private Context mContext;
    public void init(Context context){
        mContext=context;
    }
    /**
     * 通用警告对话框
     *
     * @param context 应用上下文
     * @param title   对话框标题
     * @param message 对话框信息内容
     */
    public void commonDialog(Context context, String title, String message) {
        if (context == null)
            return;
        if (false) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            TextView t1 = new TextView(context);
            //alertDialog.setView(t1,0,0,0,0);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.setButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }

    }
    private Toast toast;

    public void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
    /**
     * 通用进度条对话框
     *
     * @param context 应用上下文
     * @param title   对话框标题
     * @param message 对话框信息内容
     * @return
     */
    public ProgressDialog comProDialog(Context context, String title, String message) {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

    /**
     * 网络连接错误
     *
     * @param context
     */
    public void conectionError(Context context) {
        String title = context.getString(R.string.app_name);
        String message = "Conection Error";
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertDialog.show();

    }

    public ProgressDialog showMsg(Context context, String msg) {
        String title = context.getString(R.string.app_name);
        String message = msg;
        ProgressDialog prDialog = new ProgressDialog(context);
        prDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prDialog.setTitle(title);
        prDialog.setMessage(message);
        prDialog.setIcon(R.mipmap.ic_launcher);
        prDialog.setCancelable(true);
        return prDialog;
    }

    /**
     * 用户名或密码错误
     *
     * @param context
     */
    public void userOrPwdError(Context context) {
        String title = context.getString(R.string.app_name);
        String message = context.getString(R.string.login_username_wrong);
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertDialog.show();

    }

    /**
     * 登陆
     *
     * @param context
     * @return
     */
    public ProgressDialog logining(Context context) {
        String title = context.getString(R.string.app_name);
        String message = context.getString(R.string.login_loading);
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;

    }

    /**
     * 同步
     *
     * @return
     */
    public ProgressDialog syncing(Context context) {
        String title = context.getString(R.string.app_name);
        String message = context.getResources().getString(R.string.login_loading);
        ProgressDialog prDialog = new ProgressDialog(context);
        prDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prDialog.setTitle(title);
        prDialog.setMessage(message);
        prDialog.setIcon(R.mipmap.ic_launcher);
        prDialog.setCancelable(true);

        return prDialog;

    }


    public void showNeedUpdateFirmware(final Context mContext) {

        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);

        ad.setCancelable(false);
        ad.setMessage(mContext.getString(R.string.FirmwareUpgradeRequired));
        ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        ad.setPositiveButton(R.string.Download_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.update_firmware_website)));
                mContext.startActivity(intent);

            }
        });

        ad.show();


    }

    public interface DialogCallBackOK {
        void onClickOK();
    }
    public void showChooseDialog(final Context context, String msg, final DialogCallBackOK callBack) {
        if (alertDialog != null && alertDialog.isShowing()) {
            LogUtils.i("", "有选择框在运行,不继续显示了...!!!");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setNegativeButton(context.getString(android.R.string.no), null);
        builder.setPositiveButton(context.getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.onClickOK();
            }
        });
        alertDialog = builder.show();
    }



    /**
     * 关闭对话框
     */
    public  void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }


    /**
     * 升级
     * @param context
     * @return
     */

    public ProgressDialog updateing(Context context) {
        String title = context.getResources().getString(R.string.app_name)+"";
        String message = context.getResources().getString(R.string.updateing)+"";
        ProgressDialog prDialog = new ProgressDialog(context);
        prDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prDialog.setTitle(title);
        prDialog.setMessage(message);
        prDialog.setIcon(R.mipmap.ic_launcher);
        prDialog.setCancelable(true);

        return prDialog;

    }

}
