package dialogs;

import com.annotations.client.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.webkit.WebView.FindListener;
import android.widget.SeekBar;


public class TraitsDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		
		
		
		
		builder.setView(inflater.inflate(R.layout.traits_dialog, null))
		.setMessage("Traits")
		.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   // sign in the user ...
               }
           })
           .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   TraitsDialog.this.getDialog().cancel();
               }
           });
		
		return builder.create();
	}
}
