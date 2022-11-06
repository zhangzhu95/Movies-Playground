package com.zhangzhu95.compose.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Spacing {

    object Vertical : ComposeSpacing {
        @Composable
        override fun Bigger() = VerticalSpacing(32)

        @Composable
        override fun Big() = VerticalSpacing(28)

        @Composable
        override fun Medium() = VerticalSpacing(16)

        @Composable
        override fun Small() = VerticalSpacing(12)

        @Composable
        override fun Tiny() = VerticalSpacing(4)

        @Composable
        private fun VerticalSpacing(value: Int) = Spacer(modifier = Modifier.padding(vertical = value.dp))
    }

    object Horizontal : ComposeSpacing {
        @Composable
        override fun Bigger() {
            HorizontalSpacing(30)
        }

        @Composable
        override fun Big() {
            HorizontalSpacing(28)
        }

        @Composable
        override fun Medium() {
            HorizontalSpacing(16)
        }

        @Composable
        override fun Small() {
            HorizontalSpacing(12)
        }

        @Composable
        override fun Tiny() {
            HorizontalSpacing(4)
        }

        @Composable
        fun HorizontalSpacing(value: Int) = Spacer(modifier = Modifier.padding(horizontal = value.dp))
    }
}

interface ComposeSpacing {
    @Composable
    fun Bigger()

    @Composable
    fun Big()

    @Composable
    fun Medium()

    @Composable
    fun Small()

    @Composable
    fun Tiny()
}
