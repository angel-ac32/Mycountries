package com.l20291033.mycountries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.l20291033.mycountries.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Definar la vista atravez del objeto con la vista
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        // Enlazar los elementos
        BottomNavigationView navView = viewBinding.mainBnvNavView;

        // Pasar los ids a un configurador de barras para habilitar la navegacion

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mainNavMiContinente,
                R.id.mainNavMiBuscar,
                R.id.mainNavMiPerfil
        ).build();

        // crear un controlador para habilitar la navegacion con un elemento (host)
        NavController navController = Navigation.findNavController(this, R.id.mainFragmentNavHost);

        // Enlazar los controles para que la intefaz cambie la vista conforme se selecciona
        NavigationUI.setupWithNavController(navView, navController);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
}