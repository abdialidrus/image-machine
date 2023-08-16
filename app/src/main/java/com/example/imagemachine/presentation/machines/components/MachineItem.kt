package com.example.imagemachine.presentation.machines.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.presentation.ui.theme.Typography

@Composable
fun MachineItem(
    machine: Machine,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.
        padding(vertical = 12.dp)
    ) {
        Text(
            text = machine.name,
            style = Typography.h5,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Machine type: ${machine.type}", style = Typography.subtitle1, color = Color.DarkGray)
    }
}