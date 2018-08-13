package onpu.vasiliy.smarthouse.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import onpu.vasiliy.smarthouse.R;

public class Hall extends Fragment{
    private ImageView ivLamp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hall,container,false);
        ivLamp = view.findViewById(R.id.ivLamp);
        ivLamp.setImageResource(R.drawable.lamp_off);
        ivLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        "Image",
                        Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
