package ru.cityron.presentation.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class IPAddressVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val inputText = text.text
        val formattedText = buildString {
            for (i in inputText.indices) {
                append(inputText[i])
                if ((i + 1) % 3 == 0 && i != inputText.length - 1 && countDots(this) < 3) {
                    append('.')
                }
            }
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = offset
                for (i in 1..offset) {
                    if (i % 3 == 0 && i != offset) {
                        transformedOffset++
                    }
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = offset
                for (i in 1..offset) {
                    if (i % 4 == 0 && i != offset) {
                        originalOffset--
                    }
                }
                return originalOffset
            }
        }
        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }

    private fun countDots(s: StringBuilder): Int {
        return s.count { it == '.' }
    }
}