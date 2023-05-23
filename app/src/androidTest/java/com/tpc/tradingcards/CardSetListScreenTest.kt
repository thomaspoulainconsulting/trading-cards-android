package com.tpc.tradingcards

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.ui.cards.screen.CardSetsListScreen
import com.tpc.tradingcards.ui.cards.testtag.CardListTestTag
import org.junit.Rule
import org.junit.Test

class CardSetListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState() {
        // Given
        val cardSets: List<CardSet> = emptyList()

        // When
        composeTestRule.setContent {
            TradingCardsTheme {
                CardSetsListScreen(cardSets) {}
            }
        }

        // Then
        composeTestRule.onNodeWithTag(CardListTestTag.Loading.tag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CardListTestTag.Data.tag).assertDoesNotExist()
    }

    @Test
    fun dataLoadedState() {
        // Given
        lateinit var selectedCard: CardSet
        val cardName = "test"
        val cardSets: List<CardSet> = listOf(CardSetEmpty.copy(name = cardName))

        // When
        composeTestRule.setContent {
            TradingCardsTheme {
                CardSetsListScreen(cardSets) {
                    selectedCard = it
                }
            }
        }

        // Then
        composeTestRule.onNodeWithTag(CardListTestTag.Data.tag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CardListTestTag.Loading.tag).assertDoesNotExist()
        composeTestRule.onNodeWithText(cardName, useUnmergedTree = true).performClick()
        assert(selectedCard.name == cardName)
    }

}