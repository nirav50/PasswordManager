import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.Models.PasswordEntry
import com.example.passwordmanager.R

class PasswordAdapter(private val passwordList: List<PasswordEntry>) :
    RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.Name)
        val passwordTextView: TextView = itemView.findViewById(R.id.Password_List)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_list_view, parent, false)
        return PasswordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val currentItem = passwordList[position]
        holder.nameTextView.text = currentItem.name

        // Mask the password with dots
        val maskedPassword = "*".repeat(currentItem.password.length)
        holder.passwordTextView.text = maskedPassword

        holder.itemView.setOnClickListener {
            showPasswordDialog(holder.itemView.context, currentItem)
        }
    }

    override fun getItemCount() = passwordList.size

    private fun showPasswordDialog(context: Context, passwordEntry: PasswordEntry)
    {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.activity_dialog_password_info, null)

        val nameTextView = dialogView.findViewById<TextView>(R.id.dialogNameTextView)
        val usernameTextView = dialogView.findViewById<TextView>(R.id.dialogUsernameTextView)
        val passwordTextView = dialogView.findViewById<TextView>(R.id.dialogPasswordTextView)


        val maskedPassword = "*".repeat(passwordEntry.password.length)
        passwordTextView.text = maskedPassword // Initially, show masked password



        nameTextView.text = passwordEntry.name
        usernameTextView.text = passwordEntry.username // assuming you have a username in PasswordEntry
        passwordTextView.text = passwordEntry.password


        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)



        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
