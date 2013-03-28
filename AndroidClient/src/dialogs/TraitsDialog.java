package dialogs;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.annotations.client.R;

//http://stackoverflow.com/questions/4279787/how-can-i-pass-values-between-a-dialog-and-an-activity (Observer pattern)
@SuppressLint("UseValueOf")
public class TraitsDialog extends DialogFragment {

	OnTraitsDialogResult tDialogResult;
	public Map<String, String> values = new HashMap<String, String>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.traits_dialog, container, false);
		setCancelable(false);
		
		final SeekBar volonte_seekbar = (SeekBar) layout.findViewById(R.id.volonte_rating); 
		final SeekBar imagination_seekbar = (SeekBar) layout.findViewById(R.id.imagination_rating); 
		final SeekBar perception_seekbar = (SeekBar) layout.findViewById(R.id.perception_rating); 
		final SeekBar memoire_seekbar = (SeekBar) layout.findViewById(R.id.memoire_rating); 
		final SeekBar entrainement_seekbar = (SeekBar) layout.findViewById(R.id.entrainement_rating); 

		final TextView volonte_int = (TextView) layout.findViewById(R.id.volonte_int);
		final TextView imagination_int = (TextView) layout.findViewById(R.id.imagination_int);
		final TextView perception_int = (TextView) layout.findViewById(R.id.perception_int);
		final TextView memoire_int = (TextView) layout.findViewById(R.id.memoire_int);
		final TextView entrainement_int = (TextView) layout.findViewById(R.id.entrainement_int);

		final Button valider_traits = (Button) layout.findViewById(R.id.valider_traits);
		final Button annuler_traits = (Button) layout.findViewById(R.id.annuler_traits);
		
		volonte_int.setText("0");
		imagination_int.setText("0");
		perception_int.setText("0");
		memoire_int.setText("0");
		entrainement_int.setText("0");
		
		values.put("volonte", "0");
		values.put("imagination", "0");
		values.put("perception", "0");
		values.put("memoire", "0");
		values.put("entrainement", "0");

		volonte_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				volonte_int.setText(String.valueOf(new Integer(progress)));
				values.put("volonte", String.valueOf(new Integer(progress)));
			}
		});

		imagination_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				imagination_int.setText(String.valueOf(new Integer(progress)));
				values.put("imagination", String.valueOf(new Integer(progress)));
			}
		});

		perception_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				perception_int.setText(String.valueOf(new Integer(progress)));
				values.put("perception", String.valueOf(new Integer(progress)));
			}
		});

		memoire_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				memoire_int.setText(String.valueOf(new Integer(progress)));
				values.put("memoire", String.valueOf(new Integer(progress)));
			}
		});

		entrainement_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				entrainement_int.setText(String.valueOf(new Integer(progress)));
				values.put("entrainement", String.valueOf(new Integer(progress)));
			}
		});

		valider_traits.setOnClickListener(new ValiderListener());

		annuler_traits.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});

		return layout;
	}

	private class ValiderListener implements View.OnClickListener {
				
		public ValiderListener() {
			
		}

		@Override
		public void onClick(View v) {
			
			if( tDialogResult != null ){
                tDialogResult.finish(values);
            }
			TraitsDialog.this.dismiss();
		}

	}
	
	public void setDialogResult(OnTraitsDialogResult dialogResult){
        tDialogResult = dialogResult;
    }

	public interface OnTraitsDialogResult{
		void finish(Map<String, String> result);
	}

}
