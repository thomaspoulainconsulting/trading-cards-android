package com.tpc.tradingcards

import androidx.compose.ui.test.junit4.createComposeRule
import com.tpc.tradingcards.core.ui.UIState
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.ui.cards.CardDetailsScreen
import org.junit.Rule
import org.junit.Test

class CardDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `loading state`() {
        // Given
        val cardSetName = "test"
        val cardSet = CardSetEmpty.copy(name = cardSetName)
        val cards: UIState<List<Card>> = UIState.Loading()

        // When
        composeTestRule.setContent {
            TradingCardsTheme {
                CardDetailsScreen(cardSet = cardSet, cards = cards) {

                }
            }
        }

        // Then
    }
}