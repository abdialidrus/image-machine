package com.example.imagemachine.presentation.machines.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.imagemachine.presentation.ui.theme.Typography

@Composable
fun EmptyListMessageSection(
    modifier: Modifier = Modifier,
    onAddMachineButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Keep forgetting your tools/machines?", style = Typography.h6, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Image Machine is here to save your day!", style = Typography.subtitle1, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = {
                onAddMachineButtonClicked()
            }
        ) {
            Text(text = "Add Machine Data")
        }
    }
}