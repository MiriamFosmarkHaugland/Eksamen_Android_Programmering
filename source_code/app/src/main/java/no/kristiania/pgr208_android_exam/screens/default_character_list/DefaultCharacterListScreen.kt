package no.kristiania.pgr208_android_exam.screens.default_character_list

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun DefaultCharacterListScreen(
    viewModel: DefaultCharacterListViewModel,
    onCustomClick: () -> Unit,
    onCharacterClick: (characterId: Int) -> Unit = {}
) {
    val isLoading = viewModel.isLoading.collectAsState()
    val characters = viewModel.characters.collectAsState()
    val isAliveFilterSelected = viewModel.isAliveFilterSelected.collectAsState()
    val isDeadFilterSelected = viewModel.isDeadFilterSelected.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(RickAndMortyBg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .height(150.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.rick_and_morty_navbar),
                contentDescription = "Rick and Morty background image",
                contentScale = FillBounds
            )

            Text(
                modifier = Modifier
                    .padding(50.dp),
                text = "Rick and Morty fan page!",
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
            // Extended floating action button: https://developer.android.com/develop/ui/compose/components/fab
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .height(40.dp),

                onClick = { onCustomClick() },
                icon = { Icon(Icons.Filled.Person, "My page with Custom characters") },
                text = { Text(text = "My page") },
            )

            // Filter chip: https://developer.android.com/develop/ui/compose/components/chip
            FilterChip(
                selected = isAliveFilterSelected.value,
                onClick = {
                    viewModel.toggleIsAliveFilter()
                    viewModel.loadCharacters()
                  },
                label = { Text("Alive" )},
                leadingIcon = {
                    if (isAliveFilterSelected.value) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon")
                    }
                },
            )

            FilterChip(
                selected = isDeadFilterSelected.value,
                onClick = {
                    viewModel.toggleIsDeadFilter()
                    viewModel.loadCharacters()
                  },
                label = { Text("Dead" )},
                leadingIcon = {
                    if (isDeadFilterSelected.value) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon")
                    }
                },
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

        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(34.dp)
                    .wrapContentSize(Alignment.Center),
                color = Color.White
            )
        } else {
            // Since we wanna display a large number of characters in a list, but only a few will be visible at a time.
            // LazyColumn will only lay out items that are visible in the current viewport, this will help with optimizing the app.
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