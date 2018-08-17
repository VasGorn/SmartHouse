package onpu.vasiliy.smarthouse.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import onpu.vasiliy.smarthouse.LampChangeTask;
import onpu.vasiliy.smarthouse.R;

public class Bathroom extends Fragment{
    private ToggleButton tbLamp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bathroom,container,false);
        tbLamp = view.findViewById(R.id.tbLampBathroom);

        tbLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(getContext(),"toggle Bathroom on",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("bathroom","on");
                }else{
                    Toast.makeText(getContext(),"toggle Bathroom off",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("bathroom","off");
                }
            }
        });

        return view;
    }
}
