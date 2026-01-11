package id.antasari.fpa_230104040205_laportitik.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.antasari.fpa_230104040205_laportitik.data.AuthRepository
import id.antasari.fpa_230104040205_laportitik.ui.theme.OrangePrimary
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // STATE UNTUK ROLE (PILIHAN)
    var selectedRole by remember { mutableStateOf("Citizen") }
    val roles = listOf("Citizen", "Volunteer", "Admin")

    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()), // Agar bisa discroll di layar kecil
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = OrangePrimary)
        Text("Join LaporTitik community", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Input Nama
        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Full Name") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input Email
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input Password
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password (Min 8 chars)") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- BAGIAN MEMILIH ROLE ---
        Text(
            "Select Your Role:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
        )

        // Loop untuk membuat 3 Pilihan Radio Button
        roles.forEach { role ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedRole = role }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (role == selectedRole),
                    onClick = { selectedRole = role },
                    colors = RadioButtonDefaults.colors(selectedColor = OrangePrimary)
                )
                Text(
                    text = role,
                    fontSize = 16.sp,
                    fontWeight = if(role == selectedRole) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
        // ---------------------------

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Daftar
        Button(
            onClick = {
                if (name.isNotEmpty() && email.isNotEmpty() && password.length >= 8) {
                    isLoading = true
                    scope.launch {
                        // Kirim Role yang dipilih ke Repository
                        val result = AuthRepository.register(name, email, password, selectedRole)
                        isLoading = false

                        if (result.isSuccess) {
                            Toast.makeText(context, "Welcome $selectedRole, $name!", Toast.LENGTH_SHORT).show()
                            UserRepository.refreshUserData() // Update data user di RAM

                            navController.navigate("home") {
                                popUpTo("register") { inclusive = true }
                            }
                        } else {
                            val msg = result.exceptionOrNull()?.message ?: "Register Failed"
                            Toast.makeText(context, "Error: $msg", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Please complete all fields (Pass min 8)", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
            enabled = !isLoading
        ) {
            if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            else Text("SIGN UP", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text("Already have an account? ")
            Text("Login", color = OrangePrimary, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navController.navigate("login") })
        }
    }
}