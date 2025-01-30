package no.kristiania.pgr208_android_exam.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import no.kristiania.pgr208_android_exam.data.Character

@Composable
fun CharacterContainer(
    character: Character,
    onClick: () -> Unit = {}
){
    Column (
        modifier = Modifier
            .height(250.dp)
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.DarkGray),
            model = character.image,
            contentDescription = "Image of ${character.name}"
        )

        Column {
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = character.name,
                textAlign = TextAlign.Center,
                style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.White
                )
            )
        }
    }
}