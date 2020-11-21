package calculator

import dagger.Component

@Component
interface CalculatorComponent {
    fun getCalculator(): Calculator
}