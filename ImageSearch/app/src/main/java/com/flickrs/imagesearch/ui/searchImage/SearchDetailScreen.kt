package com.flickrs.imagesearch.ui.searchImage

import android.text.Html
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flickrs.imagesearch.domain.entities.MappedImageItemModel
import com.flickrs.imagesearch.ui.commons.DateTime

@Composable
internal fun ImageDetailScreen(
    isTwoPane: Int = 0,
    result: MappedImageItemModel,
    onBackClicked: () -> Unit
) {
    DetailScreenContent(
        modifier = Modifier.fillMaxSize(),
        item = result,
        onBackBtnClicked = onBackClicked,
        isTwoPane
    )
}

@Composable
internal fun DetailScreenContent(
    modifier: Modifier = Modifier,
    item: MappedImageItemModel,
    onBackBtnClicked: () -> Unit,
    isTwoPane: Int
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    if (isTwoPane == 0) {
                        BackButton(
                            onBackClicked = onBackBtnClicked
                        )
                    }
                    // ProfileHeader which contain the image part
                    ImageHeader(
                        item
                    )
                    ImageDetailContent(item = item, this@BoxWithConstraints.maxHeight)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButton(
    onBackClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Image Detail",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.Black
                )
            }
        }
    )
}

@Composable
private fun ImageHeader(
    item: MappedImageItemModel
) {
    AsyncImage(
        modifier = Modifier
            .heightIn(300.dp)
            .fillMaxSize(),
        model = ImageRequest.Builder(LocalContext.current).data(item.imageLink)
            .crossfade(true).build(),
        contentDescription = item.imageTitle,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ImageDetailContent(item: MappedImageItemModel, containerHeight: Dp) {
    Column {
        Title(item)
        ImageDetailProperty("Author", item.author)
        ImageDetailProperty("Published At", DateTime.getFormattedDate(item.publishedTime))
        ImageDetailProperty("Description", item.description, true)
        // To make the view scrollable
        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun Title(
    item: MappedImageItemModel
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)) {
        Text(
            text = item.imageTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ImageDetailProperty(label: String, value: String, isHtml: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        //Divider function to add a vertical line before showing any new property
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = if (isHtml) Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
                .toString() else value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Visible,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MaterialTheme {
        Surface {
//            ImageDetailScreen(dummyImageItem.toImageModel()) {}
        }
    }
}


