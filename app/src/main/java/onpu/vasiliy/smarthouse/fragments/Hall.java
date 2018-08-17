package onpu.vasiliy.smarthouse.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import onpu.vasiliy.smarthouse.LampChangeTask;
import onpu.vasiliy.smarthouse.R;
import onpu.vasiliy.smarthouse.http.Const;

public class Hall extends Fragment{
    private ToggleButton tbLamp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hall,container,false);
        tbLamp = view.findViewById(R.id.tbLampHall);

        tbLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(getContext(),"toggle Hall on",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("hall","on");
                }else{
                    Toast.makeText(getContext(),"toggle Hall off",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("hall","off");
                }
            }
        });
        return view;
    }
}
