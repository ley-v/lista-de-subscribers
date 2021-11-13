package com.example.a02crudcomroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    //    private val binding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //definir a action bar de suporte como a toolbar criada
        setSupportActionBar(app_toolbar)

        //para recuperar o navcontroller primeiro buscamos o nav_host_fragment (o FragmentContainer é um fragmento de fato)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //a partir do NavHostFragment conseguimos o navController. É assim que conseguimos o navController na activity
        navController = navHostFragment.navController
        //com essa configuração o appBar tem conhecimento do gráfico de navegação
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //e com o id do toolBar 'appToolbar' chamamos a extensions setupWithNavController e passamos os parametros abaixo para
        //que assim a toolbar possa reagir a navegação
//        app_toolbar.setupWithNavController(navController, appBarConfiguration)
        //função acima substituída por. essa nova função permite que trabalhemos melhor com o ícone de voltar(arrow back). Quando
        //utilizamos essa função, porém , precisamos sobrescrever o método 'onSupportedNavigateUp'
        setupActionBarWithNavController(navController, appBarConfiguration)

        //para conseguir trocar o ícone de cada um dos fragments adicionaremos um listener
        //esse listener será chamado toda vez que acontecer uma navegação
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //O supportActionBar está referênciando a toolbar(conforme foi definido acima)
            //Toda vez que acontecer a mudança de um fragment, vai cair dentro desse listener e esse código setta para aquela
            // tela que o ícone será a seta branca
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
            //é com esse listener que conseguimos alterar e ter mais controle sob cada um dos fragments que estão sendo exibidos
            // em determinados momentos
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //dessa forma conseguimos configurar corretamente o ícone do arrow back
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}