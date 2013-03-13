package com.annotations.client;

import restclient.AnnotationsRESTClientUsage;
import models.Video;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;


public class NameDialog extends DialogFragment {

	private Video video = new Video();
	public AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage(getActivity());

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setView(inflater.inflate(R.layout.name_dialog, null))
		.setMessage("Nom de la video")
		.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Dialog f = (Dialog) dialog;
				final EditText nomVideoView = (EditText)f.findViewById(R.id.change_video_name);
				video.setNom(nomVideoView.getText().toString());
				String result = client.postVideo(video);//"result";

				if(!result.equals("")) {
					video.setId(result);
					//String videoId = result;
					//client.postStream(video.getId(), video.getStream());
					Intent i = new Intent(getActivity(), AnnotationActivity.class);
					i.putExtra(MainActivity.ID, video.getId());
					i.putExtra(MainActivity.PATH, video.getPath());
					i.putExtra(MainActivity.NAME, video.getNom());
					startActivity(i);
				}
				else {
					Toast.makeText(getActivity().getApplicationContext(), "Error while upload. Please try again.", Toast.LENGTH_LONG).show();
				}
			}
		})
		.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				NameDialog.this.getDialog().cancel();
			}
		});      
		return builder.create();
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
