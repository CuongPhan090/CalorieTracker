package com.example.core.domain.usecase

class FilterOutDigit {
    operator fun invoke(input: String): String {
        return input.filter { it.isDigit() }
    }
}