package com.example.moviesmanager.views.ui;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;

import com.example.moviesmanager.database.ConsultarDB;
import com.example.moviesmanager.utils.AlarmNotification;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesmanager.R;
import com.example.moviesmanager.databinding.ActivityMainBinding;
import com.example.moviesmanager.database.DataConverter;
import com.example.moviesmanager.models.Usuario;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public NavController navController;
    public static String MY_CHANNEL_ID = "MovieNotification";

    private View headerView;
    private CircleImageView fotoPerfilUsuario;
    private TextView nombreUsuario;
    private TextView correoUsuario;
    private byte[] foto;

    private ConsultarDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = ConsultarDB.getInstance(this.getApplicationContext());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_busqueda, R.id.nav_favoritas,R.id.nav_recomendaciones,
                R.id.nav_ver_mas_tarde, R.id.nav_ya_vistas, R.id.nav_configuracion)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        crearCanal();
        programarNotificacion();

        /*Cargar datos de usuario: como solo se guarda informacion de un unico usuario (el que tiene la app en el telefono), solo va a
        existir un usuario. La idea no es que el usuario inicia sesion desde cualquier dispositivo que tenga la app, sino que sea una
        app personal que solo se usa en un dispositivo.
        */
        if(db.daoUsuario().existsById(1)){
            //Cargar datos
            headerView = navigationView.getHeaderView(0);
            if(!db.daoUsuario().usuarioNoCargado(1)){
                nombreUsuario = headerView.findViewById(R.id.textViewNavHeaderUsuario);
                nombreUsuario.setText(db.daoUsuario().obtenerUsuario(1));
            }
            if(!db.daoUsuario().correoNoCargado(1)){
                correoUsuario = headerView.findViewById(R.id.textViewNavHeaderCorreo);
                correoUsuario.setText(db.daoUsuario().obtenerCorreo(1));
            }
            if(!db.daoUsuario().fotoNoCargada(1)){
                fotoPerfilUsuario = headerView.findViewById(R.id.circleImageViewNavHeader);
                foto = db.daoUsuario().obtenerFotoPerfilPath(1);
                DataConverter dataConverter = new DataConverter();
                fotoPerfilUsuario.setImageBitmap(dataConverter.convertByteArrayToBitmap(foto));
            }
        } else{
            //Crear usuario
            try{
                db.daoUsuario().insertarUsuario(new Usuario(1));
            } catch (Exception e){
                Toast.makeText(this, "Error al crear el usuario: "+e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void botonBusqueda(MenuItem item) {
        navController.navigate(R.id.nav_busqueda);
    }

    private void crearCanal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,"Peliculas", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Esta es la descripcion");

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void programarNotificacion() {

        Intent intent = new Intent(getApplicationContext(), AlarmNotification.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), AlarmNotification.NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        //Para setear una hora
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Con la hora seteada
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);

        //Unos segundos despues de que inicia la aplicación
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 20000 , pendingIntent);
    }

}