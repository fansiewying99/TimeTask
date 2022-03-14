package com.g3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private TextInputEditText edit_tag_name;

    private FloatingActionButton edit_tag_cancel_btn;
    private FloatingActionButton delete_tag_btn;
    private FloatingActionButton edit_tag_btn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String tag_name = getArguments().getString("tag_name");

        edit_tag_name = (TextInputEditText) view.findViewById(R.id.edit_tag_name);
        edit_tag_name.setText(tag_name);

        edit_tag_btn = view.findViewById(R.id.edit_tag_btn);
        edit_tag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagName = edit_tag_name.getEditableText().toString().trim();

                if (tagName.isEmpty()){
                    edit_tag_name.requestFocus();
                    edit_tag_name.setError("Tag Name is required");
                    return;
                }

                SettingsDB settingsDB =((MainActivity)getActivity()).getSettingsDB();


            }
        });

    }

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditTagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTagFragment newInstance(String param1, String param2) {
        EditTagFragment fragment = new EditTagFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_tag, container, false);
    }
}