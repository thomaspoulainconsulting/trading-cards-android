package com.tpc.tradingcards

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tpc.tradingcards.core.ui.UIState
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.ui.cards.CardListScreen
import com.tpc.tradingcards.ui.cards.CardListTestTag
import org.junit.Rule
import org.junit.Test

class CardListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `loading state`() {
        // Given
        val cardSets: UIState<List<CardSet>> = UIState.Loading()

        // When
        composeTestRule.setContent {
            TradingCardsTheme {
                CardListScreen(cardSets) {}
            }
        }

        // Then
        composeTestRule.onNodeWithTag(CardListTestTag.Loading.tag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CardListTestTag.Error.tag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(CardListTestTag.Data.tag).assertDoesNotExist()
    }

    @Test
    fun `error state`() {
        // Given
        val cardSets: UIState<List<CardSet>> = UIState.Error()

        // When
        composeTestRule.setContent {
            TradingCardsTheme {
                CardListScreen(cardSets) {}
            }
        }

        // Then
        composeTestRule.onNodeWithTag(CardListTestTag.Error.tag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CardListTestTag.Loading.tag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(CardListTestTag.Data.tag).assertDoesNotExist()
    }

    @Test
    fun `success state`() {
        // Given
        lateinit var selectedCard: CardSet
        val cardName = "test"
        val cardSets: UIState<List<CardSet>> = UIState.Success(
            listOf(CardSetEmpty.copy(name = cardName))
        )

        // When
        composeTestRule.setContent {
            TradingCardsTheme {
                CardListScreen(cardSets) {
                    selectedCard = it
                }
            }
        }

        // Then
        composeTestRule.onNodeWithTag(CardListTestTag.Data.tag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CardListTestTag.Loading.tag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(CardListTestTag.Error.tag).assertDoesNotExist()
        composeTestRule.onNodeWithText(cardName).performClick()
        assert(selectedCard.name == cardName)
    }

}