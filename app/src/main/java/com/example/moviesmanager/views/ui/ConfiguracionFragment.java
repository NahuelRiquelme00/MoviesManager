package com.example.moviesmanager.views.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesmanager.R;
import com.example.moviesmanager.databinding.FragmentConfiguracionBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfiguracionFragment extends Fragment {

    //Binding
    private FragmentConfiguracionBinding binding;

    //Usuario
    private TextView textViewUsuario;
    private String nuevoUsuario;

    //Correo
    private TextView textViewCorreo;
    private String nuevoCorreo;

    //Foto de perfil
    private CircleImageView circleImageViewFotoPerfil;
    private Uri imageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //ConfiguracionViewModel configuracionViewModel =
        //        new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ConfiguracionViewModel.class);

        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Usuario
        textViewUsuario = binding.textViewConfigUsuario;
        textViewUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertaUsuario = new AlertDialog.Builder(getContext());
                //Layout del AlertDialog
                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                View viewUsuario = inflater2.inflate(R.layout.alert_dialog_config_usuario, null);
                final EditText etUsuario = (EditText)viewUsuario.findViewById(R.id.editTextUsuarioNombre);
                final Button btCancelar = (Button)viewUsuario.findViewById(R.id.buttonADUsuarioCancelar);
                final Button btAceptar = (Button)viewUsuario.findViewById(R.id.buttonADUsuarioAceptar);
                etUsuario.setText(textViewUsuario.getText());
                alertaUsuario.setView(viewUsuario);
                AlertDialog dialogUsuario = alertaUsuario.create();
                dialogUsuario.show();
                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUsuario.cancel();
                    }
                });
                btAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etUsuario.getText().toString().equals("")){
                            // --- MODIFICAR PARA QUE SE MUESTRE EL USUARIO ACTUAL Y SE LO PUEDA MODIFICAR ---
                            Toast.makeText(getContext(), "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            nuevoUsuario = etUsuario.getText().toString();
                            textViewUsuario.setText(etUsuario.getText());
                            dialogUsuario.dismiss();
                            Toast.makeText(getContext(), "Nuevo nombre: "+nuevoUsuario, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //Correo
        textViewCorreo = binding.textViewConfigCorreo;
        textViewCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertaCorreo = new AlertDialog.Builder(getContext());
                //Layout del AlertDialog
                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                View viewCorreo = inflater2.inflate(R.layout.alert_dialog_config_correo, null);
                final EditText etCorreo = (EditText)viewCorreo.findViewById(R.id.editTextCorreoDireccion);
                final Button btCancelar = (Button)viewCorreo.findViewById(R.id.buttonADCorreoCancelar);
                final Button btAceptar = (Button)viewCorreo.findViewById(R.id.buttonADCorreoAceptar);
                etCorreo.setText(textViewCorreo.getText());
                alertaCorreo.setView(viewCorreo);
                AlertDialog dialogCorreo = alertaCorreo.create();
                dialogCorreo.show();
                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCorreo.cancel();
                    }
                });
                btAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etCorreo.getText().toString().equals("")){
                            // --- MODIFICAR PARA QUE SE MUESTRE EL CORREO ACTUAL Y SE LO PUEDA MODIFICAR, Y HACER UNA VALIDACION DEL MISMO ---
                            Toast.makeText(getContext(), "Ingrese una direcci\u00f3n de correo", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            textViewCorreo.setText(etCorreo.getText());
                            nuevoCorreo = etCorreo.getText().toString();
                            dialogCorreo.dismiss();
                            Toast.makeText(getContext(), "Nuevo correo: "+nuevoCorreo, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Foto de Perfil
        circleImageViewFotoPerfil = binding.circleImageViewConfigFotoPerfil;
        circleImageViewFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Permisos
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        AlertDialog.Builder alertaPermisoStorage = new AlertDialog.Builder(getContext());
                        alertaPermisoStorage.setTitle(R.string.ad_ConfigPermisoGaleriaTitulo)
                                .setMessage(R.string.ad_ConfigPermisoGaleriaMensaje)
                                .setNegativeButton(R.string.ad_ConfigDenegar, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setPositiveButton(R.string.ad_ConfigConceder, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        permissionLauncherSingle.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                    }
                                });
                        AlertDialog dialogPermisoStorage = alertaPermisoStorage.create();
                        dialogPermisoStorage.show();
                    }
                    else{
                        cargarImagen();
                    }
                }
            }
        });
        return root;
    }

    private ActivityResultLauncher<String> permissionLauncherSingle = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if(result){
                        //Toast.makeText(getContext(), "Permiso concedido", Toast.LENGTH_SHORT).show();
                        cargarImagen();
                    } else{
                        Toast.makeText(getContext(), "Permiso no concedido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void cargarImagen() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchCargarImagen.launch(i);
    }

    ActivityResultLauncher<Intent> launchCargarImagen = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        try {
                            if (result.getData() != null){
                                imageUri = result.getData().getData();
                                circleImageViewFotoPerfil.setImageURI(imageUri);
                            }
                        }catch (Exception exception){
                            exception.printStackTrace();
                            Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}