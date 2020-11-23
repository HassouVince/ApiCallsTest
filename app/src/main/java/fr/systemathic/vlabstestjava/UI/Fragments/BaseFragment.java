package fr.systemathic.vlabstestjava.UI.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public interface Callback{
        void onFragmentViewClick(View view);
    }

    protected abstract int getFragmentLayout();
    protected abstract void initViews(View view, @Nullable Bundle data);
    protected abstract void setViews(View view);

    protected Callback callback;

    protected AlertDialog mdialog;
    protected ProgressDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initViews(view,savedInstanceState);
        return view;

    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage("Veuillez patienter..");
        setViews(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initCallback();
    }

    @Override
    public void onClick(View v) {
        callback.onFragmentViewClick(v);
    }

    private void initCallback(){
        try{
            callback = (Callback) getActivity();
        }catch (Exception e){}
    }
}
