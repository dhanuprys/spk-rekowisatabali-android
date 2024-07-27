package com.dedan.rekowisatabali.ui.screen.calculationform.step2b

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.model.Criteria
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.result.BackToHomeButton
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

object Step2BDestination : NavigationDestination {
    override val route = "calculationform_step2b"
    override val titleRes = R.string.select_criteria
}

private val HEIGHT_3 = 230.dp
private val HEIGHT_2 = 160.dp

@Composable
fun Step2BScreen(
    viewModel: CalculationFormViewModel,
    navigateUp: () -> Unit,
    navigateToStep3: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = Step2BDestination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        bottomBar = {
            GoToStep3Button(
                onClick = {
                    navigateToStep3()
                    viewModel.prepareManualCalculation()
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Step2BBody(
            onReorder = viewModel::reorderCriteria,
            onDirectionChange = viewModel::changeCriteriaDirection,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Composable
fun GoToStep3Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(modifier = modifier) {
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Lanjutkan")
        }
    }
}

@Composable
fun Step2BBody(
    onReorder: (List<Pair<String, Float>>) -> Unit,
    onDirectionChange: (Pair<String, Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        CriteriaTitle(text = "Level 1")
        CriteriaLevel1(
            onReorder = onReorder,
            onDirectionChange = onDirectionChange
        )
        Spacer(modifier = Modifier.height(16.dp))

        CriteriaTitle(text = "Level 2")
        CriteriaLevel2(onReorder = onReorder)
        Spacer(modifier = Modifier.height(16.dp))

        CriteriaTitle(text = "Level 3")
        CriteriaLevel3_1(onReorder = onReorder)
        Spacer(modifier = Modifier.height(10.dp))
        CriteriaLevel3_2(onReorder = onReorder)
        Spacer(modifier = Modifier.height(10.dp))
        CriteriaLevel3_3(onReorder = onReorder)
    }
}

@Composable
fun CriteriaTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun Step2BBodyPreview() {
    RekoWisataBaliTheme {
        Step2BBody(
            onReorder = {},
            onDirectionChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun CriteriaLevel3_1(onReorder: (List<Pair<String, Float>>) -> Unit) {
    ReorderableCriteria(
        criteriaList = Criteria.Level3_1,
        onReorder = onReorder,
        onDirectionChange = {},
        modifier = Modifier.height(HEIGHT_2)
    )
}

@Composable
fun CriteriaLevel3_2(onReorder: (List<Pair<String, Float>>) -> Unit) {
    ReorderableCriteria(
        criteriaList = Criteria.Level3_2,
        onReorder = onReorder,
        onDirectionChange = {},
        modifier = Modifier.height(HEIGHT_3)
    )
}

@Composable
fun CriteriaLevel3_3(onReorder: (List<Pair<String, Float>>) -> Unit) {
    ReorderableCriteria(
        criteriaList = Criteria.Level3_3,
        onReorder = onReorder,
        onDirectionChange = {},
        modifier = Modifier.height(HEIGHT_3)
    )
}

@Composable
fun CriteriaLevel2(onReorder: (List<Pair<String, Float>>) -> Unit) {
    ReorderableCriteria(
        criteriaList = Criteria.Level2,
        onReorder = onReorder,
        onDirectionChange = {},
        modifier = Modifier.height(HEIGHT_3)
    )
}

@Composable
fun CriteriaLevel1(
    onReorder: (List<Pair<String, Float>>) -> Unit,
    onDirectionChange: (Pair<String, Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    ReorderableCriteria(
        criteriaList = Criteria.Level1,
        onReorder = onReorder,
        onDirectionChange = onDirectionChange,
        modifier = modifier.height(HEIGHT_3)
    )
}

@Composable
fun ReorderableCriteria(
    criteriaList: List<Criteria>,
    onReorder: (List<Pair<String, Float>>) -> Unit,
    onDirectionChange: (Pair<String, Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    val data = remember { mutableStateOf(criteriaList) }
    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            data.value = data.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        },
        onDragEnd = { _, _ ->
            val criteriaValue =
                if (data.value.size == 3)
                    listOf<Float>(0.6111f, 0.2778f, 0.1111f)
                else
                    listOf<Float>(0.75f, 0.25f)

            var index = -1
            val output: List<Pair<String, Float>> = data.value.map { criteria ->
                index++
                Pair(criteria.tag, criteriaValue[index])
            }

            onReorder(output)
        }
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = state.listState,
        modifier = modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(items = data.value, key = { it.tag }) { item ->
            ReorderableItem(state, key = { item.tag }) { isDragging ->
                CriteriaCard(
                    criteria = item,
                    onDirectionChange = {
                        onDirectionChange(Pair(item.tag, it))

                        data.value = data.value.toMutableList().apply {
                            val index = indexOf(item)
                            set(index, item.copy(direction = it))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CriteriaCard(
    criteria: Criteria,
    onDirectionChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(criteria.text)

            if (criteria.withDirection) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (criteria.direction == 1) {
                        "↑ MAX"
                    } else {
                        "↓ MIN"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable {
                            onDirectionChange(if (criteria.direction == 1) 0 else 1)
                        }
                )
            }
        }
    }
}

@Preview(widthDp = 400)
@Composable
fun CriteriaCardPreview() {
    RekoWisataBaliTheme {
        CriteriaCard(
            criteria = Criteria(
                tag = "Tag",
                text = "Text"
            ),
            onDirectionChange = {}
        )
    }
}