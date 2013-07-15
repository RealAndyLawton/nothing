package com.aol.mobile.moviefoneouya.ui;

import com.aol.mobile.moviefoneouya.R;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

public class ProgressDialogFragment extends DialogFragment{



	private ProgressDialog mProgressDialog = null;
	private int            mMax            = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle(R.string.loading_please_wait);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setMax(mMax);
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.show();
		
		return mProgressDialog;
	}

	// there seems to be a bug in the compat library - if I don't do the following - the dialog is not show after an orientation switch
	@Override
	public void onDestroyView() {
		if (getDialog() != null && getRetainInstance())
			getDialog().setDismissMessage(null);
		super.onDestroyView();
	}

	public void setMax(int arg1) {
		mProgressDialog.setMax(arg1);
		mMax = arg1;
	}

	public void setProgress(int arg1) {
		mProgressDialog.setProgress(arg1);
	}
}

