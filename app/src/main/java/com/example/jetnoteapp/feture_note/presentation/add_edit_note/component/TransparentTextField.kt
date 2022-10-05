package com.example.jetnoteapp.feture_note.presentation.add_edit_note.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    text : String,
    hint : String,
    isHintVisible : Boolean = true,
    onTextChange : (String) -> Unit,
    onFocusChange : (FocusState) ->Unit,
    textStyle: TextStyle = TextStyle()
) {
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.onFocusChanged { onFocusChange(it) }
        )
        if(isHintVisible)
            Text(text = hint, style = textStyle)
    }
    
    
    
}