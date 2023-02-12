package com.example.moviesmanager.views.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.moviesmanager.R;
import com.example.moviesmanager.database.ConsultarDB;
import com.example.moviesmanager.database.DataConverter;
import com.example.moviesmanager.databinding.FragmentConfiguracionBinding;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfiguracionFragment extends Fragment {

    private FragmentConfiguracionBinding binding;
    private TextView textViewUsuario;
    private String nuevoUsuario;
    private TextView textViewCorreo;
    private String nuevoCorreo;
    private CircleImageView circleImageViewFotoPerfil;
    private Uri imageUri;
    private ConsultarDB db;
    NavigationView navigationView;
    private View headerView;
    private TextView nombreUsuario;
    private TextView correoUsuario;
    private CircleImageView fotoPerfilUsuario;
    private byte[] foto;
    private Bitmap bitmapFoto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //ConfiguracionViewModel configuracionViewModel =
        //        new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ConfiguracionViewModel.class);

        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        textViewUsuario = binding.textViewConfigUsuario;
        textViewCorreo = binding.textViewConfigCorreo;
        circleImageViewFotoPerfil = binding.circleImageViewConfigFotoPerfil;
        View root = binding.getRoot();

        //Para actualizar el header
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);

        //Base de datos
        try {
            db = ConsultarDB.getInstance(getContext().getApplicationContext());
        }catch (Exception ex){
            Toast.makeText(getContext(), "Error al consultar db " + ex, Toast.LENGTH_SHORT).show();
        }

        if(!db.daoUsuario().usuarioNoCargado(1)){
            textViewUsuario.setText(db.daoUsuario().obtenerUsuario(1));
        }
        if(!db.daoUsuario().correoNoCargado(1)){
            textViewCorreo.setText(db.daoUsuario().obtenerCorreo(1));
        }
        if(!db.daoUsuario().fotoNoCargada(1)){
            foto = db.daoUsuario().obtenerFotoPerfilPath(1);
            DataConverter dataConverter = new DataConverter();
            circleImageViewFotoPerfil.setImageBitmap(dataConverter.convertByteArrayToBitmap(foto));
        }

        //Usuario
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
                nombreUsuario = headerView.findViewById(R.id.textViewNavHeaderUsuario);
                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUsuario.cancel();
                    }
                });
                btAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nuevoUsuario = etUsuario.getText().toString();
                        if(nuevoUsuario.equals("")){
                            Toast.makeText(getContext(), "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            textViewUsuario.setText(etUsuario.getText());
                            try {
                                db.daoUsuario().actualizarNombre(1, nuevoUsuario);
                            } catch (Exception e){
                                Toast.makeText(getContext(), "Error al actualizar usuario: "+e, Toast.LENGTH_SHORT).show();
                            }
                            nombreUsuario.setText(nuevoUsuario);
                            dialogUsuario.dismiss();
                        }
                    }
                });
            }
        });

        //Correo
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
                correoUsuario = headerView.findViewById(R.id.textViewNavHeaderCorreo);
                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCorreo.cancel();
                    }
                });
                btAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nuevoCorreo = etCorreo.getText().toString();
                        if(nuevoCorreo.equals("")){
                            Toast.makeText(getContext(), "Ingrese una direcci\u00f3n de correo", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            textViewCorreo.setText(etCorreo.getText());
                            try {
                                db.daoUsuario().actualizarCorreo(1, nuevoCorreo);
                            } catch (Exception e){
                                Toast.makeText(getContext(), "Error al actualizar correo: "+e, Toast.LENGTH_SHORT).show();
                            }
                            correoUsuario.setText(nuevoCorreo);
                            dialogCorreo.dismiss();
                        }
                    }
                });
            }
        });

        //Foto de Perfil
        circleImageViewFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Permisos
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        AlertDialog.Builder alertaPermisoStorage = new AlertDialog.Builder(getContext());
                        //Layout del AlertDialog
                        LayoutInflater inflater3 = getActivity().getLayoutInflater();
                        View viewPermisos = inflater3.inflate(R.layout.alert_dialog_config_permisos, null);
                        final Button btIgnorar = (Button) viewPermisos.findViewById(R.id.buttonADPermisosIgnorar);
                        final Button btConceder = (Button) viewPermisos.findViewById(R.id.buttonADPermisosConceder);
                        alertaPermisoStorage.setView(viewPermisos);
                        AlertDialog dialogPermisoStorage = alertaPermisoStorage.create();
                        dialogPermisoStorage.show();
                        btIgnorar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogPermisoStorage.cancel();
                            }
                        });
                        btConceder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogPermisoStorage.dismiss();
                                permissionLauncherSingle.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                            }
                        });
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
                                bitmapFoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                                circleImageViewFotoPerfil.setImageBitmap(bitmapFoto);
                                DataConverter dataConverter = new DataConverter();
                                try{
                                    db.daoUsuario().actualizarFoto(1,dataConverter.convertImageToByteArray(bitmapFoto));
                                } catch (Exception e){
                                    Toast.makeText(getContext(), "Error al guardar la foto: "+e, Toast.LENGTH_SHORT).show();
                                }
                                fotoPerfilUsuario = headerView.findViewById(R.id.circleImageViewNavHeader);
                                fotoPerfilUsuario.setImageBitmap(bitmapFoto);
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