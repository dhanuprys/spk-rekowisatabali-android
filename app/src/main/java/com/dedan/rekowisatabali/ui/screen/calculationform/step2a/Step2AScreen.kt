package com.dedan.rekowisatabali.ui.screen.calculationform.step2a

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.model.RecommendationTemplate
import com.dedan.rekowisatabali.ui.AppViewModel
import com.dedan.rekowisatabali.ui.layout.DataEmpty
import com.dedan.rekowisatabali.ui.layout.PageError
import com.dedan.rekowisatabali.ui.layout.PageLoading
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.RecommendationTemplateUiState
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme

object Step2ADestination : NavigationDestination {
    override val route = "calculationform_step2a"
    override val titleRes = R.string.select_template
}

@Composable
fun Step2AScreen(
    viewModel: CalculationFormViewModel,
    navigateUp: () -> Unit,
    navigateToStep3: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.recommendationTemplateUiState

    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = Step2ADestination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Step2ABody(
            uiState = uiState,
            onTemplateSelect = { template ->
                viewModel.prepareTemplateCalculation(template.id)
                navigateToStep3()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Step2ABody(
    uiState: RecommendationTemplateUiState,
    onTemplateSelect: (RecommendationTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is RecommendationTemplateUiState.Error ->
            PageError(modifier = Modifier.fillMaxSize())

        is RecommendationTemplateUiState.Loading ->
            PageLoading(modifier = Modifier.fillMaxSize())

        is RecommendationTemplateUiState.Success ->
            if (uiState.data.isEmpty()) {
                DataEmpty(modifier = Modifier.fillMaxSize())
            } else {
                TemplateList(
                    templates = uiState.data,
                    onTemplateSelect = onTemplateSelect,
                    modifier = modifier.padding(16.dp)
                )
            }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Step2ABodyPreview() {
    RekoWisataBaliTheme {
        Step2ABody(
            uiState = RecommendationTemplateUiState.Success(
                listOf(
                    RecommendationTemplate(
                        id = 1,
                        imageUrl = "https://example.com/image.jpg",
                        name = "Example Template",
                        description = "This is an example recommendation template."
                    ),
                    RecommendationTemplate(
                        id = 2,
                        imageUrl = "https://example.com/image2.jpg",
                        name = "Another Example Template",
                        description = "This is another example recommendation template."
                    ),
                    RecommendationTemplate(
                        id = 3,
                        imageUrl = "https://example.com/image3.jpg",
                        name = "Yet Another Example Template",
                        description = "This is yet another example recommendation template."
                    )
                )
            ),
            onTemplateSelect = {}
        )
    }
}

@Composable
fun TemplateList(
    templates: List<RecommendationTemplate>,
    onTemplateSelect: (RecommendationTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(items = templates) {
            RecommendationTemplateCard(
                template = it,
                onClick = { onTemplateSelect(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun TemplateListPreview() {
    RekoWisataBaliTheme {
        TemplateList(
            templates = listOf(
                RecommendationTemplate(
                    id = 1,
                    imageUrl = "https://example.com/image.jpg",
                    name = "Example Template",
                    description = "This is an example recommendation template."
                ),
                RecommendationTemplate(
                    id = 2,
                    imageUrl = "https://example.com/image2.jpg",
                    name = "Another Example Template",
                    description = "This is another example recommendation template."
                )
            ),
            onTemplateSelect = {}
        )
    }
}

@Composable
fun RecommendationTemplateCard(
    template: RecommendationTemplate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier) {
        AsyncImage(
            model = template.imageUrl,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            fallback = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = template.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = template.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun RecommendationTemplateCardPreview() {
    RekoWisataBaliTheme {
        RecommendationTemplateCard(
            template = RecommendationTemplate(
                id = 1,
                imageUrl = "https://example.com/image.jpg",
                name = "Example Template",
                description = "This is an example recommendation template."
            ),
            onClick = {}
        )
    }
}
