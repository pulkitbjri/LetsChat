package com.mstr.letschat.tasks;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;

import com.mstr.letschat.databases.ContactTableHelper;
import com.mstr.letschat.model.Contact;
import com.mstr.letschat.tasks.Response.Listener;

public class QueryContactsTask extends BaseAsyncTask<Void, Void, List<Contact>> {
	private WeakReference<Context> contextWrapper;
	
	public QueryContactsTask(Listener<List<Contact>> listener, Context context) {
		super(listener);
		
		contextWrapper = new WeakReference<Context>(context);
	}
	
	@Override
	protected Response<List<Contact>> doInBackground(Void... params) {
		Context context = contextWrapper.get();
		if (context != null) {
			return Response.success(ContactTableHelper.getInstance(context).query());
		}
		
		return null;
	}
	
	@Override
	public void onPostExecute(Response<List<Contact>> response) {
		Listener<List<Contact>> listener = getListener();
		if (listener != null && response != null) {
			if (response.isSuccess()) {
				listener.onResponse(response.getResult());
			} else {
				listener.onErrorResponse(response.getException());
			}			
		}
	}
}