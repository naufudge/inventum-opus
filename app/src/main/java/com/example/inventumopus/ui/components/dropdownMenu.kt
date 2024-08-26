package com.example.inventumopus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.R
import com.example.inventumopus.ui.screens.poppins


@Composable
fun CustomDropdownMenu(
    viewModel: HomeViewModel
) {
    val options = listOf("Sign Out")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Box (
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .clickable { expanded = true }
                .padding(8.dp),
            painter = painterResource(id = R.drawable.more_vert),
            contentDescription = "more"
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            text = option,
                            fontFamily = poppins,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red
                        )
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        viewModel.userSignOut()
                    }
                )
            }
        }
    }
}
