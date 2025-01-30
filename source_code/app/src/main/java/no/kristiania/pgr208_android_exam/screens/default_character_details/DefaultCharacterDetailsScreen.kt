package no.kristiania.pgr208_android_exam.screens.default_character_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import no.kristiania.pgr208_android_exam.ui.theme.RickAndMortyBg

@Composable
fun DefaultCharacterDetailsScreen(
    viewModel: DefaultCharacterDetailsViewModel,
    onBackClick: () -> Unit
) {
    val characterState = viewModel.character.collectAsState()
    val character = characterState.value

    if (character == null) {
        Text(
            text = "Failed to get the character information, please try again"
        )
        return
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(RickAndMortyBg)
            // Vertical scroll: https://developer.android.com/develop/ui/compose/touch-input/pointer-input/scroll
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ){
            IconButton(
                onClick = { onBackClick() }) {
                Icon(
                    modifier = Modifier
                        .size(34.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back to default characters.",
                    tint = Color.White
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 30.dp),
            text = character.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            style =
            TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 30.sp,
                fontWeight = FontWeight.W800
            )
        )

        AsyncImage(
            modifier = Modifier
                .size(300.dp, 300.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.LightGray),
            model = character.image,
            contentDescription = "Image of ${character.name}"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Species: ${character.species}\n\nStatus: ${character.status}\n\nType: ${if (character.type != "") character.type else "Unknown"}\n\nGender: ${character.gender}",
            color = Color.White,
            style =
            TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                fontWeight = FontWeight.W800
            )
        )
    }
}
