/**
 * 
 */
package com.aol.mobile.moviefoneouya.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.aol.mobile.core.metrics.MetricsActivity;
//import com.aol.mobile.moviefonetogo.Constants;
//import com.aol.mobile.moviefonetogo.R;

public class BaseActivity extends MetricsActivity {

	
	/*
	 * Stub class that TrailerView extends, For now ignore the onCreateDialog Function
	 * 
	 */
	
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dlg = null;
//		switch (id) {
//		case Constants.DIALOG_NO_NETWORK_CONNECTION:
//			dlg = new AlertDialog.Builder(this).setTitle(R.string.app_name)
//					.setIcon(android.R.drawable.ic_dialog_alert)
//					.setMessage(R.string.no_network_connection_available)
//					.setPositiveButton(android.R.string.ok, null).create();
//			break;
//
//		case Constants.DIALOG_LOADING_PROGRESS:
//			dlg = new ProgressDialog(this);
//			dlg.setCancelable(true);
//			dlg.setOnCancelListener(new OnCancelListener() {
//				public void onCancel(DialogInterface dialog) {
//					finish();
//				}
//			});
//			((ProgressDialog) dlg).setMessage(getResources().getString(
//					R.string.loading_please_wait));
//			((ProgressDialog) dlg).setIndeterminate(true);
//			break;
//
//		case Constants.DIALOG_NO_MOVIE_SEARCH_RESULT:
//			dlg = new AlertDialog.Builder(this)
//					.setTitle(R.string.no_movie_found)
//					.setIcon(android.R.drawable.ic_dialog_alert)
//					.setMessage(R.string.no_movie_found_msg)
//					.setPositiveButton(android.R.string.ok, null).create();
//			break;
//
//		}
		return dlg;
	}

}
