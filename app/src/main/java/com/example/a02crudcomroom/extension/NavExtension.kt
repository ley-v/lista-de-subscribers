package com.example.a02crudcomroom.extension

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.a02crudcomroom.R

val slideLeftOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

//configurado os efeitos de animação, iremos usar esta função para navegar e não mais diretamente o nosso 'navigate'
fun NavController.navigateWithAnimations(
    destinationId: Int,
    animation: NavOptions = slideLeftOptions
) {
    this.navigate(destinationId, null, animation)
}