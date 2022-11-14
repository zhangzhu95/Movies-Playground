package com.zhangzhu95.compose.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhangzhu95.compose.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    @StringRes hint: Int? = null,
    onValueChange: (String) -> Unit = {},
    editable: Boolean = true,
    onTouch: (() -> Unit)? = null,
    onSearch: (() -> Unit)? = null,
    focused: Boolean = false,
    customSection: (@Composable () -> Unit)? = null
) {
    val focusRequester = remember { FocusRequester() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onTouch?.invoke()
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
    ) {

        LaunchedEffect(Unit) {
            if (focused) {
                focusRequester.requestFocus()
            }
        }

        Row(
            modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "Search"
            )

            Spacing.Horizontal.Tiny()

            Box {
                // Display the text field
                BasicTextField(
                    value = value,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearch?.invoke() }),
                    enabled = onTouch == null,
                    onValueChange = {
                        if (editable) {
                            onValueChange(it)
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    singleLine = true,
                    maxLines = 1
                )

                // Display the placeholder / hint
                if (value.isEmpty() && hint != null) {
                    Text(text = stringResource(id = hint), color = Color.Gray)
                }
            }

            customSection?.invoke()
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(value = "Value")
}

@Preview
@Composable
private fun SearchBarWithHintPreview() {
    SearchBar(hint = R.string.placeholder_title)
}
