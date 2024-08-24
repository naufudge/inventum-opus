package com.example.inventumopus.ui.screens

import InformationModal
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.Bookmark
import com.example.inventumopus.datamodels.Job
import com.example.inventumopus.datamodels.JobApplication
import com.example.inventumopus.datamodels.User
import com.example.inventumopus.ui.theme.Orange1

@Composable
fun JobDetailsScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val job = viewModel.selectedJob
    val currentUser = viewModel.currentUser

    val isLoggedIn by viewModel.signedIn.collectAsState()
    var bookmark by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        if (job?._id!! in currentUser?.bookmarks!!) {
            bookmark = true
        }
    }
    Scaffold (
        topBar = {},
        bottomBar = {
            if (isLoggedIn) JobDetailsBottomBar(
                onApplyClick = {
                    showDialog = true
                }
            )
//            JobDetailsBottomBar(
//                onApplyClick = {
//                    showDialog = true
//                }
//            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Row (
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 30.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
                // Bookmark Button
                if (isLoggedIn) {
                    IconButton(
                        onClick = {
                            if (bookmark) {
                                viewModel.manageBookmark(Bookmark(
                                    username = currentUser?.username!!,
                                    jobId = job?._id!!,
                                    add = false
                                ))
                                bookmark = false
                            } else {
                                viewModel.manageBookmark(Bookmark(
                                    username = currentUser?.username!!,
                                    jobId = job?._id!!,
                                    add = true
                                ))
                                bookmark = true
                            }
                        }
                    ) {
                        if (!bookmark) {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "back"
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "back_filled",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(50.dp))
            Column (
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Job Image Card
                    ElevatedCard(
                        modifier = Modifier
                            .size(width = 130.dp, height = 130.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp)),
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(job?.image)
                                    .size(Size.ORIGINAL)
                                    .build(),
                                contentDescription = null
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // Job Title
                    Text(
                        text = job?.name!!,
                        fontFamily = prata,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 30.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    // Company & Location
                    Text(
                        text = "${job.company} / ${job.location}",
                        fontFamily = poppins,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
                Column (
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .width(200.dp)
                ) {
                    // Job Salary
                    Text(
                        text = "Salary",
                        fontFamily = raleway,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job?.salary!!,
                        fontFamily = raleway,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Job Qualification
                    Text(
                        text = "Qualification",
                        fontFamily = raleway,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job.qualification,
                        fontFamily = raleway,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Job Experience
                    Text(
                        text = "Experience Required",
                        fontFamily = raleway,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job.experience,
                        fontFamily = raleway,
                        fontSize = 13.sp
                    )
                }
                Column (
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    // Job Description
                    Text(
                        text = "Job Description",
                        fontFamily = raleway,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job?.description!!,
                        fontFamily = raleway,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
            ConfirmationModal(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                viewModel = viewModel,
                user = currentUser,
                job = job!!
            )
        }
    }
}

@Composable
fun JobDetailsBottomBar(
    onApplyClick: () -> Unit
) {
    BottomAppBar (
        content = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonColors(
                        containerColor = Orange1,
                        contentColor = Color.Gray,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    ),
                    onClick = { onApplyClick() }
                ) {
                    Text(
                        text = "Apply!",
                        fontFamily = poppins,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Composable
fun ConfirmationModal(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel,
    user: User?,
    job: Job
) {
    var showInfoDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "Are you sure?",
                        fontFamily = prata,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Would you like to apply for the position of \"${job.name}\" at \"${job.company}\"?",
                        fontFamily = raleway,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // No button
                        OutlinedButton(
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(2.dp, Color.LightGray),
                            onClick = { onDismiss() }
                        ) {
                            Text(
                                text = "No",
                                fontFamily = raleway,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        // Yes button
                        Button(
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonColors(
                                containerColor = Orange1,
                                contentColor = Color.Gray,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.Gray
                            ),
                            onClick = {
                                // Handle "Yes" click
                                viewModel.addUserJobApplication(JobApplication(
                                    username = user?.username!!,
                                    jobId = job._id
                                ))
                                onDismiss()
                                showInfoDialog = true
                            }
                        ) {
                            Text(
                                text = "Yes",
                                fontFamily = raleway,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    InformationModal(
        showDialog = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Applied Successfully!",
        message = "You have applied for \"${job.name}\" successfully. You can check application status by going to \"Profile\" > \"Applied Jobs\""
    )
}