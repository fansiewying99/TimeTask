package com.g3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText tag_name;

    private FloatingActionButton add_tag_btn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tag_name = (TextInputEditText) view.findViewById(R.id.tag_name);

        add_tag_btn = (FloatingActionButton) view.findViewById(R.id.add_tag_btn);
        add_tag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagName = tag_name.getEditableText().toString().trim();

                if (tagName.isEmpty()){
                    tag_name.requestFocus();
                    tag_name.setError("Tag Name is required");
                    return;
                }

                SettingsDB settingsDB =((MainActivity)getActivity()).getSettingsDB();
                Tag tag = new Tag(0, tagName);

                // add to database here
                settingsDB.addTag(tag);
//                TagRecViewAdapter tagAdapter = ((MainActivity)getActivity()).getTagAdapter();
//                tagAdapter.addTag(tag);

                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    //String item = "Pig";
                    //int insertIndex = 2;
                    getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
                    getFragmentManager().popBackStack();
                }
            }
        });
    }

    public AddTagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addTagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTagFragment newInstance(String param1, String param2) {
        AddTagFragment fragment = new AddTagFragment();
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
        return inflater.inflate(R.layout.fragment_add_tag, container, false);
    }
}