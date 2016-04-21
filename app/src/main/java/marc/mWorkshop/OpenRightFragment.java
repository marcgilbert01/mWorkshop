package marc.mWorkshop;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OpenRightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OpenRightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class OpenRightFragment extends Fragment {

    private static final String ARG_TEXT_TO_SHOW = "textToShowParam";

    private String textToShow;
    private OnFragmentInteractionListener mListener;

    public OpenRightFragment() {
        // Required empty public constructor
    }

    public static OpenRightFragment newInstance(String textToShow) {

        OpenRightFragment fragment = new OpenRightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT_TO_SHOW, textToShow);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            textToShow = getArguments().getString(ARG_TEXT_TO_SHOW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate( R.layout.fragment_open_right , container , false );
        // SET TEXT WITH PARAM 1
        TextView textViewInFrament = (TextView) view.findViewById(R.id.textViewInFragment);
        textViewInFrament.setText( textToShow );
        // SET CLOSE BUTTON LISTENER TO CALL PARENT ACTIVITY
        Button buttonClose = (Button) view.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeFragment();
            }
        });

        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);

        void closeFragment();

    }
}
