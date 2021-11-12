package com.example.a02crudcomroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.a02crudcomroom.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val binding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //para recuperar o navcontroller primeiro buscamos o nav_host_fragment (o FragmentContainer é um fragmento de fato)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //a partir do NavHostFragment conseguimos o navController. É assim que conseguimos o navController na activity
        val navController = navHostFragment.navController
        //com essa configuração o appBar tem conhecimento do gráfico de navegação
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //e com o id do toolBar 'appToolbar' chamamos a extensions setupWithNavController e passamos os parametros abaixo para
        //que assim a toolbar possa reagir a navegação
        app_toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}