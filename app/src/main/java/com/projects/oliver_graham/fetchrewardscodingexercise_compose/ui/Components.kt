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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item


@SuppressLint("UnusedTransitionTargetStateParameter")
@ExperimentalAnimationApi
@Composable
fun ExpandableRow(
    listId: Int,
    itemList: List<Item>
) {
    // expanded toggles between true and false, triggering all the animations
    val expanded = remember { mutableStateOf(false) }
    val animationDuration = 1000

    val transitionState = remember {
        MutableTransitionState(expanded.value).apply { ->
            targetState = !expanded.value
        }
    }
    val transition = updateTransition(
        targetState = transitionState,
        label = "expandable transition"
    )

    // set parameters for expand animations
    // all animations happen over one second
    val backgroundColor by transition.animateColor({ ->
        tween(durationMillis = animationDuration)
    }, label = "animate background color") {
        if (expanded.value)
            MaterialTheme.colors.secondary
        else
            MaterialTheme.colors.primary
    }

    val textColor by transition.animateColor({ ->
        tween(durationMillis = animationDuration)
    }, label = "animate text color") {
        if (expanded.value)
            MaterialTheme.colors.primary
        else
            MaterialTheme.colors.onPrimary
    }

    val rowPaddingHorizontal by transition.animateDp({ ->
        tween(durationMillis = animationDuration)
    }, label = "row padding") {
        if (expanded.value)
            8.dp
        else
            12.dp
    }
    val cardElevation by getSomething(transition = transition, expanded = expanded)
    /*val cardElevation by transition.animateDp({ ->
        tween(durationMillis = animationDuration)
    }, label = "") {
        if (expanded.value)
            24.dp
        else
            4.dp
    }*/

    val rowRoundedCorners by transition.animateDp({ ->
        tween(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (expanded.value)
            8.dp
        else
            16.dp
    }

    // animate content with the animation vals defined above
    Card(
        backgroundColor  = backgroundColor,
        contentColor = MaterialTheme.colors.primary,
        elevation = cardElevation,
        shape = RoundedCornerShape(rowRoundedCorners),
        modifier = Modifier
            .fillMaxWidth(fraction = 0.75f)
            .padding(horizontal = rowPaddingHorizontal)
            .alpha(alpha = .9f)
    ) {

        Column(Modifier.clickable(onClick = { expanded.value = !expanded.value })) { ->
            CenteredContentRow { ->
                LargeText(
                    text = "ListID: $listId",
                    color = textColor,
                    textAlign = TextAlign.Center
                )

            }
            ExpandableContent(
                visible = expanded.value,
                initialVisibility = expanded.value,
                itemList = itemList
            )
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun getSomething(
    transition: Transition<MutableTransitionState<Boolean>>,
    expanded: MutableState<Boolean>,
    animationDuration: Int = 1000,
    expandedDp: Dp = 24.dp,
    unexpandedDp: Dp = 4.dp,
): State<Dp> {
   return transition.animateDp({ ->
        tween(durationMillis = animationDuration)
    }, label = "") {
        if (expanded.value)
            expandedDp
        else
            unexpandedDp
    }
}

@Composable
fun LargeText(
    text: String,
    fontFamily: FontFamily? = null,
    fontSize: TextUnit = 22.sp,
    fontWeight: FontWeight? = null,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        fontFamily = fontFamily,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign
    )
}

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

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    itemList: List<Item>
) {
    val animationDuration = 500
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
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(8.dp)) { ->
            itemList.forEach { item ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) { ->
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = "ID: ${item.id}",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = item.listId.toString(),
                        textAlign = TextAlign.Center
                    )
                    item.name?.let {
                        Text(
                            text = it,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}