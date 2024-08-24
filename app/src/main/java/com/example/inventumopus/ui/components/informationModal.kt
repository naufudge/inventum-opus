import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inventumopus.ui.screens.prata
import com.example.inventumopus.ui.screens.raleway

@Composable
fun InformationModal(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    message: String = "",
    title: String = "Error"
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { onDismiss() }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Back Icon")
                        }
                    }

                    Text(text = title, fontFamily = prata)
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(text = message, fontFamily = raleway, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
