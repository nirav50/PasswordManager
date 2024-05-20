import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.passwordmanager.R

class AddPasswordDialog(private val context: Context) {

    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.activity_add_password_dialog, null)
        dialogBuilder.setView(dialogView)

        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextUsername = dialogView.findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextPassword)
        val add_newBtn = dialogView.findViewById<Button>(R.id.add_newBtn)

        // Set a click listener for the "Save" button
        val dialog = dialogBuilder.create()

        add_newBtn.setOnClickListener {
            val name = editTextName.text.toString()
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Check if any field is empty
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Password complexity requirements
            val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}$")
            if (!password.matches(passwordPattern)) {
                Toast.makeText(
                    context,
                    "Password must contain at least 8 characters, including at least one digit, one uppercase letter, one lowercase letter, and one special character",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Here, you can perform any actions you want with the entered data.
            // For example, you can save it to a database.

            // For demonstration, let's just show a toast with the entered data.
            val message = "Name: $name\nUsername: $username\nPassword: $password"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            // Dismiss the dialog after showing the toast
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun evaluatePasswordStrength(password: String): String {
        // Password complexity requirements
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}$")
        return when {
            password.matches(passwordPattern) -> "Strong"
            password.length >= 8 -> "Medium"
            else -> "Weak"
        }
    }
}
