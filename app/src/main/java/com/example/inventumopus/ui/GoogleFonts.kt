package com.example.inventumopus.ui

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.inventumopus.R

class GoogleFonts {
    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val Poppins = FontFamily(
        Font(googleFont = GoogleFont("Poppins"), fontProvider = provider)
    )

    val Raleway = FontFamily(
        Font(googleFont = GoogleFont("Raleway"), fontProvider = provider)
    )

    val Prata = FontFamily(
        Font(googleFont = GoogleFont("Prata"), fontProvider = provider)
    )

}