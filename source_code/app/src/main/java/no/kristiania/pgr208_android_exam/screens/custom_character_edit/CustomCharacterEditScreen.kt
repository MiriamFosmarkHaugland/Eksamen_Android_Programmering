package no.kristiania.pgr208_android_exam.screens.custom_character_edit

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
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
import no.kristiania.pgr208_android_exam.screens.components.InputField
import no.kristiania.pgr208_android_exam.ui.theme.RickAndMortyBg

@Composable
fun CustomCharacterEditScreen(
    viewModel: CustomCharacterEditViewModel,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val character = viewModel.character.collectAsState().value // Get the actual Character? value
    val nameInput = viewModel.name.collectAsState()
    val imageInput = viewModel.image.collectAsState()
    val speciesInput = viewModel.species.collectAsState()

    if(character != null) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(RickAndMortyBg)
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
                    .padding(10.dp),
                text = "Hello ${character.name}, you can make changes to your character here!",
                textAlign = TextAlign.Center,
                color = Color.White,
                style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800
                )
            )

            AsyncImage(
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.LightGray),
                model = character.image,
                contentDescription = "Image of ${character.name}"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Name: ${character.name}\nSpecies: ${character.species}",
                color = Color.White,
                style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W800
                )
            )

            Spacer(modifier = Modifier.height(20.dp))


            // Input fields from component
            Column {
                InputField(
                    title = "Change Name:",
                    placeholder = "Name",
                    value = nameInput.value,
                    onChange = { output -> viewModel.setName(output)})

                Spacer(modifier = Modifier.height(25.dp))

                InputField(
                    title = "Change Image:",
                    placeholder = "Image",
                    value = imageInput.value,
                    onChange = { output -> viewModel.setImage(output)})

                Spacer(modifier = Modifier.height(25.dp))

                InputField(
                    title = "Change Species:",
                    placeholder = "Species",
                    value = speciesInput.value,
                    onChange = { output -> viewModel.setSpecies(output)})
            }

            Spacer(modifier = Modifier.height(30.dp))

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.updateCharacter()
                    onSubmitClick()
                },
                // Button color: https://stackoverflow.com/questions/64376333/background-color-on-button-in-jetpack-compose
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Submit changes",
                    color = Color.Black,
                    style =
                    TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W800
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.deleteCharacter()
                    onDeleteClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Delete character",
                    color = Color.Black,
                    style =
                    TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W800
                    )
                )
            }
        }
    } else {
        CircularProgressIndicator()
    }
}