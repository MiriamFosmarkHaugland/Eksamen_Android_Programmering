package no.kristiania.pgr208_android_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import no.kristiania.pgr208_android_exam.data.RickAndMortyRepository
import no.kristiania.pgr208_android_exam.navigation.CreateCharacter
import no.kristiania.pgr208_android_exam.navigation.CustomCharacterEdit
import no.kristiania.pgr208_android_exam.screens.default_character_list.DefaultCharacterListViewModel
import no.kristiania.pgr208_android_exam.navigation.CustomCharacterList
import no.kristiania.pgr208_android_exam.navigation.DefaultCharacterDetails
import no.kristiania.pgr208_android_exam.navigation.DefaultCharacterList
import no.kristiania.pgr208_android_exam.screens.create_character.CreateCharacterScreen
import no.kristiania.pgr208_android_exam.screens.create_character.CreateCharacterViewModel
import no.kristiania.pgr208_android_exam.screens.custom_character_edit.CustomCharacterEditScreen
import no.kristiania.pgr208_android_exam.screens.custom_character_edit.CustomCharacterEditViewModel
import no.kristiania.pgr208_android_exam.screens.custom_character_list.CustomCharacterListScreen
import no.kristiania.pgr208_android_exam.screens.custom_character_list.CustomCharacterListViewModel
import no.kristiania.pgr208_android_exam.screens.default_character_details.DefaultCharacterDetailsScreen
import no.kristiania.pgr208_android_exam.screens.default_character_details.DefaultCharacterDetailsViewModel
import no.kristiania.pgr208_android_exam.screens.default_character_list.DefaultCharacterListScreen
import no.kristiania.pgr208_android_exam.ui.theme.Pgr208androidexamTheme

class MainActivity : ComponentActivity() {

    private val _defaultCharacterListViewModel: DefaultCharacterListViewModel by viewModels()
    private val _defaultCharacterDetailsViewModel: DefaultCharacterDetailsViewModel by viewModels()
    private val _customCharacterListViewModel: CustomCharacterListViewModel by viewModels()
    private val _createCharacterViewModel: CreateCharacterViewModel by viewModels()
    private val _customCharacterEditViewModel: CustomCharacterEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        RickAndMortyRepository.initializeDatabase(applicationContext)

        setContent {
            Pgr208androidexamTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = DefaultCharacterList
                ) {
                    composable <DefaultCharacterList> {
                        DefaultCharacterListScreen(
                            viewModel = _defaultCharacterListViewModel,
                            onCustomClick = {navController.navigate(CustomCharacterList)},
                            onCharacterClick = { characterId -> navController.navigate(DefaultCharacterDetails(characterId)) }
                        )
                    }
                    composable<DefaultCharacterDetails> { backStackEntry ->
                        val characterDetails = backStackEntry.toRoute<DefaultCharacterDetails>()
                        _defaultCharacterDetailsViewModel.loadCharacter(characterDetails.characterId)

                        DefaultCharacterDetailsScreen(
                            viewModel = _defaultCharacterDetailsViewModel,
                            onBackClick = {navController.popBackStack()}
                        )
                    }
                    composable <CustomCharacterList> {
                        CustomCharacterListScreen(
                            viewModel = _customCharacterListViewModel,
                            onBackClick = {navController.navigate(DefaultCharacterList)},
                            onCreateClick = {navController.navigate(CreateCharacter)},
                            onCharacterClick = { characterId -> navController.navigate(CustomCharacterEdit(characterId)) }
                        )
                    }
                    composable <CreateCharacter> {
                        CreateCharacterScreen(
                            viewModel = _createCharacterViewModel,
                            onBackClick = {navController.navigate(CustomCharacterList)},
                            onSubmitClick = {navController.navigate(CustomCharacterList)},
                            onDefaultClick = {navController.navigate(DefaultCharacterList)},
                            onCustomClick = {navController.navigate(CustomCharacterList)}
                        )
                    }
                    composable<CustomCharacterEdit> { backStackEntry ->
                        val characterDetails = backStackEntry.toRoute<CustomCharacterEdit>()
                        _customCharacterEditViewModel.loadCharacter(characterDetails.characterId)

                        CustomCharacterEditScreen(
                            viewModel = _customCharacterEditViewModel,
                            onBackClick = {navController.popBackStack()},
                            onSubmitClick = {navController.navigate(CustomCharacterList)},
                            onDeleteClick = {navController.navigate(CustomCharacterList)}
                        )
                    }
                }
            }
        }
    }
}