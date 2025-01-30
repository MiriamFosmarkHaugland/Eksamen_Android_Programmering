package no.kristiania.pgr208_android_exam.screens.custom_character_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.kristiania.pgr208_android_exam.R
import no.kristiania.pgr208_android_exam.screens.components.CharacterContainer
import no.kristiania.pgr208_android_exam.ui.theme.RickAndMortyBg

@Composable
fun CustomCharacterListScreen(
    viewModel: CustomCharacterListViewModel,
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit,
    onCharacterClick: (characterId: Int) -> Unit = {}
) {
    val isLoading = viewModel.isLoading.collectAsState()
    val characters = viewModel.characters.collectAsState()

    // Loads custom characters every time we enter the custom characters page.
    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RickAndMortyBg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rick_and_morty_planet),
                contentDescription = "Rick and Morty background image",
                contentScale = FillBounds
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ){
                IconButton(
                    modifier = Modifier
                        .padding(bottom = 65.dp),
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
                text = "Here are all your custom characters!",
                textAlign = TextAlign.Center,
                color = Color.White,
                style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800
                )
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .height(40.dp),
                onClick = { onCreateClick() },
                icon = { Icon(Icons.Filled.Add, "Create custom character.") },
                text = { Text(text = "Create") },
            )

            IconButton(
                onClick = { viewModel.loadCharacters() }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh default characters.",
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(10.dp),
            text = "Click on a character to see details and make changes if you wish",
            textAlign = TextAlign.Center,
            color = Color.White,
            style =
            TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = FontWeight.W800
            )
        )

        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(34.dp)
                    .wrapContentSize(Alignment.Center),
                color = Color.White
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ){
                items(characters.value) { character ->
                    CharacterContainer(
                        character = character,
                        onClick = {
                            onCharacterClick(character.id)
                        }
                    )
                }
            }
        }
    }
}