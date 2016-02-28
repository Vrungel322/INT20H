package me.kpr.nnp.fragments;


import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import me.kpr.nnp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements SwichChanger{


    private Switch swNFC;
    private TextView tvNFChelpTExt;
    private Button bNFCSettings;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        tvNFChelpTExt = (TextView) rootView.findViewById(R.id.tvNFChelpTExt);
        bNFCSettings = (Button) rootView.findViewById(R.id.bNFCSettings);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Bluetooth Stuff
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Switch swBluetooth = (Switch) view.findViewById(R.id.swBluetooth);
        checkBluetooth(view, bluetoothAdapter, swBluetooth);
        swBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    bluetoothAdapter.disable();
                }else {
                    bluetoothAdapter.enable();
                }
            }
        });

        //NFC Stuff
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(view.getContext());
        chechNFC(view, nfcAdapter);
        bNFCSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(intent);
            }
        });
    }

   // chechNFC(view, nfcAdapter, swNFC);

    private void checkBluetooth(View view, BluetoothAdapter bluetoothAdapter, Switch swBluetooth) {
        if (bluetoothAdapter.isEnabled()){
            swBluetooth.setChecked(true);
            Toast.makeText(view.getContext(), "bluetooth ON", Toast.LENGTH_SHORT).show();
        } else {
            swBluetooth.setChecked(false);
            Toast.makeText(view.getContext(), "bluetooth OFF", Toast.LENGTH_SHORT).show();
        }
    }


    private void chechNFC(View view, NfcAdapter nfcAdapter) {
        if (nfcAdapter.isEnabled()){
            //swNFC.setChecked(true);
            tvNFChelpTExt.setText("NFC is ON");
            Toast.makeText(view.getContext(), "NFC ON", Toast.LENGTH_SHORT).show();
        } else {
            //swNFC.setChecked(false);
            tvNFChelpTExt.setText("NFC is OFF");
            Toast.makeText(view.getContext(), "NFC OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChangeSwitch(int state) {
        if (state == NfcAdapter.STATE_ON){
            tvNFChelpTExt.setText("NFC is ON");
        } else {
            tvNFChelpTExt.setText("NFC is OFF");
        }

    }
}
