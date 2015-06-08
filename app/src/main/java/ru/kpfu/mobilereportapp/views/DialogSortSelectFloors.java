package ru.kpfu.mobilereportapp.views;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ru.kpfu.mobilereportapp.R;

/**
 * Created by Azz on 01.06.2015.
 */
public class DialogSortSelectFloors extends DialogFragment {

    ListView listViewFloors;

    private OnCompleteListener mListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onAttach(getActivity());
        View v = inflater.inflate(R.layout.dialog_select_floor_layout, null);
        getDialog().setTitle(R.string.headerDialogSelectFloorsTrue);
        try {
            Bundle bundle = getArguments();

            listViewFloors = (ListView) v.findViewById(R.id.listViewFloors);

            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),
                    android.R.layout.simple_list_item_1, bundle.getIntegerArrayList("floors"));
            listViewFloors.setAdapter(adapter);

            listViewFloors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        TextView textView = (TextView) view;
                        mListener.onComplete(textView.getText().toString());
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }

    // make sure the Activity implemented it
    public void onAttach(Activity activity) {

        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
        super.onAttach(activity);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
