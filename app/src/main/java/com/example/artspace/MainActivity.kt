package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import com.example.artspace.ui.theme.ArtSpaceTheme

data class Artwork(val title: String, val author: String, val year: String, val imageId: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtSpaceScreen()
            }
        }
    }
}

@Composable
fun ArtSpaceScreen() {
    val artworks = listOf(
        Artwork("Plafond de l’Opéra de Paris", "Marc Chagall", "(1964)", R.drawable.image1),
        Artwork("Le Cri", "Edvard Munch", "(1893)", R.drawable.image2),
        Artwork("La Nuit étoilée", "Vincent Van Gogh", "(1889)", R.drawable.image3),
        Artwork("La Jeune fille à la perle", "Johannes Vermeer", "(1665)", R.drawable.image4),
        Artwork("La Joconde", "Léonard de Vinci", "(1503-1519)", R.drawable.image5)
    )

    var currentIndex by rememberSaveable { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Box(modifier = Modifier.fillMaxSize()) {
        if (isPortrait) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImageSection(artwork = currentArtwork)
                DescriptorSection(artwork = currentArtwork)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImageSection(artwork = currentArtwork, modifier = Modifier.weight(1f).fillMaxHeight())
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(top = 8.dp)
                ) {
                    DescriptorSection(artwork = currentArtwork)
                }
            }
        }

        ControllerSection(
            currentIndex = currentIndex,
            artworksSize = artworks.size,
            onPrevious = { if (currentIndex > 0) currentIndex-- },
            onNext = { if (currentIndex < artworks.size - 1) currentIndex++ },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ImageSection(artwork: Artwork, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(16.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.small,
            color = Color.White,
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Image(
                painter = painterResource(id = artwork.imageId),
                contentDescription = artwork.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}

@Composable
fun DescriptorSection(artwork: Artwork) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {

        Text(
            text = artwork.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.sp,
            maxLines = 2,
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = artwork.author,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
                maxLines = 1,
            )

            Text(
                text = " ${artwork.year}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = Color.Gray,
                maxLines = 1,

            )
        }
    }
}

@Composable
fun ControllerSection(
    currentIndex: Int,
    artworksSize: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPrevious,
            enabled = currentIndex > 0,
            modifier = Modifier
                .height(56.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Précédente", modifier = Modifier.padding(8.dp), color = Color.White)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onNext,
            enabled = currentIndex < artworksSize - 1,
            modifier = Modifier
                .height(56.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Suivante", modifier = Modifier.padding(8.dp), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}























