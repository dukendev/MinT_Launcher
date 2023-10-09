package com.dukendev.mintlauncher.presentation.nav

sealed class Routes(val name : String) {
    object Root : Routes("root")
    object Home : Routes("home")
    object Apps : Routes("apps")

}