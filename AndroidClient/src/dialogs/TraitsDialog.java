package dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.annotations.client.AnnotationActivity;
import com.annotations.client.MainActivity;
import com.annotations.client.R;

public class TraitsDialog extends DialogFragment {

	List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
	View view;
	
	public static TraitsDialog create() {
		TraitsDialog frag = new TraitsDialog();
        return frag;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/*
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		String[] items = {"Volonté", "Imagination", "Entrainement"};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.traits_dialog, null);

		//listInit();
		builder.setView(inflater.inflate(R.layout.traits_dialog, null))
		.setMessage("Traits")
		
		.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.trait_item, items), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		})
		 
		.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Dialog f = (Dialog) dialog;

			}
		});

		return builder.create();
	}
*/

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.traits_dialog, container);
		ListView v = (ListView) view.findViewById(R.id.traits_list);
		v.setAdapter(new BaseAdapter() {

			public View getView(int position, View convertView, ViewGroup parent) {
				View traitItem = null;
				if (convertView == null) {
					traitItem = inflater.inflate(R.layout.trait_item, parent, false);

				}
				else {
					traitItem = convertView;
				}
				TextView textView = (TextView) traitItem.findViewById(R.id.trait_name);
				switch (position) {
				case 0: textView.setText("Volonte"); break;
				case 1: textView.setText("Imagination"); break;
				}
				return traitItem;

			}

			@Override public long getItemId(int arg0) { 
				return arg0; }
			@Override public Object getItem(int arg0) { return arg0; }
			@Override public int getCount() { return 2; }
		});
		return view;
	}


	/*
	public void listInit() {

		ListView listView = (ListView)view.findViewById(R.id.traits_list);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		map.put("nom", "Volonté");
		map2.put("nom", "Imagination");
		map3.put("nom", "Entrainement");
		listItem.add(map);
		listItem.add(map2);
		listItem.add(map3);

		SimpleAdapter mSchedule = new SimpleAdapter (getActivity().getBaseContext(), listItem, R.layout.trait_item,
				new String[] {"nom"}, new int[] {R.id.trait_name});
		listView.setAdapter(mSchedule);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// Action quand annotation cliquée ?
			}
		});
	}
	 */

}
