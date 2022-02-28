package com.otero.recipetoshop.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
/*
Esta clase es necesaria para que Hilt pueda hacer su trabajo (requerimiento de Hilt).
 */
@HiltAndroidApp
class BaseApplication: Application()