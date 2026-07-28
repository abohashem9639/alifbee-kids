package com.alifbee.kidsdemo.ui.screens.matchpairs.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.MatchAnswer
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.components.DashedAnswerSlot
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.components.GhostSlot
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.components.MatchCardFace
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.viewmodel.MatchPairsContract
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
internal fun MatchPairsScreen(
    state: MatchPairsContract.State,
    effectFlow: Flow<MatchPairsContract.Effect>,
    onEventSent: (event: MatchPairsContract.Event) -> Unit,
    onCloseClicked: () -> Unit,
) {
    effectFlow.CollectEffect { }

    MatchPairsContent(
        state = state,
        onEventSent = onEventSent,
        onCloseClicked = onCloseClicked,
    )
}

@Composable
private fun MatchPairsContent(
    state: MatchPairsContract.State,
    onEventSent: (event: MatchPairsContract.Event) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.cd_close),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onCloseClicked),
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f)
                        .background(LocalCustomColors.current.statusTodo, RoundedCornerShape(5.dp)),
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.fetchState.loading -> CircularProgressIndicator(
                    color = LocalCustomColors.current.statusTodo,
                    modifier = Modifier.align(Alignment.Center),
                )

                state.fetchState.error != null -> Text(
                    text = stringResource(R.string.syllabus_error),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                )

                else -> MatchExercise(state = state, onEventSent = onEventSent)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "!",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            val checkEnabled = state.allPlaced && state.result == null
            Button(
                onClick = { onEventSent(MatchPairsContract.Event.OnCheckClicked) },
                enabled = checkEnabled,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalCustomColors.current.statusTodo,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
            ) {
                Text(
                    text = stringResource(R.string.check),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun MatchExercise(
    state: MatchPairsContract.State,
    onEventSent: (event: MatchPairsContract.Event) -> Unit,
) {
    var rootOrigin by remember { mutableStateOf(Offset.Zero) }
    val homeAbsolute = remember { mutableStateMapOf<Int, Rect>() }
    val slotAbsolute = remember { mutableStateMapOf<Int, Rect>() }
    val homeRects = homeAbsolute.mapValues { Rect(it.value.topLeft - rootOrigin, it.value.size) }
    val slotRects = slotAbsolute.mapValues { Rect(it.value.topLeft - rootOrigin, it.value.size) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val slotHeight = ((maxHeight - 200.dp) / 6).coerceIn(56.dp, 110.dp)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { rootOrigin = it.positionInRoot() },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = state.questionText,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        state.cardsA.forEachIndexed { index, card ->
                            GhostSlot(
                                text = card.text,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(slotHeight)
                                    .onGloballyPositioned {
                                        homeAbsolute[index] = Rect(it.positionInRoot(), it.size.toSize())
                                    },
                            )
                        }
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(vertical = 20.dp),
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        state.slotsB.forEachIndexed { index, slot ->
                            DashedAnswerSlot(
                                text = slot.text,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(slotHeight)
                                    .onGloballyPositioned {
                                        slotAbsolute[index] = Rect(it.positionInRoot(), it.size.toSize())
                                    },
                            )
                        }
                    }
                }

                state.cardsA.forEachIndexed { index, card ->
                    key(card.id) {
                        DraggableMatchCard(
                            card = card,
                            homeRect = homeRects[index],
                            assignedSlot = state.assignments[card.id],
                            correct = state.assignments[card.id]?.let { state.result?.get(it) },
                            locked = state.result != null,
                            slotRects = slotRects,
                            onDropped = { slotIndex ->
                                onEventSent(MatchPairsContract.Event.OnCardDropped(card.id, slotIndex))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.DraggableMatchCard(
    card: MatchAnswer,
    homeRect: Rect?,
    assignedSlot: Int?,
    correct: Boolean?,
    locked: Boolean,
    slotRects: Map<Int, Rect>,
    onDropped: (Int?) -> Unit,
) {
    if (homeRect == null) return
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val targetRect = assignedSlot?.let { slotRects[it] } ?: homeRect
    val offset = remember { Animatable(targetRect.topLeft, Offset.VectorConverter) }
    var dragging by remember { mutableStateOf(false) }
    var placed by remember { mutableStateOf(false) }

    val currentHome by rememberUpdatedState(homeRect)
    val currentSlots by rememberUpdatedState(slotRects)

    LaunchedEffect(targetRect, dragging) {
        if (dragging) return@LaunchedEffect
        if (!placed) {
            offset.snapTo(targetRect.topLeft)
            placed = true
        } else {
            offset.animateTo(
                targetValue = targetRect.topLeft,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            )
        }
    }

    val cardSize = with(density) { DpSize(homeRect.width.toDp(), homeRect.height.toDp()) }
    Box(
        modifier = Modifier
            .align(AbsoluteAlignment.TopLeft)
            .absoluteOffset { offset.value.round() }
            .size(cardSize)
            .zIndex(if (dragging) 2f else 1f)
            .then(
                if (locked) Modifier
                else Modifier.pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { dragging = true },
                        onDrag = { change, amount ->
                            change.consume()
                            scope.launch { offset.snapTo(offset.value + amount) }
                        },
                        onDragEnd = {
                            dragging = false
                            val center = offset.value +
                                Offset(currentHome.width / 2f, currentHome.height / 2f)
                            val hitSlot = currentSlots.entries
                                .firstOrNull { it.value.contains(center) }?.key
                            onDropped(hitSlot)
                        },
                        onDragCancel = { dragging = false },
                    )
                }
            ),
    ) {
        MatchCardFace(text = card.text, isDragging = dragging, correct = correct)
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchPairsScreenPreview() {
    KidsDemoTheme {
        MatchPairsScreen(
            state = MatchPairsContract.State(
                questionText = "Match the pairs of words/phrases",
                cardsA = listOf(
                    MatchAnswer(id = 0, text = "Near"),
                    MatchAnswer(id = 1, text = "Picnic"),
                    MatchAnswer(id = 2, text = "The Guide"),
                ),
                slotsB = listOf(
                    MatchAnswer(id = 0, text = "قرب"),
                    MatchAnswer(id = 2, text = "الدليل"),
                    MatchAnswer(id = 1, text = "النزهة"),
                ),
                fetchState = MatchPairsContract.FetchState.LoadData(),
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
            onCloseClicked = {},
        )
    }
}
