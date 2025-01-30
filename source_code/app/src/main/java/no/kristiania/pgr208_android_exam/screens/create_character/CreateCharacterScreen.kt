package no.kristiania.pgr208_android_exam.screens.create_character

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
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
import no.kristiania.pgr208_android_exam.screens.components.InputField
import no.kristiania.pgr208_android_exam.ui.theme.RickAndMortyBg

@Composable
fun CreateCharacterScreen(
    viewModel: CreateCharacterViewModel,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onCustomClick: () -> Unit,
    onDefaultClick: () -> Unit
) {
    val nameInput = viewModel.name.collectAsState()
    val imageInput = viewModel.image.collectAsState()
    val speciesInput = viewModel.species.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(RickAndMortyBg)
            .verticalScroll(state = rememberScrollState())
    ){
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(RickAndMortyBg)
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rick_and_morty_portal),
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
                        contentDescription = "Go back to custom characters.",
                        tint = Color.Black
                    )
                }
            }

            Text(
                modifier = Modifier
                    .width(350.dp)
                    .padding(20.dp),
                text = "Create your own Character for the Rick and Morty universe!",
                textAlign = TextAlign.Center,
                style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .height(40.dp),
                onClick = { onDefaultClick() },
                icon = { Icon(Icons.Filled.Person, "Original characters.") },
                text = { Text(text = "Original") },
            )

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .height(40.dp),
                onClick = { onCustomClick() },
                icon = { Icon(Icons.Filled.Person, "My page with Custom characters.") },
                text = { Text(text = "My page") },
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        InputField(
            title = "Choose Name (mandatory):",
            placeholder = "Name", value = nameInput.value,
            onChange = { output -> viewModel.setName(output)})

        Spacer(modifier = Modifier.height(25.dp))

        InputField(
            title = "Choose Image (optional):",
            placeholder = "Image", value = imageInput.value,
            onChange = { output -> viewModel.setImage(output)})

        Spacer(modifier = Modifier.height(25.dp))

        InputField(
            title = "Choose Species (optional):",
            placeholder = "Species",
            value = speciesInput.value,
            onChange = { output -> viewModel.setSpecies(output)})

        Spacer(modifier = Modifier.height(50.dp))

        FilledTonalButton(
            onClick = {
                viewModel.addCharacter()
                onSubmitClick()
            },
            enabled = nameInput.value.isNotBlank(),
            colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray, containerColor = Color.Green)
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black,
                text = "Submit",
                style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W800
                )
            )
        }
    }
}