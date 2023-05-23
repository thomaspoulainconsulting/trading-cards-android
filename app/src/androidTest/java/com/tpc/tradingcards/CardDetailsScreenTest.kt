package com.tpc.tradingcards

import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardEmpty
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.ui.cards.screen.CardDetailsScreen
import org.junit.Rule
import org.junit.Test

class CardDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState() {
        // Given
        var numberOfCardText = ""
        val cardSetName = "test"
        val cardSet = CardSetEmpty.copy(name = cardSetName)
        val cards: List<Card> = listOf(CardEmpty)

        // When
        composeTestRule.setContent {
            numberOfCardText =
                pluralStringResource(R.plurals.number_or_cards, count = cards.size, cards.size)
            TradingCardsTheme {
                CardDetailsScreen(cardSet = cardSet, cards = cards) {}
            }
        }

        // Then
        composeTestRule.onNodeWithText(cardSetName, useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(numberOfCardText).assertIsDisplayed()
    }
}