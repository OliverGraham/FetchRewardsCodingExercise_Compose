package com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item

/**** Public Functions *****/

/**
 *  Compose a Row() with its content aligned vertically and horizontally
 */
@Composable
fun CenteredContentRow(
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceEvenly,
    rowContent: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        content = rowContent
    )
}

/**
 *  Given a label (listId) and list content (itemList),
 *  this composable creates a Card that can be clicked (pressed)
 *  to toggle dropdown content
 */
@SuppressLint("UnusedTransitionTargetStateParameter")
@ExperimentalAnimationApi
@Composable
fun ExpandableCard(
    listId: Int,
    itemList: List<Item>
) {
    // expanded toggles between true and false, triggering all animations
    val expanded = remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(expanded.value).apply { ->
            targetState = !expanded.value
        }
    }

    // provides the state for the animations;
    // goes between currentState and targetState
    val transition = updateTransition(
        targetState = transitionState,
        label = "expandable transition"
    )

    val colors = MaterialTheme.colors

    // create arguments for color animations
    val backgroundColor by animateColor(transition, expanded)
    val textColor by animateColor(transition, expanded,
        expandedColor = colors.primary, unexpandedColor = colors.onPrimary)

    // create arguments for expansion animations
    val cardElevation by animateDp(transition, expanded)
    val rowPaddingHorizontal by animateDp(transition, expanded,
        expandedDp = 8.dp, unexpandedDp = 12.dp)

    val rowRoundedCorners by animateDp(transition, expanded,
        expandedDp = 8.dp, unexpandedDp = 16.dp)

    // animate content using the animation vals defined above
    Card(
        backgroundColor  = backgroundColor,
        contentColor = colors.primary,
        elevation = cardElevation,
        shape = RoundedCornerShape(rowRoundedCorners),
        modifier = Modifier
            .fillMaxWidth(fraction = 0.75f)
            .padding(horizontal = rowPaddingHorizontal)
    ) {
        ExpandableCardContent(
            listId = listId,
            itemList = itemList,
            textColor = textColor,
            expanded = expanded
        )
    }
}

/**** Private Functions *****/

/**
 *  Return a toggleable, animated color transition property
 */
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun animateColor(
    transition: Transition<MutableTransitionState<Boolean>>,
    expanded: MutableState<Boolean>,
    animationDuration: Int = 1000,
    label: String = "animate color",
    expandedColor: Color = MaterialTheme.colors.secondary,
    unexpandedColor: Color = MaterialTheme.colors.primary
) : State<Color> {
    return transition.animateColor({ ->
        tween(durationMillis = animationDuration) },
        label = label) {
        if (expanded.value)
            expandedColor
        else
            unexpandedColor
    }
}

/**
 *  Return a toggleable, animated size transition property
 */
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun animateDp(
    transition: Transition<MutableTransitionState<Boolean>>,
    expanded: MutableState<Boolean>,
    animationDuration: Int = 1000,
    label: String = "animate dp",
    expandedDp: Dp = 24.dp,
    unexpandedDp: Dp = 4.dp,
): State<Dp> {
    return transition.animateDp({ ->
        tween(durationMillis = animationDuration) },
        label = label) {
        if (expanded.value)
            expandedDp
        else
            unexpandedDp
    }
}
/**
 *  Displays a column of rows of list items when expanded
 *  Can be toggled between expanded and contracted
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandableCardContent(
    listId: Int,
    itemList: List<Item>,
    textColor: Color,
    expanded: MutableState<Boolean>,
) {
    // when card is clicked, toggle between expanded or contracted
    Column(Modifier.clickable(onClick = { expanded.value = !expanded.value })) { ->
        CenteredContentRow { ->
            Text(
                text = "ListID: $listId",
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )
        }
        // when the card is expanded, this content becomes visible
        ExpandableContent(
            visible = expanded.value,
            initialVisibility = expanded.value,
            itemList = itemList
        )
    }
}

/**
 *  Prepare the animation properties for the dropdown content
 */
@ExperimentalAnimationApi
@Composable
private fun ExpandableContent(
    itemList: List<Item>,
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    animationDuration: Int = 500
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = animationDuration,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(animationDuration))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = animationDuration,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(animationDuration))
    }
    AnimatedDropdown(
        itemList = itemList,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut,
        visible = visible,
        initialVisibility = initialVisibility
    )
}

/**
 *  Show the dropdown content given the content and animation properties
 */
@ExperimentalAnimationApi
@Composable
private fun AnimatedDropdown(
    itemList: List<Item>,
    enter: EnterTransition,
    exit: ExitTransition,
    visible: Boolean,
    initialVisibility: Boolean,
) {
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enter,
        exit = exit
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { ->

            // headers for item properties
            CenteredContentRow(padding = 4.dp) { ->
                TextPerLabel(
                    labels = listOf("ID", "ListID", "Name"),
                    fontSize = 20.sp,
                )
            }

            // compose a row consisting of each property for every item in list
            itemList.forEach { item ->
                CenteredContentRow(padding = 4.dp) { ->
                    TextPerLabel(
                        labels = listOf("${item.id}", "${item.listId}", "${item.name}")
                    )
                }
            }
        }
    }
}

/**
 *  Compose a Text() for every string in the given list
 */
@Composable
private fun TextPerLabel(
    labels: List<String>,
    fontSize: TextUnit = 18.sp,
    textAlignment: TextAlign = TextAlign.Left,
) {
    labels.forEach { label ->
        Text(
            text = label,
            textAlign = textAlignment,
            fontSize = fontSize
        )
    }
}