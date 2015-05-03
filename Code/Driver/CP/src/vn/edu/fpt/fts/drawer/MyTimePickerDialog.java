package vn.edu.fpt.fts.drawer;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class MyTimePickerDialog extends TimePickerDialog {

	private CharSequence title;

	public MyTimePickerDialog(Context context,
			OnTimeSetListener callBack, int hourOfDay, int minute,
			boolean is24HourView, CharSequence title) {
		super(context, callBack, hourOfDay, minute, is24HourView);
		this.title = title;
		setTitle(title);
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		super.onTimeChanged(view, hourOfDay, minute);
		setTitle(title);
	}
}
