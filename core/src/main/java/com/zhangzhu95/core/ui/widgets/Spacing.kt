package com.zhangzhu95.core.ui.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressWarnings("MemberNameEqualsClassName")
class Spacing {

    object Vertical : ComposeSpacing {
        @Composable
        override fun Bigger() = Vertical(32)

        @Composable
        override fun Big() = Vertical(28)

        @Composable
        override fun Medium() = Vertical(16)

        @Composable
        override fun Small() = Vertical(12)

        @Composable
        override fun Tiny() = Vertical(4)

        @Composable
        private fun Vertical(value: Int) = Spacer(modifier = Modifier.padding(vertical = value.dp))
    }

    object Horizontal : ComposeSpacing {
        @Composable
        override fun Bigger() {
            Horizontal(30)
        }

        @Composable
        override fun Big() {
            Horizontal(28)
        }

        @Composable
        override fun Medium() {
            Horizontal(16)
        }

        @Composable
        override fun Small() {
            Horizontal(12)
        }

        @Composable
        override fun Tiny() {
            Horizontal(4)
        }

        @Composable
        fun Horizontal(value: Int) = Spacer(modifier = Modifier.padding(vertical = value.dp))
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
